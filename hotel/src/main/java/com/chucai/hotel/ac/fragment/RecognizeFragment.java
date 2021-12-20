package com.chucai.hotel.ac.fragment;

import android.Manifest;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.hardware.Camera;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.arcsoft.face.AgeInfo;
import com.arcsoft.face.ErrorInfo;
import com.arcsoft.face.FaceEngine;
import com.arcsoft.face.FaceFeature;
import com.arcsoft.face.FaceInfo;
import com.arcsoft.face.GenderInfo;
import com.arcsoft.face.LivenessInfo;
import com.arcsoft.face.enums.DetectFaceOrientPriority;
import com.arcsoft.face.enums.DetectMode;
import com.arcsoft.imageutil.ArcSoftImageFormat;
import com.arcsoft.imageutil.ArcSoftImageUtil;
import com.arcsoft.imageutil.ArcSoftImageUtilError;
import com.arcsoft.imageutil.ArcSoftRotateDegree;
import com.chucai.hotel.R;
import com.chucai.hotel.bean.FacePreviewInfo;
import com.chucai.hotel.util.util.ConfigUtil;
import com.chucai.hotel.util.util.camera.CameraHelper;
import com.chucai.hotel.util.util.camera.CameraListener;
import com.chucai.hotel.util.util.face.FaceHelper;
import com.chucai.hotel.util.util.face.FaceListener;
import com.chucai.hotel.util.util.face.LivenessType;
import com.chucai.hotel.util.util.face.RequestFeatureStatus;
import com.chucai.hotel.util.util.face.RequestLivenessStatus;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;


public class RecognizeFragment extends Fragment {
    private static final String TAG = "RegisterAndRecognize";
    private static final int MAX_DETECT_NUM = 10;
    private static Context mContext;

    /**
     * 当FR成功，活体未成功时，FR等待活体的时间
     */
    private static final int WAIT_LIVENESS_INTERVAL = 100;
    /**
     * 失败重试间隔时间（ms）
     */
    private static final long FAIL_RETRY_INTERVAL = 1000;
    /**
     * 出错重试最大次数
     */
    private static final int MAX_RETRY_TIME = 3;

    private CameraHelper cameraHelper;
    private Camera.Size previewSize;

    private CompositeDisposable getFeatureDelayedDisposables = new CompositeDisposable();
    private CompositeDisposable delayFaceTaskCompositeDisposable = new CompositeDisposable();

    /**
     * 优先打开的摄像头，本界面主要用于单目RGB摄像头设备，因此默认打开前置
     */
    private Integer rgbCameraID = Camera.CameraInfo.CAMERA_FACING_FRONT;

    /**
     * VIDEO模式人脸检测引擎，用于预览帧人脸追踪
     */
    private FaceEngine ftEngine;
    /**
     * 用于特征提取的引擎
     */
    private FaceEngine frEngine;
    /**
     * IMAGE模式活体检测引擎，用于预览帧人脸活体检测
     */
    private FaceEngine flEngine;

    private int ftInitCode = -1;
    private int frInitCode = -1;
    private int flInitCode = -1;
    private FaceHelper faceHelper;
    /**
     * 活体检测的开关
     */
    private boolean livenessDetect = true;
    /**
     * 注册人脸状态码，准备注册
     */
    private static final int REGISTER_STATUS_READY = 0;
    /**
     * 注册人脸状态码，注册中
     */
    private static final int REGISTER_STATUS_PROCESSING = 1;
    /**
     * 注册人脸状态码，注册结束（无论成功失败）
     */
    private static final int REGISTER_STATUS_DONE = 2;

    private int registerStatus = REGISTER_STATUS_DONE;
    /**
     * 用于记录人脸识别相关状态
     */
    private ConcurrentHashMap<Integer, Integer> requestFeatureStatusMap = new ConcurrentHashMap<>();
    /**
     * 用于记录人脸特征提取出错重试次数
     */
    private ConcurrentHashMap<Integer, Integer> extractErrorRetryMap = new ConcurrentHashMap<>();
    /**
     * 用于存储活体值
     */
    private ConcurrentHashMap<Integer, Integer> livenessMap = new ConcurrentHashMap<>();
    /**
     * 用于存储活体检测出错重试次数
     */
    private ConcurrentHashMap<Integer, Integer> livenessErrorRetryMap = new ConcurrentHashMap<>();

    /**
     * 相机预览显示的控件，可为SurfaceView或TextureView
     */
    private View previewView;

    private boolean startRec=false;
    /**
     * 绘制人脸框的控件
     */



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rec_face, container, false);
        startRec=false;
        initView(view);
        init(getActivity());
        return view;
    }

    //    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_register_and_recognize);
