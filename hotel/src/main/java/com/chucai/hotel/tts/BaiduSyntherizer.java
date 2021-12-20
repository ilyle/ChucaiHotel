package com.chucai.hotel.tts;

import android.os.Handler;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.util.Log;
import android.util.Pair;

import com.baidu.tts.auth.AuthInfo;
import com.baidu.tts.client.SpeechError;
import com.baidu.tts.client.SpeechSynthesizeBag;
import com.baidu.tts.client.SpeechSynthesizer;
import com.baidu.tts.client.SpeechSynthesizerListener;
import com.baidu.tts.client.TtsMode;
import com.chucai.hotel.core.DeviceHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 该类是对SpeechSynthesizer的封装
 * <p>
 * Created by fujiayi on 2017/5/24.
 */

public class BaiduSyntherizer  {

    protected  SpeechSynthesizer mSpeechSynthesizer;
    protected String appId = "16188792";

    protected String appKey = "tvRlts8WOf0KBuevvxE5Gmmu";

    protected String secretKey = "ZNrMXHFG83AcdXdhEWi5xovCG5pdm1j3";

    // TtsMode.MIX; 离在线融合，在线优先； TtsMode.ONLINE 纯在线； 没有纯离线
    protected TtsMode ttsMode = TtsMode.MIX;
    private static final String TAG = "NonBlockSyntherizer";

    private static boolean isInitied = false;

    private String offlineVoice = OfflineResource.VOICE_FEMALE;

    static BaiduSyntherizer mBaiduSyntherizer=new BaiduSyntherizer();
    public void setOfflineVoice(String offlineVoice) {
        this.offlineVoice = offlineVoice;
        setParams(getParams());
    }


    public static BaiduSyntherizer newInstance() {

        return mBaiduSyntherizer;
    }

    /**
     * 注意该方法需要在新线程中调用。且该线程不能结束。详细请参见NonBlockSyntherizer的实现
     *
     * @return
     */
    public boolean init() {
        Log.i(TAG, "init: ");
        Map<String, String> params = getParams();
        InitConfig config = new InitConfig(appId, appKey, secretKey, ttsMode, params, null);
        boolean isMix = config.getTtsMode().equals(TtsMode.MIX);
        mSpeechSynthesizer = SpeechSynthesizer.getInstance();
        mSpeechSynthesizer.setContext(DeviceHelper.getContext());
        mSpeechSynthesizer.setSpeechSynthesizerListener(config.getListener());


        // 请替换为语音开发者平台上注册应用得到的App ID ,AppKey ，Secret Key ，填写在SynthActivity的开始位置
        mSpeechSynthesizer.setAppId(config.getAppId());
        mSpeechSynthesizer.setApiKey(config.getAppKey(), config.getSecretKey());
        setParams(config.getParams());
        if (isMix) {

            // 授权检测接口(只是通过AuthInfo进行检验授权是否成功。选择纯在线可以不必调用auth方法。
            AuthInfo authInfo = mSpeechSynthesizer.auth(config.getTtsMode());
            if (!authInfo.isSuccess()) {
                // 离线授权需要网站上的应用填写包名。本demo的包名是com.baidu.tts.sample，定义在build.gradle中
                String errorMsg = authInfo.getTtsError().getDetailMessage();
                return false;
            } else {
                Log.i(TAG, "init: 验证通过，离线正式授权文件存在。");
            }
        }

        // 初始化tts
        int result = mSpeechSynthesizer.initTts(config.getTtsMode());
        if (result != 0) {
            Log.i(TAG, "init: 【error】initTts 初始化失败 + errorCode：\"  "+result);
            isInitied = false;
            return false;
        }else {
            Log.i(TAG, "init: "+result);
        }
        // 此时可以调用 speak和synthesize方法
        Log.i(TAG, "合成引擎初始化成功");
        isInitied = true;
        return true;
    }

    /**
     * 合成并播放
     *
     * @param text 小于1024 GBK字节，即512个汉字或者字母数字
     * @return
     */
    public  int  speak(String text) {
        stop();
        SpannableStringBuilder spanned = (SpannableStringBuilder) Html.fromHtml(text);
        String string = spanned.toString();
        Log.i(TAG, "speak: "+string);

        return mSpeechSynthesizer.speak(string);
    }

