//package com.chucai.hotel.ac.fragment;
//
//import android.annotation.SuppressLint;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.hardware.Camera;
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.Message;
//import android.os.SystemClock;
//import android.support.annotation.NonNull;
//import android.support.annotation.Nullable;
//import android.support.v4.app.Fragment;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.util.Base64;
//import android.util.DisplayMetrics;
//import android.util.Log;
//import android.view.Display;
//import android.view.Gravity;
//import android.view.LayoutInflater;
//import android.view.SurfaceHolder;
//import android.view.SurfaceView;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.FrameLayout;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.alibaba.fastjson.JSON;
//import com.baidu.aip.FaceDetectRoundView;
//import com.baidu.aip.FaceDetector;
//import com.baidu.aip.ImageFrame;
//import com.baidu.aip.face.ArgbPool;
//import com.baidu.aip.face.FaceDetectManager;
//import com.baidu.aip.face.FaceFilter;
//import com.baidu.aip.face.ImageSource;
//import com.baidu.aip.utils.CameraUtils;
//import com.baidu.idl.facesdk.FaceInfo;
//import com.chad.library.adapter.base.BaseQuickAdapter;
//import com.chad.library.adapter.base.BaseViewHolder;
//import com.chucai.hotel.R;
//import com.huaka.usb.sdk.HandlerMsg;
//import com.huaka.usb.sdk.HkIDCardInfo;
//import com.huaka.usb.sdk.HkOtgApi;
//import com.weteco.android.IDCReader.IDCReaderSDK;
//import com.xtf.xtflib.widget.common.SpaceItemDecoration;
//
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.List;
//
//public class CheckRoomFragment_bk extends Fragment implements SurfaceHolder.Callback ,Camera.PreviewCallback,
//        Camera.ErrorCallback{
//    private static final String TAG = "CheckRoomFragment";
//    private ImageView mImage;
//    private TextView mStateTv;
//    private RecyclerView mRec;
//
//
//    public static boolean readPhoneCard = false;
//
//
//    protected HashMap<String, String> mBase64ImageMap = new HashMap<String, String>();
//    protected boolean mIsCreateSurface = false;
//    protected volatile boolean mIsCompletion = false;
//    // 相机
//    protected Camera mCamera;
//    protected Camera.Parameters mCameraParam;
//    protected int mCameraId;
//
//    protected int mPreviewDegree;
//    // 人脸信息
//    private int width;
//    private int height;
//
//    boolean m_Auto = false;
//    long startTime;
//    @SuppressLint("HandlerLeak")
//    private Handler mHandler = new Handler() {
//
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            if (msg.what == HandlerMsg.READ_SUCCESS) {
//                Bundle data = msg.getData();
//                String data1 = data.getString("data");
//                Log.i(TAG, "handleMessage: "+data1);
//                HkIDCardInfo cardInfo=JSON.parseObject(data1,HkIDCardInfo.class);
//                m_Auto=false;
//                mDetectFaceRound.setVisibility(View.VISIBLE);
//                mDetectSurfaceLayout.setVisibility(View.VISIBLE);
//                mImage.setVisibility(View.GONE);
//
//
//            }
//
//        }
//    };
//    private TextView mTipsTv;
//    private FrameLayout mDetectSurfaceLayout;
//    private FaceDetectRoundView mDetectFaceRound;
//    SurfaceView mSurfaceView;
//    SurfaceHolder mSurfaceHolder;
//    ImageSource imageRes;
//    // 显示Size
////    protected int mDisplayWidth = 607;
////    protected int mDisplayHeight = 1080;
////    protected int mSurfaceWidth = 0;
////    protected int mSurfaceHeight = 0;
////    protected int mPreviewWidth=607;
////    protected int mPreviewHight=1080;
//
//    HkOtgApi api;
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View view = View.inflate(getContext(), R.layout.fragment_check_room, null);
//        api = new HkOtgApi(mHandler, getActivity());
//        int ret = api.init();// 因为第一次需要点击授权，所以第一次点击时候的返回是-1所以我利用了广播接受到授权后用handler发送消息
//        initView(view);
//
//
//        mSurfaceView = new SurfaceView(getActivity());
//        mSurfaceHolder = mSurfaceView.getHolder();
//        mSurfaceHolder.setSizeFromLayout();
//        mSurfaceHolder.addCallback(this);
//        mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
//        DisplayMetrics dm = new DisplayMetrics();
//        Display display = getActivity().getWindowManager().getDefaultDisplay();
//        display.getMetrics(dm);
////        mDisplayWidth = dm.widthPixels;
////        mDisplayHeight = dm.heightPixels;
////        int w = mDisplayWidth;
////        int h = mDisplayHeight;
//        mDetectFaceRound.setIsActiveLive(false);
//        FrameLayout.LayoutParams cameraFL = new FrameLayout.LayoutParams(
//             1080, 607,Gravity.TOP|Gravity.CENTER);
//        cameraFL.topMargin=100;
//        mSurfaceView.setLayoutParams(cameraFL);
//        mDetectSurfaceLayout.addView(mSurfaceView);
////        Camera.Parameters parameters = mCamera.getParameters();
////        parameters.setPreviewSize(720,1280);
////        readPhoneCard=true;
////
////        startReadPhoneCard(mHandler);
//        startPreview();
//        mDetectFaceRound.setVisibility(View.VISIBLE);
//        mDetectSurfaceLayout.setVisibility(View.VISIBLE);
//        mImage.setVisibility(View.GONE);
//        return view;
//    }
//    FaceDetectManager faceDetectManager;
//    private void initView(View view) {
//        faceDetectManager = new FaceDetectManager(getActivity());
//        mImage = (ImageView) view.findViewById(R.id.image);
//        mStateTv = (TextView) view.findViewById(R.id.state_tv);
//        mRec = (RecyclerView) view.findViewById(R.id.rec);
//
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
//        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
//        mRec.setLayoutManager(linearLayoutManager);
//        mRec.addItemDecoration(new SpaceItemDecoration(getContext().getResources().getDimension(R.dimen.dp_27), 0));
//
//
//        mTipsTv = (TextView)view. findViewById(R.id.tips_tv);
//        mDetectSurfaceLayout = (FrameLayout) view.findViewById(R.id.detect_surface_layout);
//        mDetectFaceRound = (FaceDetectRoundView) view.findViewById(R.id.detect_face_round);
//        m_Auto=true;
//        new CPUThread().start();
//        faceDetectManager.setOnFaceDetectListener(new FaceDetectManager.OnFaceDetectListener() {
//            @Override
//            public void onDetectFace(int status, FaceInfo[] infos, ImageFrame imageFrame) {
//                Log.i(TAG, "onDetectFace: "+status);
//            }
//        });
//        faceDetectManager.setOnTrackListener(new FaceFilter.OnTrackListener() {
//            @Override
//            public void onTrack(FaceFilter.TrackedModel trackedModel) {
//                Log.i(TAG, "onTrack: ");
//            }
//        });
//
//         imageRes=       new ImageSource();
//        imageRes.setPreviewView(mSurfaceView);
//        faceDetectManager.setImageSource(imageRes);
////        faceDetectManage
//
//    }
//
//
//    class MyAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
//
//
//        public MyAdapter(@Nullable List<String> data) {
//            super(data);
//            mLayoutResId = R.layout.adapter_fragment_check_room_item;
//        }
//
//        @Override
//        protected void convert(@NonNull BaseViewHolder helper, String item) {
//
//        }
//    }
//
//
//    public static void startReadPhoneCard(final Handler mHandler) {
//
//    }
//
//
//    public void stopReadCard() {
//        readPhoneCard = false;
//    }
//
//
//
//    private Camera open() {
//        Camera camera;
//        int numCameras = Camera.getNumberOfCameras();
//        if (numCameras == 0) {
//            return null;
//        }
//
//        int index = 0;
//        while (index < numCameras) {
//            Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
//            Camera.getCameraInfo(index, cameraInfo);
//            if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
//                break;
//            }
//            index++;
//        }
//
//        if (index < numCameras) {
//            camera = Camera.open(index);
//            mCameraId = index;
//        } else {
//            camera = Camera.open(0);
//            mCameraId = 0;
//        }
//        return camera;
//    }
//
//    protected void startPreview() {
//        if (mSurfaceView != null && mSurfaceView.getHolder() != null) {
//            mSurfaceHolder = mSurfaceView.getHolder();
//            mSurfaceHolder.addCallback(this);
//            Log.i(TAG, "startPreview: null");
//        }
//
//        if (mCamera == null) {
//            try {
//                mCamera = open();
//            } catch (RuntimeException e) {
//                e.printStackTrace();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//
//        if (mCamera == null) {
//            return;
//        }
////        if (mCameraParam == null) {
////            mCameraParam = mCamera.getParameters();
////        }
////        mCameraParam.setPreviewSize(mPreviewWidth,mPreviewHight);
//
////        mCameraParam.setPictureFormat(PixelFormat.JPEG);
////        int degree = displayOrientation(getActivity());
////        mCamera.setDisplayOrientation(degree);
//        // 设置后无效，camera.setDisplayOrientation方法有效
////        mCameraParam.set("rotation", degree);
////        mPreviewDegree = degree;
//
////        Point point =
////                new Point(mDisplayWidth, mDisplayHeight);
////        mPreviewWidth = point.x;
////        mPreviewHight = point.y;
////        // Preview 768,432
////        mPreviewRect.set(0, 0, mPreviewWidth, mPreviewHight);
//
////        mCameraParam.setPreviewSize(mPreviewWidth, mPreviewHight);
////        mCamera.setParameters(mCameraParam);
//
//        try {
//
//            mCamera.stopPreview();
//            mCamera.setErrorCallback(this);
//            mCamera.setPreviewCallback(this);
//            mCamera.startPreview();
//            Log.i(TAG, "startPreview: --------------------------");
//            faceDetectManager.start();
//        } catch (RuntimeException e) {
//            e.printStackTrace();
//            CameraUtils.releaseCamera(mCamera);
//            mCamera = null;
//        } catch (Exception e) {
//            e.printStackTrace();
//            CameraUtils.releaseCamera(mCamera);
//            mCamera = null;
//        }
//
//    }
//
//    protected void stopPreview() {
//        if (mCamera != null) {
//            try {
//                mCamera.setErrorCallback(null);
//                mCamera.setPreviewCallback(null);
//                mCamera.stopPreview();
//            } catch (RuntimeException e) {
//                e.printStackTrace();
//            } catch (Exception e) {
//                e.printStackTrace();
//            } finally {
//                CameraUtils.releaseCamera(mCamera);
//                mCamera = null;
//            }
//        }
//        if (mSurfaceHolder != null) {
//            mSurfaceHolder.removeCallback(this);
//        }
//
//
//    }
//
//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        api.unInit();
//        stopPreview();
//    }
//
//
//    @Override
//    public void surfaceCreated(SurfaceHolder holder) {
//        mIsCreateSurface = true;
//        Log.i(TAG, "surfaceCreated: ");
//    }
//
//    @Override
//    public void surfaceChanged(SurfaceHolder holder,
//                               int format,
//                               int width,
//                               int height) {
//        if (holder.getSurface() == null) {
//            return;
//        }
//        try {
//            if(mCamera!=null) {
//                mCamera.setPreviewDisplay(holder);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        Log.i(TAG, "surfaceChanged: ");
//
//    //
//    }
//
//    @Override
//    public void surfaceDestroyed(SurfaceHolder holder) {
//        mIsCreateSurface = false;
//    }
//    private ArgbPool argbPool = new ArgbPool();
//    @Override
//    public void onPreviewFrame(byte[] data, Camera camera) {
//
//        if (mIsCompletion) {
//            return;
//        }
//
//        int[] argb = argbPool.acquire(width, height);
////
////                            if (argb == null || argb.length != width * height) {
////                                argb = new int[width * height];
////                            }
//        FaceDetector.yuvToARGB(data, width, height, argb, 0, 0);
////        Log.i(TAG, "onPreviewFrame: -------");
//        ImageFrame frame = new ImageFrame();
//        frame.setArgb(argb);
//        frame.setWidth(width);
//        frame.setHeight(height);
//        frame.setPool(argbPool);
////        ArrayList<OnFrameAvailableListener> listeners = getListeners();
////        for (OnFrameAvailableListener listener : listeners) {
////            listener.onFrameAvailable(frame);
////        }
//
//    }
//
//    @Override
//    public void onError(int error, Camera camera) {
//        Log.i(TAG, "onError: "+error);
//    }
//
//
//
//
//
//
//     class CPUThread extends Thread {
//        public CPUThread() {
//            super();
//        }
//
//        @Override
//        public void run() {
//            super.run();
//            HkIDCardInfo ici;
//            Message msg;
//            while (m_Auto) {
//                // ///////////////循环读卡，不拿开身份证
//                if (api.NotAuthenticate(200, 200) != 1) {
//                    // ////////////////循环读卡，需要重新拿开身份证
//                    // if (api.Authenticate(200, 200) != 1) {
//                    msg = Message.obtain();
//                    msg.what = HandlerMsg.READ_ERROR;
//                    mHandler.sendMessage(msg);
//                } else {
//                    ici = new HkIDCardInfo();
//                    if (api.ReadCard(ici, 200, 1300) == 1) {
//                        msg = Message.obtain();
//
//                        Bundle data = msg.getData();
//                        data.putString("data", JSON.toJSONString(ici));
//
//                        byte [] bmp = new  byte[38862];
//                        byte [] bas64code = new byte[51817];
//                        int ret = IDCReaderSDK.wltGetbmp(ici.getwltdata(),bmp,bas64code) ;
//                        if (ret != 1) {// 读卡失败
//                            // msg.what = USBMsg.ReadIdCardFail;
//
//                            return;
//                        }
//                        try {
//
//
//                        String  base64= new String(bas64code);
//                        byte[] decodedString = Base64.decode(base64, Base64.DEFAULT);
//                        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
//                        msg.obj = decodedByte;
//
//                        msg.setData(data);
//
//                        msg.what = HandlerMsg.READ_SUCCESS;
//                        mHandler.sendMessage(msg);
//                        }catch (Exception e){
//                            e.printStackTrace();
//                        }
//                    }
//                }
//                SystemClock.sleep(300);
//                msg = Message.obtain();
//                msg.what = HandlerMsg.READ_ERROR;
//                mHandler.sendMessage(msg);
//                SystemClock.sleep(300);
//            }
//
//        }
//    }
//
//
//}
