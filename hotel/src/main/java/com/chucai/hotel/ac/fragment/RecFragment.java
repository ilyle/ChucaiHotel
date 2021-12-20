//package com.chucai.hotel.ac.fragment;
//
//import com.baidu.idl.face.platform.FaceStatusNewEnum;
//import com.baidu.idl.face.platform.model.ImageInfo;
//import com.baidu.idl.face.platform.ui.FaceDetectFragment;
//
//import java.util.HashMap;
//
//public class RecFragment extends FaceDetectFragment {
//    RecListener recListener;
//
//    public void setRecListener(RecListener recListener) {
//        this.recListener = recListener;
//    }
//
//    @Override
//    public void onDetectCompletion(FaceStatusNewEnum status, String message, HashMap<String, ImageInfo> base64ImageCropMap, HashMap<String, ImageInfo> base64ImageSrcMap) {
//        super.onDetectCompletion(status, message, base64ImageCropMap, base64ImageSrcMap);
//        if(recListener!=null){
//            recListener.onDetectCompletion(status,message,base64ImageCropMap,base64ImageSrcMap);
//        }
//    }
//
//
//    public interface RecListener{
//
//        public void onDetectCompletion(FaceStatusNewEnum status, String message, HashMap<String, ImageInfo> base64ImageCropMap, HashMap<String, ImageInfo> base64ImageSrcMap) ;
//
//
//        }
//}