//        //保持亮屏
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            WindowManager.LayoutParams attributes = getWindow().getAttributes();
//            attributes.systemUiVisibility = View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
//            getWindow().setAttributes(attributes);
//        }
//
//        // Activity启动后就锁定为启动时的方向
//        //本地人脸库初始化
//
//        initView();
//    }

    private void initView(View  b) {
        previewView = b.findViewById(R.id.single_camera_texture_preview);


        initEngine();
        initCamera();
    }

    /**
     * 初始化引擎
     */
    private void initEngine() {
        ftEngine = new FaceEngine();
        ftInitCode = ftEngine.init(getActivity(), DetectMode.ASF_DETECT_MODE_VIDEO, ConfigUtil.getFtOrient(getActivity()),
                16, MAX_DETECT_NUM, FaceEngine.ASF_FACE_DETECT);

        frEngine = new FaceEngine();
        frInitCode = frEngine.init(getActivity(), DetectMode.ASF_DETECT_MODE_IMAGE, DetectFaceOrientPriority.ASF_OP_0_ONLY,
                16, MAX_DETECT_NUM, FaceEngine.ASF_FACE_RECOGNITION);

        flEngine = new FaceEngine();
        flInitCode = flEngine.init(getActivity(), DetectMode.ASF_DETECT_MODE_IMAGE, DetectFaceOrientPriority.ASF_OP_0_ONLY,
                16, MAX_DETECT_NUM, FaceEngine.ASF_LIVENESS);

        Log.i(TAG, "initEngine:  init: " + ftInitCode);

        if (ftInitCode != ErrorInfo.MOK) {
//            String error = getString(R.string.specific_engine_init_failed, "ftEngine", ftInitCode);
//            Log.i(TAG, "initEngine: " + error);
//            showToast(error);
        }
        if (frInitCode != ErrorInfo.MOK) {
//            String error = getString(R.string.specific_engine_init_failed, "frEngine", frInitCode);
//            Log.i(TAG, "initEngine: " + error);
//            showToast(error);
        }
        if (flInitCode != ErrorInfo.MOK) {
//            String error = getString(R.string.specific_engine_init_failed, "flEngine", flInitCode);
//            Log.i(TAG, "initEngine: " + error);
//            showToast(error);
        }
    }

    /**
     * 销毁引擎，faceHelper中可能会有特征提取耗时操作仍在执行，加锁防止crash
     */
    private void unInitEngine() {
        if (ftInitCode == ErrorInfo.MOK && ftEngine != null) {
            synchronized (ftEngine) {
                int ftUnInitCode = ftEngine.unInit();
                Log.i(TAG, "unInitEngine: " + ftUnInitCode);
            }
        }
        if (frInitCode == ErrorInfo.MOK && frEngine != null) {
            synchronized (frEngine) {
                int frUnInitCode = frEngine.unInit();
                Log.i(TAG, "unInitEngine: " + frUnInitCode);
            }
        }
        if (flInitCode == ErrorInfo.MOK && flEngine != null) {
            synchronized (flEngine) {
                int flUnInitCode = flEngine.unInit();
                Log.i(TAG, "unInitEngine: " + flUnInitCode);
            }
        }
    }


    @Override
    public void onDestroy() {

        if (cameraHelper != null) {
            cameraHelper.release();
            cameraHelper = null;
        }

        unInitEngine();
//        if (getFeatureDelayedDisposables != null) {
//            getFeatureDelayedDisposables.clear();
//        }
//        if (delayFaceTaskCompositeDisposable != null) {
//            delayFaceTaskCompositeDisposable.clear();
//        }
        if (faceHelper != null) {
            ConfigUtil.setTrackedFaceCount(getActivity(), faceHelper.getTrackedFaceCount());
            faceHelper.release();
            faceHelper = null;
        }

        super.onDestroy();
    }

    private void initCamera() {

        final FaceListener faceListener = new FaceListener() {
            @Override
            public void onFail(Exception e) {
                Log.e(TAG, "onFail: " + e.getMessage());
            }

            //请求FR的回调
            @Override
            public void onFaceFeatureInfoGet(@Nullable final FaceFeature faceFeature, final Integer requestId, final Integer errorCode) {
                //FR成功
                if (faceFeature != null) {
//                    Log.i(TAG, "onPreview: fr end = " + System.currentTimeMillis() + " trackId = " + requestId);
                    Integer liveness = livenessMap.get(requestId);
                    //不做活体检测的情况，直接搜索
                    if (!livenessDetect) {
//                        searchFace(faceFeature, requestId);
                        Log.i(TAG, "onFaceFeatureInfoGet: ");
                        if(onRecListener!=null){
                            startRec=true;
//                            onRecListener.onRecFace(getImage(getActivity(),faceFeature.getFeatureData().clone(),previewSize.width,previewSize.height));
                        }
                    }
                    //活体检测通过，搜索特征
                    else if (liveness != null && liveness == LivenessInfo.ALIVE) {
                        Log.i(TAG, "onFaceFeatureInfoGet: ");
//                        searchFace(faceFeature, requestId);
                        if(onRecListener!=null){
                            startRec=true;
//                            onRecListener.onRecFace(getImage(getActivity(),faceFeature.getFeatureData().clone(),previewSize.width,previewSize.height));
                        }
                    }
                    //活体检测未出结果，或者非活体，延迟执行该函数
                    else {
//                        if (requestFeatureStatusMap.containsKey(requestId)) {
//                            Observable.timer(WAIT_LIVENESS_INTERVAL, TimeUnit.MILLISECONDS)
//                                    .subscribe(new Observer<Long>() {
//                                        Disposable disposable;
//
//                                        @Override
//                                        public void onSubscribe(Disposable d) {
//                                            disposable = d;
//                                            getFeatureDelayedDisposables.add(disposable);
//                                        }
//
//                                        @Override
//                                        public void onNext(Long aLong) {
//                                            onFaceFeatureInfoGet(faceFeature, requestId, errorCode);
//                                        }
//
//                                        @Override
//                                        public void onError(Throwable e) {
//
//                                        }
//
//                                        @Override
//                                        public void onComplete() {
//                                            getFeatureDelayedDisposables.remove(disposable);
//                                        }
//                                    });
//                        }
                    }

                }
                //特征提取失败
                else {
//                    if (increaseAndGetValue(extractErrorRetryMap, requestId) > MAX_RETRY_TIME) {
//                        extractErrorRetryMap.put(requestId, 0);
//
//                        String msg;
//                        // 传入的FaceInfo在指定的图像上无法解析人脸，此处使用的是RGB人脸数据，一般是人脸模糊
//                        if (errorCode != null && errorCode == ErrorInfo.MERR_FSDK_FACEFEATURE_LOW_CONFIDENCE_LEVEL) {
//                            msg = getString(R.string.low_confidence_level);
//                        } else {
//                            msg = "ExtractCode:" + errorCode;
//                        }
//                        faceHelper.setName(requestId, getString(R.string.recognize_failed_notice, msg));
//                        // 在尝试最大次数后，特征提取仍然失败，则认为识别未通过
//                        requestFeatureStatusMap.put(requestId, RequestFeatureStatus.FAILED);
//                        retryRecognizeDelayed(requestId);
//                    } else {
//                        requestFeatureStatusMap.put(requestId, RequestFeatureStatus.TO_RETRY);
//                    }
                }
            }

            @Override
            public void onFaceLivenessInfoGet(@Nullable LivenessInfo livenessInfo, final Integer requestId, Integer errorCode) {
                if (livenessInfo != null) {
                    int liveness = livenessInfo.getLiveness();
                    livenessMap.put(requestId, liveness);
                    // 非活体，重试
                    if (liveness == LivenessInfo.NOT_ALIVE) {
                        faceHelper.setName(requestId, "NOT_ALIVE");
                        // 延迟 FAIL_RETRY_INTERVAL 后，将该人脸状态置为UNKNOWN，帧回调处理时会重新进行活体检测
                        retryLivenessDetectDelayed(requestId);
                    }
                } else {
                    if (increaseAndGetValue(livenessErrorRetryMap, requestId) > MAX_RETRY_TIME) {
                        livenessErrorRetryMap.put(requestId, 0);
                        String msg;
                        // 传入的FaceInfo在指定的图像上无法解析人脸，此处使用的是RGB人脸数据，一般是人脸模糊
//                        if (errorCode != null && errorCode == ErrorInfo.MERR_FSDK_FACEFEATURE_LOW_CONFIDENCE_LEVEL) {
//                            msg = getString(R.string.low_confidence_level);
//                        } else {
//                            msg = "ProcessCode:" + errorCode;
//                        }
//                        faceHelper.setName(requestId, getString(R.string.recognize_failed_notice, msg));
                        retryLivenessDetectDelayed(requestId);
                    } else {
                        livenessMap.put(requestId, LivenessInfo.UNKNOWN);
                    }
                }
            }


        };


        CameraListener cameraListener = new CameraListener() {
            @Override
            public void onCameraOpened(Camera camera, int cameraId, int displayOrientation, boolean isMirror) {
                Camera.Size lastPreviewSize = previewSize;
                previewSize = camera.getParameters().getPreviewSize();
                Log.i(TAG, "onCameraOpened: "+previewSize.width+"    "+previewSize.height);
                // 切换相机的时候可能会导致预览尺寸发生变化
                if (faceHelper == null ||
                        lastPreviewSize == null ||
                        lastPreviewSize.width != previewSize.width || lastPreviewSize.height != previewSize.height) {
                    Integer trackedFaceCount = null;
                    // 记录切换时的人脸序号
                    if (faceHelper != null) {
                        trackedFaceCount = faceHelper.getTrackedFaceCount();
                        faceHelper.release();
                    }
                    Log.i(TAG, "onCameraOpened: "+previewSize.width+"    "+previewSize.height);
                    faceHelper = new FaceHelper.Builder()
                            .ftEngine(ftEngine)
                            .frEngine(frEngine)
                            .flEngine(flEngine)
                            .frQueueSize(MAX_DETECT_NUM)
                            .flQueueSize(MAX_DETECT_NUM)
                            .previewSize(previewSize)
                            .faceListener(faceListener)
                            .trackedFaceCount(trackedFaceCount == null ? ConfigUtil.getTrackedFaceCount(getContext()) : trackedFaceCount)
                            .build();
                }
            }


            @Override
            public void onPreview(final byte[] nv21, Camera camera) {
//                if (faceRectView != null) {
//                    faceRectView.clearFaceInfo();
//                }
                List<FacePreviewInfo> facePreviewInfoList = faceHelper.onPreviewFrame(nv21);
//                if (facePreviewInfoList != null && faceRectView != null && drawHelper != null) {
//                    drawPreviewInfo(facePreviewInfoList);
//                }
//                registerFace(nv21, facePreviewInfoList);
//                clearLeftFace(facePreviewInfoList);
                if(startRec){
                    startRec=false;
                    if(onRecListener!=null){
                        onRecListener.onRecFace(getImage(getActivity(),nv21,previewSize.width,previewSize.height));
                    }

                }
                if (facePreviewInfoList != null && facePreviewInfoList.size() > 0 && previewSize != null) {
                    for (int i = 0; i < facePreviewInfoList.size(); i++) {
                        Integer status = requestFeatureStatusMap.get(facePreviewInfoList.get(i).getTrackId());
                        /**
                         * 在活体检测开启，在人脸识别状态不为成功或人脸活体状态不为处理中（ANALYZING）且不为处理完成（ALIVE、NOT_ALIVE）时重新进行活体检测
                         */
                        if (livenessDetect && (status == null || status != RequestFeatureStatus.SUCCEED)) {
                            Integer liveness = livenessMap.get(facePreviewInfoList.get(i).getTrackId());
                            if (liveness == null
                                    || (liveness != LivenessInfo.ALIVE && liveness != LivenessInfo.NOT_ALIVE && liveness != RequestLivenessStatus.ANALYZING)) {
                                livenessMap.put(facePreviewInfoList.get(i).getTrackId(), RequestLivenessStatus.ANALYZING);
                                faceHelper.requestFaceLiveness(nv21, facePreviewInfoList.get(i).getFaceInfo(), previewSize.width, previewSize.height, FaceEngine.CP_PAF_NV21, facePreviewInfoList.get(i).getTrackId(), LivenessType.RGB);
                            }
                        }
                        /**
                         * 对于每个人脸，若状态为空或者为失败，则请求特征提取（可根据需要添加其他判断以限制特征提取次数），
                         * 特征提取回传的人脸特征结果在{@link FaceListener#onFaceFeatureInfoGet(FaceFeature, Integer, Integer)}中回传
                         */
                        if (status == null
                                || status == RequestFeatureStatus.TO_RETRY) {
                            requestFeatureStatusMap.put(facePreviewInfoList.get(i).getTrackId(), RequestFeatureStatus.SEARCHING);
                            faceHelper.requestFaceFeature(nv21, facePreviewInfoList.get(i).getFaceInfo(), previewSize.width, previewSize.height, FaceEngine.CP_PAF_NV21, facePreviewInfoList.get(i).getTrackId());
//                            Log.i(TAG, "onPreview: fr start = " + System.currentTimeMillis() + " trackId = " + facePreviewInfoList.get(i).getTrackedFaceCount());
                        }
                    }
                }
            }

            @Override
            public void onCameraClosed() {
                Log.i(TAG, "onCameraClosed: ");
            }

            @Override
            public void onCameraError(Exception e) {
                Log.i(TAG, "onCameraError: " + e.getMessage());
            }

            @Override
            public void onCameraConfigurationChanged(int cameraID, int displayOrientation) {
//                if (drawHelper != null) {
//                    drawHelper.setCameraDisplayOrientation(displayOrientation);
//                }
                Log.i(TAG, "onCameraConfigurationChanged: " + cameraID + "  " + displayOrientation);
            }
        };
        WindowManager windowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        int myrotation = windowManager.getDefaultDisplay() .getRotation();
        Log.d("pyq chk","myrotation="+myrotation);

        cameraHelper = new CameraHelper.Builder()
                .previewViewSize(new Point(previewView.getMeasuredWidth(), previewView.getMeasuredHeight()))
                .rotation(90)
                .specificCameraId(rgbCameraID != null ? rgbCameraID : Camera.CameraInfo.CAMERA_FACING_FRONT)
                .isMirror(false)
                .previewOn(previewView)
                .cameraListener(cameraListener)
                .build();
        cameraHelper.init();
        cameraHelper.start();
    }

