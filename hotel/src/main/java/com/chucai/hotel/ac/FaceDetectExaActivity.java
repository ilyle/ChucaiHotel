package com.chucai.hotel.ac;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.baidu.aip.face.AipFace;
import com.baidu.aip.face.MatchRequest;
import com.baidu.idl.face.platform.FaceStatusNewEnum;
import com.baidu.idl.face.platform.model.ImageInfo;
import com.baidu.idl.face.platform.ui.FaceDetectActivity;
import com.chucai.hotel.ac.config.Config;
import com.chucai.hotel.bean.baidu.BaiduResponse;
import com.chucai.hotel.bean.baidu.FaceCompare;
import com.chucai.hotel.util.Base64IntentUtils;
import com.github.rholder.retry.Attempt;
import com.github.rholder.retry.RetryException;
import com.github.rholder.retry.RetryListener;
import com.github.rholder.retry.Retryer;
import com.github.rholder.retry.RetryerBuilder;
import com.github.rholder.retry.StopStrategies;
import com.github.rholder.retry.WaitStrategies;
import com.google.common.base.Predicates;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class FaceDetectExaActivity extends FaceDetectActivity {
    private static final String TAG = "FaceDetectExaActivity";

    private Context mContext;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
    }

    @Override
    public void onDetectCompletion(FaceStatusNewEnum status, String message,
                                   HashMap<String, ImageInfo> base64ImageCropMap,
                                   HashMap<String, ImageInfo> base64ImageSrcMap) {
        super.onDetectCompletion(status, message, base64ImageCropMap, base64ImageSrcMap);
        if (status == FaceStatusNewEnum.OK && mIsCompletion) {
            // 获取最优图片
            getBestImage(base64ImageCropMap, base64ImageSrcMap);
        } else if (status == FaceStatusNewEnum.DetectRemindCodeTimeout) {
            if (mViewBg != null) {
                mViewBg.setVisibility(View.VISIBLE);
            }
        }
    }

    /**
     * 获取最优图片
     *
     * @param imageCropMap 抠图集合
     * @param imageSrcMap  原图集合
     */
    private void getBestImage(HashMap<String, ImageInfo> imageCropMap, HashMap<String, ImageInfo> imageSrcMap) {
        String bmpStr = null;
        // 将抠图集合中的图片按照质量降序排序，最终选取质量最优的一张抠图图片
        if (imageCropMap != null && imageCropMap.size() > 0) {
            List<Map.Entry<String, ImageInfo>> list1 = new ArrayList<>(imageCropMap.entrySet());
            Collections.sort(list1, new Comparator<Map.Entry<String, ImageInfo>>() {

                @Override
                public int compare(Map.Entry<String, ImageInfo> o1,
                                   Map.Entry<String, ImageInfo> o2) {
                    String[] key1 = o1.getKey().split("_");
                    String score1 = key1[2];
                    String[] key2 = o2.getKey().split("_");
                    String score2 = key2[2];
                    // 降序排序
                    return Float.valueOf(score2).compareTo(Float.valueOf(score1));
                }
            });

            // 获取抠图中的加密或非加密的base64
//            int secType = mFaceConfig.getSecType();
//            String base64;
//            if (secType == 0) {
//                base64 = list1.get(0).getValue().getBase64();
//            } else {
//                base64 = list1.get(0).getValue().getSecBase64();
//            }
        }

        // 将原图集合中的图片按照质量降序排序，最终选取质量最优的一张原图图片
        if (imageSrcMap != null && imageSrcMap.size() > 0) {
            List<Map.Entry<String, ImageInfo>> list2 = new ArrayList<>(imageSrcMap.entrySet());
            Collections.sort(list2, new Comparator<Map.Entry<String, ImageInfo>>() {

                @Override
                public int compare(Map.Entry<String, ImageInfo> o1,
                                   Map.Entry<String, ImageInfo> o2) {
                    String[] key1 = o1.getKey().split("_");
                    String score1 = key1[2];
                    String[] key2 = o2.getKey().split("_");
                    String score2 = key2[2];
                    // 降序排序
                    return Float.valueOf(score2).compareTo(Float.valueOf(score1));
                }
            });
            bmpStr = list2.get(0).getValue().getBase64();

            // 获取原图中的加密或非加密的base64
//            int secType = mFaceConfig.getSecType();
//            String base64;
//            if (secType == 0) {
//                base64 = list2.get(0).getValue().getBase64();
//            } else {
//                base64 = list2.get(0).getValue().getSecBase64();
//            }
        }

        // 页面跳转
        Base64IntentUtils.getInstance().setmCollectBase64(bmpStr);
        String local = Base64IntentUtils.getInstance().getmLocalBase64();
        startCompare(local, bmpStr);
    }


    @Override
    public void finish() {
        super.finish();
    }


    private void startCompare(final String base64_1, final String base64_2) {
        if (TextUtils.isEmpty(base64_1) || TextUtils.isEmpty(base64_2)) {
            Toast.makeText(mContext, "有照片为空", Toast.LENGTH_SHORT).show();
            return;
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Retryer<Boolean> myRetryer = RetryerBuilder.<Boolean>newBuilder()
                            //retryIf 重试条件
                            .retryIfException()
                            .retryIfRuntimeException()
                            .retryIfExceptionOfType(Exception.class)
                            .retryIfException(Predicates.equalTo(new Exception()))
                            .retryIfResult(Predicates.equalTo(false))
                            //等待策略：每次请求间隔1s
                            .withWaitStrategy(WaitStrategies.fixedWait(1, TimeUnit.SECONDS))
                            //停止策略 : 尝试请求5次
                            .withStopStrategy(StopStrategies.stopAfterAttempt(5))
                            .withRetryListener(new RetryListener() {
                                @Override
                                public <V> void onRetry(Attempt<V> attempt) {
                                    Log.i("Retry", "time:" + attempt.getAttemptNumber());
                                    Log.i("Retry", "time:" + attempt.getAttemptNumber());
                                    // 重试结果: 是异常终止, 还是正常返回
                                    Log.i(TAG, "hasException=" + attempt.hasException());
                                    Log.i(TAG, "hasResult=" + attempt.hasResult());

                                    // 是什么原因导致异常
                                    if (attempt.hasException()) {
                                        Log.i("Retry:", "causeBy=" + attempt.getExceptionCause().toString());
                                    } else {
                                        // 正常返回时的结果
                                        Log.i("Retry:", "result=" + attempt.getResult());
                                    }
                                    if (attempt.getAttemptNumber() == 5) {
                                        faceCompareFailed();
                                    }
                                }
                            })
                            .build();


                    myRetryer.call(new Callable<Boolean>() {
                        @Override
                        public Boolean call() throws Exception {
                            Log.i(TAG, "start compare");
                            // 初始化一个AipFace
                            AipFace client = new AipFace(Config.appId, Config.apiKey, Config.secretKey);

                            // image1/image2也可以为url或facetoken, 相应的imageType参数需要与之对应。
                            MatchRequest req1 = new MatchRequest(base64_1, "BASE64");
                            MatchRequest req2 = new MatchRequest(base64_2, "BASE64");
                            ArrayList<MatchRequest> requests = new ArrayList<MatchRequest>();
                            requests.add(req1);
                            requests.add(req2);

                            JSONObject res = client.match(requests);

                            System.out.println(String.valueOf(res));
                            BaiduResponse<FaceCompare> response = new Gson().fromJson(String.valueOf(res), new TypeToken<BaiduResponse<FaceCompare>>() {
                            }.getType());
                            double score = response.getResult().getScore();

                            Log.i(TAG, "fenshu:" + score);

                            if (score >= 80.0) {
                                faceCompareSuccess();
                                return true;
                            }
                            return false;
                        }
                    });
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (RetryException e) {
                    e.printStackTrace();
                }
            }
        }).start();;


    }

    /**
     * 解析比对结果
     */
    private void faceCompareSuccess() {
        runOnUiThread(() -> {
            Toast.makeText(mContext, "匹配成功", Toast.LENGTH_SHORT).show();
            setResult(1001);
            finish();
        });

    }

    /**
     * 解析比对结果
     */
    private void faceCompareFailed() {
        runOnUiThread(() -> {
            Toast.makeText(mContext, "匹配失败", Toast.LENGTH_SHORT).show();
            setResult(1002);
            finish();
        });

    }
}
