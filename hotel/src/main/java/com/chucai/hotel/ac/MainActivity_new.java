package com.chucai.hotel.ac;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.services.weather.LocalWeatherLive;
import com.baidu.idl.face.platform.FaceSDKManager;
import com.baidu.idl.face.platform.utils.Base64Utils;
import com.baidu.idl.face.platform.utils.DensityUtils;
import com.chucai.hotel.R;
import com.chucai.hotel.ac.fragment.CheckRoomFragment;
import com.chucai.hotel.bean.BookMessage;
import com.chucai.hotel.bean.IdCardUser;
import com.chucai.hotel.util.Base64IntentUtils;
import com.donkingliang.banner.CustomBanner;
import com.huaka.usb.sdk.HandlerMsg;
import com.huaka.usb.sdk.HkIDCardInfo;
import com.huaka.usb.sdk.HkOtgApi;
import com.weteco.android.IDCReader.IDCReaderSDK;
import com.xtf.xtflib.widget.bam.BamConstraintLayout;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.os.Vibrator;
import android.net.Uri;
import android.widget.Toast;

public class MainActivity_new extends Activity {
    private static final String TAG = "MainActivity";
    private static final long WEATHER_TIME = 30 * 60 * 1000;
    private CustomBanner banner;
    private BamConstraintLayout xianchangRuzhuLayout;
    private BamConstraintLayout dingdanRuzhuLayout;
    private BamConstraintLayout fangkedengjiLayout;
    private BamConstraintLayout xuzhujiezhangLayout;
    private RecyclerView mRec;
    HkOtgApi api;
    private List<IdCardUser> idCardUsers = new ArrayList<>();
    IdCardUser curIdCardUser;
    private ConstraintLayout timeLayout;
    private ImageView tianqiIcon;
    private TextView tianQiName;
    private TextView dateTv;
    private TextView timeTv;
    private static int FAN_YE_TIME = 5000;
    public static SimpleDateFormat dayTimeFormate = new SimpleDateFormat("yyyy/MM/dd");
    public static SimpleDateFormat secTimeFormate = new SimpleDateFormat("HH:mm:ss");
    public static LocalWeatherLive slocalWeatherLive;
    private int clickTimes;
    private long lastTouchTime;
    CheckRoomFragment showCheckRoomFragement;
    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;

    public static LocalWeatherLive getSlocalWeatherLive() {
        return slocalWeatherLive;
    }

    private WebView mWebView;
    private Vibrator vibrator;
    private ValueCallback mUploadMessage;
    static String url = "http://39.99.54.163:8879"; //
    private ValueCallback<Uri> filePathsCallback;
    private final static int FILECHOOSER_RESULTCODE = 1;

    private Context mContext;