    /**
     * 合成并播放
     *
     * @param text        小于1024 GBK字节，即512个汉字或者字母数字
     * @param utteranceId 用于listener的回调，默认"0"
     * @return
     */
    public  int speak(String text, String utteranceId) {
        stop();
        SpannableStringBuilder spanned = (SpannableStringBuilder) Html.fromHtml(text);
        String string = spanned.toString();
        Log.i(TAG, "speak: "+string);
        return mSpeechSynthesizer.speak(string, utteranceId);
    }




    public  int speak(String text, String data,final OnTargetSpeekListener  onTargetSpeekListener) {
        stop();
        SpannableStringBuilder spanned = (SpannableStringBuilder) Html.fromHtml(text);
        String string = spanned.toString();
        Log.i(TAG, "speak: "+string);
        if(onTargetSpeekListener==null){
            mSpeechSynthesizer.setSpeechSynthesizerListener(null);
        }
        mSpeechSynthesizer.setSpeechSynthesizerListener(new SpeechSynthesizerListener() {
            @Override
            public void onSynthesizeStart(String s) {

            }

            @Override
            public void onSynthesizeDataArrived(String s, byte[] bytes, int i, int i1) {

            }

            @Override
            public void onSynthesizeFinish(String s) {

            }

            @Override
            public void onSpeechStart(String s) {
                if(onTargetSpeekListener!=null){
                    onTargetSpeekListener.onSpeekStart();
                }
            }

            @Override
            public void onSpeechProgressChanged(String s, int i) {

            }

            @Override
            public void onSpeechFinish(String s) {
                if(onTargetSpeekListener!=null){
                    onTargetSpeekListener.onSpeekFinish();
                }
            }

            @Override
            public void onError(String s, SpeechError speechError) {

            }
        });
        return mSpeechSynthesizer.speak(string,data);
    }
    /**
     * 只合成不播放
     *
     * @param text
     * @return
     */
    public   int synthesize(String text) {
        return mSpeechSynthesizer.synthesize(text);
    }

    public  int synthesize(String text, String utteranceId) {
        return mSpeechSynthesizer.synthesize(text, utteranceId);
    }

    public int batchSpeak(List<Pair<String, String>> texts) {
        List<SpeechSynthesizeBag> bags = new ArrayList<SpeechSynthesizeBag>();
        for (Pair<String, String> pair : texts) {
            SpeechSynthesizeBag speechSynthesizeBag = new SpeechSynthesizeBag();
            speechSynthesizeBag.setText(pair.first);
            if (pair.second != null) {
                speechSynthesizeBag.setUtteranceId(pair.second);
            }
            bags.add(speechSynthesizeBag);

        }
        return mSpeechSynthesizer.batchSpeak(bags);
    }

    public  void setParams(Map<String, String> params) {
        if (params != null) {
            for (Map.Entry<String, String> e : params.entrySet()) {
                mSpeechSynthesizer.setParam(e.getKey(), e.getValue());
            }
        }
    }

    public void setVoiceParams(){

        setParams(getParams());
    }

    public  int pause() {
        return mSpeechSynthesizer.pause();
    }

    public  int resume() {
        return mSpeechSynthesizer.resume();
    }

    public  int stop() {
        return mSpeechSynthesizer.stop();
    }

    /**
     * 引擎在合成时该方法不能调用！！！
     * 注意 只有 TtsMode.MIX 才可以切换离线发音
     *
     * @return
     */
    public  int loadModel(String modelFilename, String textFilename) {
        int res  = mSpeechSynthesizer.loadModel(modelFilename, textFilename);
        return res;
    }

    /**
     * 设置播放音量，默认已经是最大声音
     * 0.0f为最小音量，1.0f为最大音量
     *
     * @param leftVolume  [0-1] 默认1.0f
     * @param rightVolume [0-1] 默认1.0f
     */
    public  void setStereoVolume(float leftVolume, float rightVolume) {
        mSpeechSynthesizer.setStereoVolume(leftVolume, rightVolume);
    }

