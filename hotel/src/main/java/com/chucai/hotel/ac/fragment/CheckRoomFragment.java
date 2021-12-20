package com.chucai.hotel.ac.fragment;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.baidu.idl.face.platform.FaceStatusNewEnum;
import com.baidu.idl.face.platform.model.ImageInfo;
import com.baidu.idl.face.platform.ui.FaceDetectFragment;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chucai.hotel.R;
import com.chucai.hotel.ac.BookRoomActivity;
import com.chucai.hotel.ac.OrderSearchActivity;
import com.chucai.hotel.animutils.ByteArrayOutputStream;
import com.chucai.hotel.bean.BookMessage;
import com.chucai.hotel.bean.IdCardUser;
import com.chucai.hotel.core.DeviceHelper;
import com.chucai.hotel.http.RequestUtil;
import com.huaka.usb.sdk.HandlerMsg;
import com.huaka.usb.sdk.HkIDCardInfo;
import com.huaka.usb.sdk.HkOtgApi;
import com.weteco.android.IDCReader.IDCReaderSDK;
import com.xtf.xtflib.util.BitmapUtil;
import com.xtf.xtflib.util.ShapeUtil;
import com.xtf.xtflib.util.ToastUtil;
import com.xtf.xtflib.widget.common.SpaceItemDecoration;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@SuppressLint("ValidFragment")
public class CheckRoomFragment extends Fragment {
    private static final String TAG = "CheckRoomFragment";
    private ImageView mImage;
    private TextView mStateTv;
    private RecyclerView mRec;
    public static boolean readPhoneCard = false;
    protected HashMap<String, String> mBase64ImageMap = new HashMap<String, String>();
    protected boolean mIsCreateSurface = false;
    protected volatile boolean mIsCompletion = false;
    MyAdapter myAdapter;
    private int type;
    private TextView mTipsTv;
    private Button mRuZhuBtn;


    HkOtgApi api;


    public CheckRoomFragment(int type) {
        this.type = type;
    }

    private List<IdCardUser> idCardUsers=new ArrayList<>();
    IdCardUser curIdCardUser;