    @SuppressLint("JavascriptInterface")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_new);

        mContext = this;

        String urlString = url + "/FoFuCheckin/index.jsp";

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        mWebView = (WebView) findViewById(R.id.webView);

        WebSettings webSettings = mWebView.getSettings();
        webSettings.setSavePassword(false);
        webSettings.setSaveFormData(false);
        // 下面的一句话是必须的，必须要打开javaScript不然所做一切都是徒劳的
        webSettings.setJavaScriptEnabled(true);
        //webSettings.setSupportZoom(false);
        //webSettings.setBuiltInZoomControls(true);
        webSettings.setBuiltInZoomControls(false);
        webSettings.setSupportZoom(false);
        webSettings.setDisplayZoomControls(false);
        //以下两条设置可以使页面适应手机屏幕的分辨率，完整的显示在屏幕上
        //设置是否使用WebView推荐使用的窗口
        webSettings.setUseWideViewPort(true);
        //设置WebView加载页面的模式
        webSettings.setLoadWithOverviewMode(true);

        webSettings.setDomStorageEnabled(true);
        webSettings.setDefaultTextEncodingName("UTF-8");
        webSettings.setAllowContentAccess(true); // 是否可访问Content Provider的资源，默认值 true
        webSettings.setAllowFileAccess(true);    // 是否可访问本地文件，默认值 true
        // 是否允许通过file url加载的Javascript读取本地文件，默认值 false
        webSettings.setAllowFileAccessFromFileURLs(false);
        // 是否允许通过file url加载的Javascript读取全部资源(包括文件,http,https)，默认值 false
        webSettings.setAllowUniversalAccessFromFileURLs(false);


        mWebView.setWebChromeClient(new MyWebChromeClient());

        // 添加js调用接口

        mWebView.addJavascriptInterface(this, "android");
        mWebView.clearCache(true);
        mWebView.loadUrl(urlString);
        mWebView.setWebViewClient(new WebViewClient() {
            // 重写此方法表明点击网页里面的链接还是在当前的webview里跳转，不跳到浏览器那边
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            /**
             * 网页加载结束的时候执行的回调方法
             * @param view
             * @param url
             */
        });
        //重置alert
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onJsAlert(WebView webView, String url, String message, JsResult result) {
                return super.onJsAlert(webView, url, message, result);
            }

            //For Android 4.1
            public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
                System.out.println("openFileChooser:3----------");
                mUploadMessage = uploadMsg;
                uploadMsg.onReceiveValue(null);
                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                i.addCategory(Intent.CATEGORY_OPENABLE);
                i.setType("*/*");
                startActivityForResult(Intent.createChooser(i, "File Chooser"), FILECHOOSER_RESULTCODE);

            }

            public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback) {
                System.out.println("----***--onShowFileChooser*****------");
                return false;
            }

        });
        //开启身份证读卡
        System.out.println("hyj-----开始咯1");
        api = new HkOtgApi(mHandler, this);
        System.out.println("hyj-----开始咯2");
        int ret = api.init();// 因为第一次需要点击授权，所以第一次点击时候的返回是-1所以我利用了广播接受到授权后用handler发送消息
        System.out.println("hyj-----开始咯3：" + ret);


    }

    //  测试方法
    @JavascriptInterface
    public void test() {
        Toast.makeText(MainActivity_new.this, "test", Toast.LENGTH_SHORT).show();
    }

    //  测试方法
    @JavascriptInterface
    public void sfzcheck() {
        Toast.makeText(MainActivity_new.this, "开始读卡", Toast.LENGTH_SHORT).show();
        //开始读卡
        m_Auto = true;
        new CPUThread().start();
    }

    //  测试方法
    @JavascriptInterface
    public void test1() {
        startActivity(new Intent(MainActivity_new.this, BookRoomActivity.class));
    }

    AMapLocationClient mLocationClient;


    public void showCheckRoomFagement(BookMessage.DataDTO orderListDTO) {
        showCheckRoomFragement.setData(0, orderListDTO);
       /* getSupportFragmentManager().beginTransaction().replace(R.id.content, showCheckRoomFragement).commit();
        mTopWeatherLayout.setStep(3);*/

    }

    @Override
    protected void onResume() {
        super.onResume();


    }

    @Override
    protected void onPause() {
        super.onPause();


    }


    final class MyWebChromeClient extends WebChromeClient {
        @Override
        public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
            System.out.println("***" + message);
            result.confirm();
            return super.onJsConfirm(view, "", message, result);
        }
    }

    boolean m_Auto = false;
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            System.out.print("handleMessagehyj--msg.what--: " + msg.what);
            if (msg.what == HandlerMsg.READ_SUCCESS) {
                Bundle data = msg.getData();
                String data1 = data.getString("data");
                Log.i(TAG, "handleMessage: " + data1);
                System.out.print("handleMessagehyj----: " + data1);
                Toast.makeText(MainActivity_new.this, data1, Toast.LENGTH_SHORT).show();

                // 储存身份照
                Bitmap bitmap = (Bitmap) msg.obj;
                Base64IntentUtils.getInstance().setmLocalBase64(bitmapToBase64(bitmap));

                /*HkIDCardInfo cardInfo = JSON.parseObject(data1, HkIDCardInfo.class);
                curIdCardUser=new IdCardUser();
                curIdCardUser.setHkIDCardInfo(cardInfo);
                curIdCardUser.setBitmap((Bitmap) msg.obj);
                m_Auto = false;
                boolean exist=false;
                for(IdCardUser user:idCardUsers){
                    if(user.getHkIDCardInfo().getIDCard().equals(cardInfo.getIDCard())){
                        exist=true;
                        break;
                    }
                }
                if(!exist) {
                    startRecFace();
                    Log.i(TAG, "handleMessage: startRecFace");
                }else {
                    ToastUtil.showShort(getContext(),curIdCardUser.getHkIDCardInfo().getPeopleName()+"已经的登记");
                    new CheckRoomFragment.CPUThread().start();
                }*/

            } else if (msg.what == 100) {
//                FaceFilter.TrackedModel trackedModel= (FaceFilter.TrackedModel) msg.obj;
//                curIdCardUser.setNewBit(trackedModel.cropFace());
//                if(curIdCardUser!=null){
//                    new Thread(new Runnable() {
//                        @Override
//                        public void run() {
//                            faceCompare(mHandler,trackedModel.cropFace(),curIdCardUser.getBitmap(),curIdCardUser);
//                        }
//                    }).start();
//                }
            } else if (msg.what == 93) {
               /* m_Auto = true;
                if(idCardUsers==null){
                    idCardUsers=new ArrayList<>();
                }

                idCardUsers.add(curIdCardUser);
                myAdapter.setNewData(idCardUsers);
                new CheckRoomFragment.CPUThread().start();
                stopRecFace();
*/


            } else if (msg.what == 94) {
                /*ToastUtil.showShort(getContext(),"人证比对失败,请重试");
//                m_Auto = true;
//                new CPUThread().start();
                mHandler.sendEmptyMessageDelayed(33,4000);*/
            } else if (msg.what == 33) {
                /* recFragment.setTakePic();*/
            }


        }
    };

    class CPUThread extends Thread {
        public CPUThread() {
            super();
        }

        int readCount = 0;

        @Override
        public void run() {

            super.run();
            HkIDCardInfo ici;
            Message msg;
            while (m_Auto) {
                readCount = 0;
/*
                if(readCount%20==0){
                    api.unInit();
                    int ret = api.init();// 因为第一次需要点击授权，所以第一次点击时候的返回是-1所以我利用了广播接受到授权后用handler发送消息
                }

 */
                // ///////////////循环读卡，不拿开身份证
                if (api.NotAuthenticate(200, 200) != 1) {
                    // ////////////////循环读卡，需要重新拿开身份证
                    // if (api.Authenticate(200, 200) != 1) {
                    msg = Message.obtain();
                    msg.what = HandlerMsg.READ_ERROR;
                    mHandler.sendMessage(msg);
                    Log.i(TAG, "run: read error");
                } else {
                    ici = new HkIDCardInfo();
                    if (api.ReadCard(ici, 200, 1300) == 1) {
                        msg = Message.obtain();

                        Bundle data = msg.getData();
                        data.putString("data", JSON.toJSONString(ici));

                        byte[] bmp = new byte[38862];
                        byte[] bas64code = new byte[51817];
                        int ret = IDCReaderSDK.wltGetbmp(ici.getwltdata(), bmp, bas64code);
                        if (ret != 1) {// 读卡失败
                            // msg.what = USBMsg.ReadIdCardFail;

                            return;
                        }
                        try {
                            Log.i(TAG, "run: -----------------------------------" + ret);
/*
                            String base64 = new String(bas64code);
                            byte[] decodedString = Base64.decode(base64, Base64.DEFAULT);
                            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
 */
                            Bitmap decodedByte = BitmapFactory.decodeByteArray(bmp, 0, bmp.length);
                            msg.obj = decodedByte;

                            msg.setData(data);

                            msg.what = HandlerMsg.READ_SUCCESS;
                            mHandler.sendMessage(msg);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                SystemClock.sleep(300);
                msg = Message.obtain();
                msg.what = HandlerMsg.READ_ERROR;
                mHandler.sendMessage(msg);
                SystemClock.sleep(300);
                readCount++;
            }

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 100) {
            if (resultCode == 1001) {
                Toast.makeText(mContext, "识别成功", Toast.LENGTH_SHORT).show();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 加载本地图片，模拟身份证识别获取证件照
     */
    private void loadLocal() {
        try {
            StringBuilder bmpStr = new StringBuilder();
            InputStream inputStream = getResources().getAssets().open("local.base64");
            byte[] bytes = new byte[1024];
            int len;
            while ((len = inputStream.read(bytes)) != -1) {
                bmpStr.append(new String(bytes, 0, len));
            }
            Base64IntentUtils.getInstance().setmLocalBase64(bmpStr.toString());
//            Bitmap bmp = base64ToBitmap(bmpStr.toString());
//            bmp = FaceSDKManager.getInstance().scaleImage(bmp,
//                    DensityUtils.dip2px(getApplicationContext(), 128),
//                    DensityUtils.dip2px(getApplicationContext(), 128));
//            mLocal.setImageBitmap(bmp);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void startFaceCollect() {
        // 测试获取本地数据
        // TODO：演示请屏蔽
        loadLocal();
        // 开始人脸采集和识别
        Intent intent = new Intent(this, FaceDetectExaActivity.class);
        startActivityForResult(intent, 100);
    }

    private Bitmap base64ToBitmap(String base64Data) {
        byte[] bytes = Base64Utils.decode(base64Data, Base64Utils.NO_WRAP);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    private String bitmapToBase64(Bitmap bitmap) {
        ByteArrayOutputStream baos = null;
        try {
            baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            baos.flush();
            baos.close();
            byte[] bitmapBytes = baos.toByteArray();
            return Base64.encodeToString(bitmapBytes, Base64.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            if (baos != null) {
                baos.flush();
                baos.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public void test(View view) {
        startFaceCollect();
    }
}