    public   void release() {
        mSpeechSynthesizer.stop();
        mSpeechSynthesizer.release();
        mSpeechSynthesizer = null;
        isInitied = false;
    }


    /**
     * 合成的参数，可以初始化时填写，也可以在合成前设置。
     *
     * @return
     */
    protected Map<String, String> getParams() {
        Map<String, String> params = new HashMap<String, String>();
        // 以下参数均为选填
        // 设置在线发声音人： 0 普通女声（默认） 1 普通男声 2 特别男声 3 情感男声<度逍遥> 4 情感儿童声<度丫丫>
        params.put(SpeechSynthesizer.PARAM_SPEAKER, "0");
        // 设置合成的音量，0-9 ，默认 5

        // 设置合成的语速，0-9 ，默认 5
        params.put(SpeechSynthesizer.PARAM_SPEED, "5");
        // 设置合成的语调，0-9 ，默认 5
        params.put(SpeechSynthesizer.PARAM_PITCH, "5");
        params.put(SpeechSynthesizer.PARAM_MIX_MODE, SpeechSynthesizer.MIX_MODE_HIGH_SPEED_SYNTHESIZE);
        // 该参数设置为TtsMode.MIX生效。即纯在线模式不生效。
        // MIX_MODE_DEFAULT 默认 ，wifi状态下使用在线，非wifi离线。在线状态下，请求超时6s自动转离线
        // MIX_MODE_HIGH_SPEED_SYNTHESIZE_WIFI wifi状态下使用在线，非wifi离线。在线状态下， 请求超时1.2s自动转离线
        // MIX_MODE_HIGH_SPEED_NETWORK ， 3G 4G wifi状态下使用在线，其它状态离线。在线状态下，请求超时1.2s自动转离线
        // MIX_MODE_HIGH_SPEED_SYNTHESIZE, 2G 3G 4G wifi状态下使用在线，其它状态离线。在线状态下，请求超时1.2s自动转离线

        // 离线资源文件
        OfflineResource offlineResource = createOfflineResource(offlineVoice);
        // 声学模型文件路径 (离线引擎使用), 请确认下面两个文件存在
        if(offlineResource!=null) {
            params.put(SpeechSynthesizer.PARAM_TTS_TEXT_MODEL_FILE, offlineResource.getTextFilename());
            params.put(SpeechSynthesizer.PARAM_TTS_SPEECH_MODEL_FILE,
                    offlineResource.getModelFilename());
        }

//        if(DeviceHelper.getsMachineMessage()!=null) {
//            if (DeviceHelper.getsMachineMessage().getData() != null) {
//                int vol = 5;
//
//                if (DeviceHelper.getsMachineMessage().getData().getVoiceVolume() / 10 == 10) {
//                    vol = 9;
//                } else {
//                    if(DeviceHelper.getsMachineMessage().getData().getVoiceVolume()>0) {
//                        vol = DeviceHelper.getsMachineMessage().getData().getVoiceVolume() / 10;
//                    }
//                }
//                params.put(SpeechSynthesizer.PARAM_VOLUME, vol + "");
//
//            }
//
//        }
        return params;
    }

    public    void setVoice(int vol) {
        if (vol/ 10 == 10) {
            vol = 9;
        } else {
            vol =vol / 10;
        }
        mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_VOLUME, vol + "");
    }

    protected   OfflineResource createOfflineResource(String voiceType) {
        OfflineResource offlineResource = null;
        try {
            offlineResource = new OfflineResource(DeviceHelper.getContext(), voiceType);
        } catch (IOException e) {
            // IO 错误自行处理
            e.printStackTrace();
//            toPrint("【error】:copy files from assets failed." + e.getMessage());
        }
        return offlineResource;
    }

    public void initBaiDuVoice(final Handler mHandler){
        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean init=init();
                if(!init){
                    mHandler.sendEmptyMessageDelayed(100,15*1000);
                }
            }
        }).start();

    }

    public  static  interface OnTargetSpeekListener{
        void onSpeekStart();
        void onSpeekFinish();
    }
}