    boolean m_Auto = false;
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == HandlerMsg.READ_SUCCESS) {
                Bundle data = msg.getData();
                String data1 = data.getString("data");
                Log.i(TAG, "handleMessage: " + data1);
                System.out.print("handleMessagehyj----: " + data1);
                HkIDCardInfo cardInfo = JSON.parseObject(data1, HkIDCardInfo.class);
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
                    new CPUThread().start();
                }

            }else if(msg.what==100){
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
            }else if(msg.what==93){
                m_Auto = true;
                if(idCardUsers==null){
                    idCardUsers=new ArrayList<>();
                }

                idCardUsers.add(curIdCardUser);
                myAdapter.setNewData(idCardUsers);
                new CPUThread().start();
                stopRecFace();



            }else if(msg.what==94){
                ToastUtil.showShort(getContext(),"人证比对失败,请重试");
//                m_Auto = true;
//                new CPUThread().start();
                 mHandler.sendEmptyMessageDelayed(33,4000);
            }else if(msg.what==33){
                recFragment.setTakePic();
            }


        }
    };
    View view =null;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(view==null) {
            view = View.inflate(getContext(), R.layout.fragment_check_room, null);
        }
        ViewGroup parent = (ViewGroup) view.getParent();
        if (parent != null) {
            parent.removeView(view);
        }
        Log.i(TAG, "onCreateView: ");
        api = new HkOtgApi(mHandler, getActivity());
        int ret = api.init();// 因为第一次需要点击授权，所以第一次点击时候的返回是-1所以我利用了广播接受到授权后用handler发送消息

        initView(view);

        return view;
    }

    FrameLayout mRecFragemnt;
    private void initView(View view) {
        mImage = (ImageView) view.findViewById(R.id.image);
        mStateTv = (TextView) view.findViewById(R.id.state_tv);
        mRec = (RecyclerView) view.findViewById(R.id.rec);
        mRuZhuBtn=view.findViewById(R.id.ruzhu_btn);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRec.setLayoutManager(linearLayoutManager);
        mRec.addItemDecoration(new SpaceItemDecoration(getContext().getResources().getDimension(R.dimen.dp_27), 0));
        mTipsTv = (TextView) view.findViewById(R.id.tips_tv);
        m_Auto = true;
        mRuZhuBtn.setText("确认入住");
        mRecFragemnt=view.findViewById(R.id.rec_fragment);
        mRuZhuBtn.setBackground(  ShapeUtil.getSoldRadiusBg(Color.parseColor("#EF7561"),getResources().getDimension(R.dimen.dp_10)));
        mRuZhuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<BookMessage.DataDTO.RoomListDTO> room_list = orderListDTO.getRoom_list();
                BookMessage.DataDTO.RoomListDTO roomListDTO = room_list.get(0);
                if(getActivity() instanceof OrderSearchActivity) {
                    List<BookMessage.DataDTO.RoomListDTO.EnterMsgDTO> personsDTOS=new ArrayList<>();

                    for(IdCardUser idCardUser:idCardUsers){
                        BookMessage.DataDTO.RoomListDTO.EnterMsgDTO userListDTOX=new BookMessage.DataDTO.RoomListDTO.EnterMsgDTO();

                        userListDTOX.setId_card(idCardUser.getHkIDCardInfo().getIDCard());
                        userListDTOX.setName(idCardUser.getHkIDCardInfo().getPeopleName());
                        userListDTOX.setCard_type(1);

                        String sex = idCardUser.getHkIDCardInfo().getSex();
                        if(sex.equals("男")){
                            userListDTOX.setGender(1);
                        }else {
                            userListDTOX.setGender(2);
                        }
                        orderListDTO.setLink_man(idCardUser.getHkIDCardInfo().getPeopleName());
                        userListDTOX.setLink_phone(orderListDTO.getLink_phone() );
//                          userListDTOX.se(bitmapToString(idCardUser.getBitmap()));
                        // userListDTOX.setNation_name(idCardUser.getHkIDCardInfo().getPeopleName());
                        personsDTOS.add(userListDTOX);
                    }
                    // roomListDTO.setUser_list(personsDTOS);
                    roomListDTO.setEnter_msg(personsDTOS);

                    orderListDTO.setRoom_list(room_list);




                    OrderSearchActivity bookRoomAcivity = (OrderSearchActivity) getActivity();
                    Log.i(TAG, "onClick: ------------"+JSON.toJSONString(orderListDTO));

                    bookRoomAcivity.showPayFragment(pageType,orderListDTO);

                }else {
                    BookRoomActivity bookRoomActivity = (BookRoomActivity) getActivity();
                    List<BookMessage.DataDTO.RoomListDTO.EnterMsgDTO> personsDTOS=new ArrayList<>();

                    for(IdCardUser idCardUser:idCardUsers){
                        BookMessage.DataDTO.RoomListDTO.EnterMsgDTO userListDTOX=new BookMessage.DataDTO.RoomListDTO.EnterMsgDTO();

                        userListDTOX.setId_card(idCardUser.getHkIDCardInfo().getIDCard());
                        userListDTOX.setName(idCardUser.getHkIDCardInfo().getPeopleName());
                        userListDTOX.setCard_type(1);

                        String sex = idCardUser.getHkIDCardInfo().getSex();
                        if(sex.equals("男")){
                            userListDTOX.setGender(1);
                        }else {
                            userListDTOX.setGender(2);
                        }
                        orderListDTO.setLink_man(idCardUser.getHkIDCardInfo().getPeopleName());

                        userListDTOX.setLink_phone(orderListDTO.getLink_phone() );
//                      userListDTOX.se(bitmapToString(idCardUser.getBitmap()));
//                      userListDTOX.setNation_name(idCardUser.getHkIDCardInfo().getPeopleName());
                        personsDTOS.add(userListDTOX);
                    }
                    //  roomListDTO.setUser_list(personsDTOS);
                    roomListDTO.setEnter_msg(personsDTOS);

                    orderListDTO.setRoom_list(room_list);
                    Log.i(TAG, "onClick: ------------"+JSON.toJSONString(orderListDTO));

//                            orderListDTO.setPersons(personsDTOS);
//                        orderListDTO.setName(idCardUsers.get(0).getHkIDCardInfo().getPeopleName());
//                        orderListDTO.setIDCard(idCardUsers.get(0).getHkIDCardInfo().getIDCard());
                        bookRoomActivity.showPayFragment(pageType,orderListDTO);
                    }

            }
        });
