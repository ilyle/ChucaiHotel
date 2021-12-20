package com.chucai.hotel.animutils;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;


import java.lang.ref.SoftReference;

/**
 * TITLE
 * Created by shixiaoming on 16/12/27.
 */

public class AnimationsContainerResId {
    private static final String TAG = "AnimationsContainerFile";
    public int FPS = 58;  // 每秒播放帧数，fps = 1/t，t-动画两帧时间间隔
    private static Context mContext ;
    private Integer[] resList;
    // 单例
    private static AnimationsContainerResId mInstance;


    public AnimationsContainerResId(Context context){
        mContext=context;
    }
    //获取单例
    public static AnimationsContainerResId getInstance(Context context,Integer[] resId, int fps) {
        if (mInstance == null)
            mInstance = new AnimationsContainerResId(context);
        mInstance.setResId(resId, fps);
        return mInstance;
    }

    public void setResId(Integer[] files, int fps){
        this.resList=files;
        this.FPS = fps;
    }
//    // 从xml中读取资源ID数组
//    private int[] mProgressAnimFrames = getData(resId);

    /**
     * @param imageView
     * @return progress dialog animation
     */
    public FramesSequenceAnimation createProgressDialogAnim(ImageView imageView) {
        return new FramesSequenceAnimation(imageView, resList, FPS);
    }


    /**
     * 循环读取帧---循环播放帧
     */
    public class FramesSequenceAnimation {
        private Integer[] mFrames; // 帧数组
        private int mIndex; // 当前帧
        private boolean mShouldRun; // 开始/停止播放用
        private boolean mIsRunning; // 动画是否正在播放，防止重复播放
        private SoftReference<ImageView> mSoftReferenceImageView; // 软引用ImageView，以便及时释放掉
        private Handler mHandler;
        private int mDelayMillis;
        private boolean oneShot=false;

        public void setOneShot(boolean oneShot) {
            this.oneShot = oneShot;
        }

        private OnAnimationStoppedListener mOnAnimationStoppedListener; //播放停止监听



        private Bitmap mBitmap = null;
        private BitmapFactory.Options mBitmapOptions;//Bitmap管理类，可有效减少Bitmap的OOM问题

        public FramesSequenceAnimation(ImageView imageView, Integer[] files, int fps) {
            mHandler = new Handler();
            mFrames = files;
            mIndex = -1;
            mSoftReferenceImageView = new SoftReference<ImageView>(imageView);
            mShouldRun = false;
            mIsRunning = false;
            mDelayMillis = 1000 / fps;//帧动画时间间隔，毫秒
            if(files.length>0) {
                imageView.setImageBitmap(BitmapFactory.decodeResource(imageView.getResources(),files[0]));

                // 当图片大小类型相同时进行复用，避免频繁GC
                if (Build.VERSION.SDK_INT >= 11) {
                    Bitmap bmp = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
                    int width = bmp.getWidth();
                    int height = bmp.getHeight();
                    Bitmap.Config config = bmp.getConfig();
                    mBitmap = Bitmap.createBitmap(width, height, config);
                    mBitmapOptions = new BitmapFactory.Options();
                    //设置Bitmap内存复用
                    mBitmapOptions.inBitmap = mBitmap;//Bitmap复用内存块，类似对象池，避免不必要的内存分配和回收
                    mBitmapOptions.inMutable = true;//解码时返回可变Bitmap
                    mBitmapOptions.inSampleSize = 1;//缩放比例
                }
            }
        }

        public void resetIndex(){
            mIndex=-1;
        }
        //循环读取下一帧
        private int getNext() {
            mIndex++;
            if(!oneShot) {
                if (mIndex >= mFrames.length)
                    mIndex = 0;
            }else {
                if(mIndex>=mFrames.length-1){
                    mIndex=mFrames.length-1;
                }

            }
            return mIndex;
        }
        Runnable runnable;
        /**
         * 播放动画，同步锁防止多线程读帧时，数据安全问题
         */
        public synchronized void start() {
            mShouldRun = true;
            if (mIsRunning) {
                return;
            }
            mIndex = -1;
            runnable = new Runnable() {
                @Override
                public void run() {
                    Log.i(TAG, "run: exec");
                    ImageView imageView = mSoftReferenceImageView.get();
                    if (!mShouldRun || imageView == null) {
                        mIsRunning = false;
                        if (mOnAnimationStoppedListener != null) {
                            mOnAnimationStoppedListener.AnimationStopped();
                        }
                        return;
                    }

                    mIsRunning = true;
                    mHandler.postDelayed(this, mDelayMillis);
                    int imageRes = getNext();
                    Log.i(TAG, "run: "+imageView.isShown()+"  "+imageRes);
                    if (imageView.isShown()) {
                        if (mBitmap != null) { // so Build.VERSION.SDK_INT >= 11
                            Bitmap bitmap = null;
                            try {
                                if(resList[imageRes]!=0) {
                                    if(oneShot){
                                       if(imageRes!=resList.length-1){
                                           bitmap = BitmapFactory.decodeResource(imageView.getResources(),resList[imageRes], mBitmapOptions);
                                       }else {
                                           mHandler.removeCallbacks(runnable);
                                       }
                                    }else {
                                        bitmap = BitmapFactory.decodeResource(imageView.getResources(),resList[imageRes], mBitmapOptions);
                                    }
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            if (bitmap != null) {
                                imageView.setImageBitmap(bitmap);
                            } else {
//                                imageView.setImageResource(imageRes);
//                                if(mBitmap!=null) {
//                                    mBitmap.recycle();
//                                    mBitmap = null;
//                                }
                            }
                        } else {
                           // imageView.setImageResource(imageRes);
                        }
                    }else {
                    }
                    //新开线程去读下一帧

                }
            };
                mHandler.post(runnable);
        }

        /**
         * 停止播放
         */
        public synchronized void stop() {
            mShouldRun = false;
            mIsRunning=false;
        }

        /**
         * 设置停止播放监听
         * @param listener
         */
        public void setOnAnimStopListener(OnAnimationStoppedListener listener){
            this.mOnAnimationStoppedListener = listener;
        }
    }

    /**
     * 从xml中读取帧数组
     * @param resId
     * @return
     */
    private int[] getData(int resId){
        TypedArray array = mContext.getResources().obtainTypedArray(resId);

        int len = array.length();
        int[] intArray = new int[array.length()];

        for(int i = 0; i < len; i++){
            intArray[i] = array.getResourceId(i, 0);
        }
        array.recycle();
        return intArray;
    }

    /**
     * 停止播放监听
     */
    public interface OnAnimationStoppedListener{
        void AnimationStopped();
    }
}