//    private void registerFace(final byte[] nv21, final List<FacePreviewInfo> facePreviewInfoList) {
//        if (registerStatus == REGISTER_STATUS_READY && facePreviewInfoList != null && facePreviewInfoList.size() > 0) {
//            registerStatus = REGISTER_STATUS_PROCESSING;
//            Observable.create(new ObservableOnSubscribe<Boolean>() {
//                @Override
//                public void subscribe(ObservableEmitter<Boolean> emitter) {
//
//                    boolean success = FaceServer.getInstance().registerNv21(RegisterAndRecognizeActivity.this, nv21.clone(), previewSize.width, previewSize.height,
//                            facePreviewInfoList.get(0).getFaceInfo(), "registered " + faceHelper.getTrackedFaceCount());
//                    emitter.onNext(success);
//                }
//            })
//                    .subscribeOn(Schedulers.computation())
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribe(new Observer<Boolean>() {
//                        @Override
//                        public void onSubscribe(Disposable d) {
//
//                        }
//
//                        @Override
//                        public void onNext(Boolean success) {
//                            String result = success ? "register success!" : "register failed!";
//                            showToast(result);
//                            registerStatus = REGISTER_STATUS_DONE;
//                        }
//
//                        @Override
//                        public void onError(Throwable e) {
//                            e.printStackTrace();
//                            showToast("register failed!");
//                            registerStatus = REGISTER_STATUS_DONE;
//                        }
//
//                        @Override
//                        public void onComplete() {
//
//                        }
//                    });
//        }
//    }

//    private void drawPreviewInfo(List<FacePreviewInfo> facePreviewInfoList) {
//        List<DrawInfo> drawInfoList = new ArrayList<>();
//        for (int i = 0; i < facePreviewInfoList.size(); i++) {
//            String name = faceHelper.getName(facePreviewInfoList.get(i).getTrackId());
//            Integer liveness = livenessMap.get(facePreviewInfoList.get(i).getTrackId());
//            Integer recognizeStatus = requestFeatureStatusMap.get(facePreviewInfoList.get(i).getTrackId());
//
//            // 根据识别结果和活体结果设置颜色
//            int color = RecognizeColor.COLOR_UNKNOWN;
//            if (recognizeStatus != null) {
//                if (recognizeStatus == RequestFeatureStatus.FAILED) {
//                    color = RecognizeColor.COLOR_FAILED;
//                }
//                if (recognizeStatus == RequestFeatureStatus.SUCCEED) {
//                    color = RecognizeColor.COLOR_SUCCESS;
//                }
//            }
//            if (liveness != null && liveness == LivenessInfo.NOT_ALIVE) {
//                color = RecognizeColor.COLOR_FAILED;
//            }
//
//            drawInfoList.add(new DrawInfo(drawHelper.adjustRect(facePreviewInfoList.get(i).getFaceInfo().getRect()),
//                    GenderInfo.UNKNOWN, AgeInfo.UNKNOWN_AGE, liveness == null ? LivenessInfo.UNKNOWN : liveness, color,
//                    name == null ? String.valueOf(facePreviewInfoList.get(i).getTrackId()) : name));
//        }
//        drawHelper.draw(faceRectView, drawInfoList);
//    }
//
//    @Override
//    void afterRequestPermission(int requestCode, boolean isAllGranted) {
//        if (requestCode == ACTION_REQUEST_PERMISSIONS) {
//            if (isAllGranted) {
//                initEngine();
//                initCamera();
//            } else {
//                showToast(getString(R.string.permission_denied));
//            }
//        }
//    }

    /**
     * 删除已经离开的人脸
     *
     * @param facePreviewInfoList 人脸和trackId列表
     */
    private void clearLeftFace(List<FacePreviewInfo> facePreviewInfoList) {
//        if (compareResultList != null) {
//            for (int i = compareResultList.size() - 1; i >= 0; i--) {
//                if (!requestFeatureStatusMap.containsKey(compareResultList.get(i).getTrackId())) {
//                    compareResultList.remove(i);
//                    adapter.notifyItemRemoved(i);
//                }
//            }
//        }
        if (facePreviewInfoList == null || facePreviewInfoList.size() == 0) {
            requestFeatureStatusMap.clear();
            livenessMap.clear();
            livenessErrorRetryMap.clear();
            extractErrorRetryMap.clear();
//            if (getFeatureDelayedDisposables != null) {
//                getFeatureDelayedDisposables.clear();
//            }
            return;
        }
        Enumeration<Integer> keys = requestFeatureStatusMap.keys();
        while (keys.hasMoreElements()) {
            int key = keys.nextElement();
            boolean contained = false;
            for (FacePreviewInfo facePreviewInfo : facePreviewInfoList) {
                if (facePreviewInfo.getTrackId() == key) {
                    contained = true;
                    break;
                }
            }
            if (!contained) {
                requestFeatureStatusMap.remove(key);
                livenessMap.remove(key);
                livenessErrorRetryMap.remove(key);
                extractErrorRetryMap.remove(key);
            }
        }


    }

//    private void searchFace(final FaceFeature frFace, final Integer requestId) {
//        Observable
//                .create(new ObservableOnSubscribe<CompareResult>() {
//                    @Override
//                    public void subscribe(ObservableEmitter<CompareResult> emitter) {
////                        Log.i(TAG, "subscribe: fr search start = " + System.currentTimeMillis() + " trackId = " + requestId);
//                        CompareResult compareResult = FaceServer.getInstance().getTopOfFaceLib(frFace);
////                        Log.i(TAG, "subscribe: fr search end = " + System.currentTimeMillis() + " trackId = " + requestId);
//                        emitter.onNext(compareResult);
//
//                    }
//                })
//                .subscribeOn(Schedulers.computation())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Observer<CompareResult>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//
//                    }
//
//                    @Override
//                    public void onNext(CompareResult compareResult) {
//                        if (compareResult == null || compareResult.getUserName() == null) {
//                            requestFeatureStatusMap.put(requestId, RequestFeatureStatus.FAILED);
//                            faceHelper.setName(requestId, "VISITOR " + requestId);
//                            return;
//                        }
//
////                        Log.i(TAG, "onNext: fr search get result  = " + System.currentTimeMillis() + " trackId = " + requestId + "  similar = " + compareResult.getSimilar());
//                        if (compareResult.getSimilar() > SIMILAR_THRESHOLD) {
//                            boolean isAdded = false;
//                            if (compareResultList == null) {
//                                requestFeatureStatusMap.put(requestId, RequestFeatureStatus.FAILED);
//                                faceHelper.setName(requestId, "VISITOR " + requestId);
//                                return;
//                            }
//                            for (CompareResult compareResult1 : compareResultList) {
//                                if (compareResult1.getTrackId() == requestId) {
//                                    isAdded = true;
//                                    break;
//                                }
//                            }
//                            if (!isAdded) {
//                                //对于多人脸搜索，假如最大显示数量为 MAX_DETECT_NUM 且有新的人脸进入，则以队列的形式移除
//                                if (compareResultList.size() >= MAX_DETECT_NUM) {
//                                    compareResultList.remove(0);
//                                    adapter.notifyItemRemoved(0);
//                                }
//                                //添加显示人员时，保存其trackId
//                                compareResult.setTrackId(requestId);
//                                compareResultList.add(compareResult);
//                                adapter.notifyItemInserted(compareResultList.size() - 1);
//                            }
//                            requestFeatureStatusMap.put(requestId, RequestFeatureStatus.SUCCEED);
//                            faceHelper.setName(requestId, getString(R.string.recognize_success_notice, compareResult.getUserName()));
//
//                        } else {
//                            faceHelper.setName(requestId, getString(R.string.recognize_failed_notice, "NOT_REGISTERED"));
//                            retryRecognizeDelayed(requestId);
//                        }
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        faceHelper.setName(requestId, getString(R.string.recognize_failed_notice, "NOT_REGISTERED"));
//                        retryRecognizeDelayed(requestId);
//                    }
//
//                    @Override
//                    public void onComplete() {
//
//                    }
//                });
//    }


    /**
     * 将准备注册的状态置为{@link #REGISTER_STATUS_READY}
     *
     * @param view 注册按钮
     */
    public void register(View view) {
        if (registerStatus == REGISTER_STATUS_DONE) {
            registerStatus = REGISTER_STATUS_READY;
        }
    }

//    /**
//     * 切换相机。注意：若切换相机发现检测不到人脸，则极有可能是检测角度导致的，需要销毁引擎重新创建或者在设置界面修改配置的检测角度
//     *
//     * @param view
//     */
//    public void switchCamera(View view) {
//        if (cameraHelper != null) {
//            boolean success = cameraHelper.switchCamera();
//            if (!success) {
//                showToast(getString(R.string.switch_camera_failed));
//            } else {
//                showLongToast(getString(R.string.notice_change_detect_degree));
//            }
//        }


    /**
     * 将map中key对应的value增1回传
     *
     * @param countMap map
     * @param key      key
     * @return 增1后的value
     */
    public int increaseAndGetValue(Map<Integer, Integer> countMap, int key) {
        if (countMap == null) {
            return 0;
        }
        Integer value = countMap.get(key);
        if (value == null) {
            value = 0;
        }
        countMap.put(key, ++value);
        return value;
    }

    /**
     * 延迟 FAIL_RETRY_INTERVAL 重新进行活体检测
     *
     * @param requestId 人脸ID
     */
    private void retryLivenessDetectDelayed(final Integer requestId) {
        Observable.timer(FAIL_RETRY_INTERVAL, TimeUnit.MILLISECONDS)
                .subscribe(new Observer<Long>() {
                    Disposable disposable;

                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                        delayFaceTaskCompositeDisposable.add(disposable);
                    }

                    @Override
                    public void onNext(Long aLong) {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        // 将该人脸状态置为UNKNOWN，帧回调处理时会重新进行活体检测
                        if (livenessDetect) {
                            faceHelper.setName(requestId, Integer.toString(requestId));
                        }
                        livenessMap.put(requestId, LivenessInfo.UNKNOWN);
                        delayFaceTaskCompositeDisposable.remove(disposable);
                    }
                });
    }

//    /**
//     * 延迟 FAIL_RETRY_INTERVAL 重新进行人脸识别
//     *
//     * @param requestId 人脸ID
//     */
//    private void retryRecognizeDelayed(final Integer requestId) {
//        requestFeatureStatusMap.put(requestId, RequestFeatureStatus.FAILED);
//        Observable.timer(FAIL_RETRY_INTERVAL, TimeUnit.MILLISECONDS)
//                .subscribe(new Observer<Long>() {
//                    Disposable disposable;
//
//                    @Override
//                    public void onSubscribe(Disposable d) {
//                        disposable = d;
//                        delayFaceTaskCompositeDisposable.add(disposable);
//                    }
//
//                    @Override
//                    public void onNext(Long aLong) {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        e.printStackTrace();
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        // 将该人脸特征提取状态置为FAILED，帧回调处理时会重新进行活体检测
//                        faceHelper.setName(requestId, Integer.toString(requestId));
//                        requestFeatureStatusMap.put(requestId, RequestFeatureStatus.TO_RETRY);
//                        delayFaceTaskCompositeDisposable.remove(disposable);
//                    }
//                });
//    }
    OnRecListener  onRecListener;

    public void setOnRecListener(OnRecListener onRecListener) {
        this.onRecListener = onRecListener;
    }

    public interface  OnRecListener{

        void onRecFace(Bitmap faceFeature);

    }
    private static FaceEngine faceEngine = null;

    /**
     * 初始化
     *
     * @param context 上下文对象
     * @return 是否初始化成功
     */
    public boolean init(Context context) {
        synchronized (this) {
            if (faceEngine == null && context != null) {
                mContext=context;
                faceEngine = new FaceEngine();
                int engineCode = faceEngine.init(context, DetectMode.ASF_DETECT_MODE_IMAGE, DetectFaceOrientPriority.ASF_OP_0_ONLY, 16, 1, FaceEngine.ASF_FACE_RECOGNITION | FaceEngine.ASF_FACE_DETECT);
                Log.i(TAG, "init:engineCode= "+engineCode);
                if (engineCode == ErrorInfo.MOK) {

                    return true;
                } else {
                    faceEngine = null;
                    Log.e(TAG, "init: failed! code = " + engineCode);
                    return false;
                }
            }
            return false;
        }
    }
    /**
     * 用于注册照片人脸
     *
     * @param context 上下文对象
     * @param bgr24   bgr24数据
     * @param width   bgr24宽度
     * @param height  bgr24高度
     * @param name    保存的名字，若为空则使用时间戳
     * @return 是否注册成功
     */
    public static Bitmap getImage(Context context, byte[] bgr24, int width, int height) {
            if (faceEngine == null || context == null || bgr24 == null || width % 4 != 0 ) {
                Log.e(TAG, "registerBgr24:  invalid params");
                return null;
            }
//
//            if (ROOT_PATH == null) {
//                ROOT_PATH = context.getFilesDir().getAbsolutePath();
//            }
//            //特征存储的文件夹
//            File featureDir = new File(ROOT_PATH + File.separator + SAVE_FEATURE_DIR);
//            if (!featureDir.exists() && !featureDir.mkdirs()) {
//                Log.e(TAG, "registerBgr24: can not create feature directory");
//                return false;
//            }
//            //图片存储的文件夹
//            File imgDir = new File(ROOT_PATH + File.separator + SAVE_IMG_DIR);
//            if (!imgDir.exists() && !imgDir.mkdirs()) {
//                Log.e(TAG, "registerBgr24: can not create image directory");
//                return false;
//            }
            //人脸检测
            List<FaceInfo> faceInfoList = new ArrayList<>();
        Log.i(TAG, "getImage: "+bgr24.length);
            int code = faceEngine.detectFaces(bgr24, width, height, FaceEngine.CP_PAF_BGR24, faceInfoList);
            if (code == ErrorInfo.MOK && faceInfoList.size() > 0) {
                FaceFeature faceFeature = new FaceFeature();

                //特征提取
                code = faceEngine.extractFaceFeature(bgr24, width, height, FaceEngine.CP_PAF_BGR24, faceInfoList.get(0), faceFeature);
                try {
                    //保存注册结果（注册图、特征数据）
                    if (code == ErrorInfo.MOK) {
                        //为了美观，扩大rect截取注册图
                        Rect cropRect = getBestRect(width, height, faceInfoList.get(0).getRect());
                        if (cropRect == null) {
                            Log.e(TAG, "registerBgr24: cropRect is null");
                            return null;
                        }

                        cropRect.left &= ~3;
                        cropRect.top &= ~3;
                        cropRect.right &= ~3;
                        cropRect.bottom &= ~3;



                        // 创建一个头像的Bitmap，存放旋转结果图
                        Bitmap headBmp = getHeadImage(bgr24, width, height, faceInfoList.get(0).getOrient(), cropRect, ArcSoftImageFormat.BGR24);


//                        ArrayList   faceRegisterInfoList = null;  // 内存中的数据同步
//                        if (faceRegisterInfoList == null) {
//                            faceRegisterInfoList = new ArrayList<>();
//                        }
//                        faceRegisterInfoList.add(new FaceRegisterInfo(faceFeature.getFeatureData(), userName));
                        return headBmp;
                    } else {
                        Log.e(TAG, "registerBgr24: extract face feature failed, code is " + code);
                        return null;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            } else {
                Log.e(TAG, "registerBgr24: no face detected, code is " + code);
                return null;
            }

    }

    /**
     * 将图像中需要截取的Rect向外扩张一倍，若扩张一倍会溢出，则扩张到边界，若Rect已溢出，则收缩到边界
     *
     * @param width   图像宽度
     * @param height  图像高度
     * @param srcRect 原Rect
     * @return 调整后的Rect
     */
    private static Rect getBestRect(int width, int height, Rect srcRect) {
        if (srcRect == null) {
            return null;
        }
        Rect rect = new Rect(srcRect);

        // 原rect边界已溢出宽高的情况
        int maxOverFlow = Math.max(-rect.left, Math.max(-rect.top, Math.max(rect.right - width, rect.bottom - height)));
        if (maxOverFlow >= 0) {
            rect.inset(maxOverFlow, maxOverFlow);
            return rect;
        }

        // 原rect边界未溢出宽高的情况
        int padding = rect.height() / 2;

        // 若以此padding扩张rect会溢出，取最大padding为四个边距的最小值
        if (!(rect.left - padding > 0 && rect.right + padding < width && rect.top - padding > 0 && rect.bottom + padding < height)) {
            padding = Math.min(Math.min(Math.min(rect.left, width - rect.right), height - rect.bottom), rect.top);
        }
        rect.inset(-padding, -padding);
        return rect;
    }

    /**
     * 截取合适的头像并旋转，保存为注册头像
     *
     * @param originImageData 原始的BGR24数据
     * @param width           BGR24图像宽度
     * @param height          BGR24图像高度
     * @param orient          人脸角度
     * @param cropRect        裁剪的位置
     * @param imageFormat     图像格式
     * @return 头像的图像数据
     */
    public static Bitmap getHeadImage(byte[] originImageData, int width, int height, int orient, Rect cropRect, ArcSoftImageFormat imageFormat) {
        byte[] headImageData = ArcSoftImageUtil.createImageData(cropRect.width(), cropRect.height(), imageFormat);
        int cropCode = ArcSoftImageUtil.cropImage(originImageData, headImageData, width, height, cropRect, imageFormat);
        if (cropCode != ArcSoftImageUtilError.CODE_SUCCESS) {
            throw new RuntimeException("crop image failed, code is " + cropCode);
        }

        //判断人脸旋转角度，若不为0度则旋转注册图
        byte[] rotateHeadImageData = null;
        int rotateCode;
        int cropImageWidth;
        int cropImageHeight;
        // 90度或270度的情况，需要宽高互换
        if (orient == FaceEngine.ASF_OC_90 || orient == FaceEngine.ASF_OC_270) {
            cropImageWidth = cropRect.height();
            cropImageHeight = cropRect.width();
        } else {
            cropImageWidth = cropRect.width();
            cropImageHeight = cropRect.height();
        }
        ArcSoftRotateDegree rotateDegree = null;
        switch (orient) {
            case FaceEngine.ASF_OC_90:
                rotateDegree = ArcSoftRotateDegree.DEGREE_270;
                break;
            case FaceEngine.ASF_OC_180:
                rotateDegree = ArcSoftRotateDegree.DEGREE_180;
                break;
            case FaceEngine.ASF_OC_270:
                rotateDegree = ArcSoftRotateDegree.DEGREE_90;
                break;
            case FaceEngine.ASF_OC_0:
            default:
                rotateHeadImageData = headImageData;
                break;
        }
        // 非0度的情况，旋转图像
        if (rotateDegree != null){
            rotateHeadImageData = new byte[headImageData.length];
            rotateCode = ArcSoftImageUtil.rotateImage(headImageData, rotateHeadImageData, cropRect.width(), cropRect.height(), rotateDegree, imageFormat);
            if (rotateCode != ArcSoftImageUtilError.CODE_SUCCESS) {
                throw new RuntimeException("rotate image failed, code is " + rotateCode);
            }
        }
        // 将创建一个Bitmap，并将图像数据存放到Bitmap中
        Bitmap headBmp = Bitmap.createBitmap(cropImageWidth, cropImageHeight, Bitmap.Config.RGB_565);
        if (ArcSoftImageUtil.imageDataToBitmap(rotateHeadImageData, headBmp, imageFormat) != ArcSoftImageUtilError.CODE_SUCCESS) {
            throw new RuntimeException("failed to transform image data to bitmap");
        }
        return headBmp;
    }
}