//
        new CPUThread().start();
////
////
////
////        int rotation = 0;
        myAdapter=new MyAdapter(idCardUsers);
        mRec.setAdapter(myAdapter);
//


        //startRecFace();
//        stopRecFace();
//        startRecFace();
    }

    /**
     * 图片转换成base64字符串
     *
     * @param bitmap
     * @return
     */
    public static String bitmapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] imgBytes = baos.toByteArray();// 转为byte数组
        return Base64.encodeToString(imgBytes, Base64.DEFAULT);
    }

    public static void faceCompare(final Handler mHandler, final Bitmap bitmap, final Bitmap bitmap2){
        try {
            JSONObject   jsonObject1=new JSONObject();JSONArray jsonArray=new JSONArray();
            jsonObject1.put("image", BitmapUtil.bitmapToBase64(bitmap));
            jsonObject1.put("image_type","BASE64");
            jsonObject1.put("face_type","LIVE");
            jsonObject1.put("quality_control","NORMAL");
            jsonObject1.put("liveness_control","NORMAL");
            jsonArray.put(jsonObject1);
            JSONObject jsonObject2=new JSONObject();
            jsonObject2.put("image", BitmapUtil.bitmapToBase64(bitmap2));
            jsonObject2.put("image_type","BASE64");
            jsonObject2.put("face_type","IDCARD");
            jsonObject2.put("quality_control","NORMAL");
            jsonObject2.put("liveness_control","NORMAL");
            jsonArray.put(jsonObject2);
            String post = RequestUtil.requestHttps2("https://aip.baidubce.com/rest/2.0/face/v3/match?access_token=" + DeviceHelper.getsFaceToken(), "POST", jsonArray.toString());
            jsonObject1=new JSONObject(post);
            if(jsonObject1.getInt("error_code")==0){
                JSONObject result = jsonObject1.getJSONObject("result");

                int so = result.getInt("score");
                if (so > 80) {
                    mHandler.sendEmptyMessage(93);
                } else {
                    mHandler.sendEmptyMessage(94);
                }
            }else {

                mHandler.sendEmptyMessage(94);

            }

        }catch (Exception e){
            e.printStackTrace();
        }


    }




    FaceDetectFragment recFragment = null;
    public void startRecFace() {
        mImage.setVisibility(View.VISIBLE);
        mTipsTv.setVisibility(View.GONE);
        mRuZhuBtn.setVisibility(View.GONE);
//        mHandler.sendEmptyMessageDelayed(93,3*1000);
//            recFragment.

//            recFragment.setOnRecListener(new RecognizeFragment.OnRecListener() {
//                @Override
//                public void onRecFace(Bitmap faceFeature) {
////                    Bitmap bitmap1 = BitmapFactory.decodeByteArray(featureData, 0, featureData.length);
////
////                    byte[] headImageData = ArcSoftImageUtil.createImageData(1080, 1920, ArcSoftImageFormat.NV21);
////                    int cropCode = ArcSoftImageUtil.cropImage(featureData, headImageData, 1080, 1920, new Rect(0,0,1080,1920),  ArcSoftImageFormat.NV21);
//
////                    Bitmap image = RecognizeFragment.getImage(getActivity(), featureData, 1280, 720);
//
//
////                    Bitmap bitmap = Bitmap.createBitmap(720, 1280, Bitmap.Config.ARGB_8888);
////                    ArcSoftImageUtil.imageDataToBitmap(faceFeature.getFeatureData(),bitmap,  ArcSoftImageFormat.NV21);
//                    mImage.post(new Runnable() {
//                        @Override
//                        public void run() {
//                            mImage.setImageBitmap(faceFeature);
//                        }
//                    });
//
//                }
//            });
        recFragment = new FaceDetectFragment(getActivity());
        RelativeLayout.LayoutParams lps=new RelativeLayout.LayoutParams(-1,-1);
         mRecFragemnt.addView(recFragment,lps);

            recFragment.setRecListener(new FaceDetectFragment.RecListener() {
                @Override
                public void onDetectCompletion(FaceStatusNewEnum status, String message, HashMap<String, ImageInfo> base64ImageCropMap, HashMap<String, ImageInfo> base64ImageSrcMap) {
                    Log.i(TAG, "onDetectCompletion: "+message);

                    if (status == FaceStatusNewEnum.DetectRemindCodeTimeout) {
//                        recFragment.reRec();
//                        ToastUtil.showShort(getContext(),"识别超时，请重试");
                        mImage.post(new Runnable() {
                            @Override
                            public void run() {
                                stopRecFace();


                            }
                        });


                    }
                }
            });
        recFragment.setOnPhotoTakListener(new FaceDetectFragment.OnPhotoTakListener() {
            @Override
            public void onTakePic(Bitmap path) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        faceCompare(mHandler,path,curIdCardUser.getBitmap());
                    }
                }).start();




            }
        });
        mHandler.sendEmptyMessageDelayed(33,4000);
    }



    public void stopRecFace(){
        mImage.setVisibility(View.VISIBLE)
        ;
        mHandler.removeMessages(93);
        mTipsTv.setVisibility(View.VISIBLE);
        if(idCardUsers.size()>0) {
            mRuZhuBtn.setVisibility(View.VISIBLE);
        }else {
            mRuZhuBtn.setVisibility(View.GONE);
        }
        try {
            mRecFragemnt.removeAllViews();

        }catch (Exception e){
            e.printStackTrace();
        }

    }


    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, "onResume: ");


    }



    @Override
    public void onPause() {
        super.onPause();
        Log.i(TAG, "onPause: ");
        stopReadCard();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy: ");
        mHandler.removeMessages(33);
    }






    public void stopReadCard() {
        readPhoneCard = false;
    }




    @Override
    public void onDestroyView() {
        super.onDestroyView();
        api.unInit();
        if (view != null) {
            ViewGroup parentView = (ViewGroup) view.getParent();
            if (parentView != null) {
                parentView.removeView(view);
            }
        }
        Log.i(TAG, "onDestroyView: ");

    }
    BookMessage.DataDTO orderListDTO;
    int pageType;
    public void setData(int type,  BookMessage.DataDTO orderListDTO) {
        this.orderListDTO=orderListDTO;
        this.pageType=type;
    }


    class CPUThread extends Thread {
        public CPUThread() {
            super();
        }
        int readCount=0;
        @Override
        public void run() {

            super.run();
            HkIDCardInfo ici;
            Message msg;
            while (m_Auto) {
                readCount=0;
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
                            Log.i(TAG, "run: -----------------------------------"+ret);
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
    class MyAdapter extends BaseQuickAdapter<IdCardUser, BaseViewHolder> {


        public MyAdapter(@Nullable List<IdCardUser> data) {
            super(data);
            mLayoutResId = R.layout.adapter_fragment_check_room_item;
        }


        @Override
        protected void convert(@NonNull BaseViewHolder helper, IdCardUser item) {
            helper.setImageBitmap(R.id.image,item.getBitmap());
            helper.setText(R.id.name,item.getHkIDCardInfo().getPeopleName());
        }
    }




}
