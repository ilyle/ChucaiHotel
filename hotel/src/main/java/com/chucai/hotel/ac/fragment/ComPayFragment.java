package com.chucai.hotel.ac.fragment;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.caysn.autoreplyprint.AutoReplyPrint;
import com.chucai.hotel.R;
import com.chucai.hotel.ac.BookRoomActivity;
import com.chucai.hotel.ac.OrderSearchActivity;
import com.chucai.hotel.bean.BookMessage;
import com.chucai.hotel.bean.NewRoom;
import com.chucai.hotel.bean.PayData;
import com.chucai.hotel.control.MqttControl;
import com.chucai.hotel.control.NetControl;
import com.chucai.hotel.control.Printusb;
import com.chucai.hotel.core.DeviceHelper;
import com.sun.jna.Pointer;
import com.xtf.xtflib.util.ShapeUtil;
import com.xtf.xtflib.util.TimeUtil;
import com.xtf.xtflib.util.ZxingUtil;
import com.xtf.xtflib.widget.NiceImageView.NiceImageView;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class ComPayFragment extends Fragment implements MqttControl.OnMqttMessageListener {
    private static final String TAG = "ComPayFragment";
    private View mBgView;
    private NiceImageView mRoomPic;
    private TextView mRoomName;
    private TextView mRoomSpace;
    private TextView mRoomArea;
    private TextView mRoomChuang;
    private TextView mRoomTime;
    private TextView mRoomPrice;
    private TextView mRuShijianTv;
    private TextView mRuzhuShuijianTv;
    private TextView mTimeSpace;
    private TextView mZhuSuTv;
    private TextView mZhuSuUser;
    private TextView mPhoneTv;
    private TextView mUserPhone;
    private TextView mMoneyTv;
    private TextView mUserMoney;
    private TextView mYaJinTv;
    private TextView mUserYaJin;
    private ConstraintLayout mMoneyLayout;
    private ImageView mCodeImage;
    private View mLogoBg;
    private TextView mRoomNum;
    private TextView mRoomType;
    private TextView mPayTips;
    private TextView mTips;
    private TextView mPayYaJinTv;
    BookMessage.DataDTO orderListDTO;
    SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = View.inflate(getContext(),R.layout.fragment_com_pay, null);
        initView(inflate);
        return inflate;


    }

    private void initView(View inflate) {
        mBgView = (View) inflate.findViewById(R.id.bg_view);
        mRoomPic = (NiceImageView) inflate.findViewById(R.id.room_pic);
        mRoomName = (TextView) inflate.findViewById(R.id.room_name);
        mRoomSpace = (TextView) inflate.findViewById(R.id.room_space);
        mRoomArea = (TextView) inflate.findViewById(R.id.room_area);
        mRoomChuang = (TextView) inflate.findViewById(R.id.room_chuang);
        mRoomTime = (TextView) inflate.findViewById(R.id.room_time);
        mRoomPrice = (TextView) inflate.findViewById(R.id.room_price);
        mRuShijianTv = (TextView) inflate.findViewById(R.id.ru_shijian_tv);
        mRuzhuShuijianTv = (TextView) inflate.findViewById(R.id.ruzhu_shuijian_tv);
        mTimeSpace = (TextView) inflate.findViewById(R.id.time_space);
        mZhuSuTv = (TextView) inflate.findViewById(R.id.zhu_su_tv);
        mZhuSuUser = (TextView) inflate.findViewById(R.id.zhu_su_user);
        mPhoneTv = (TextView) inflate.findViewById(R.id.phone_tv);
        mUserPhone = (TextView) inflate.findViewById(R.id.user_phone);
        mMoneyTv = (TextView) inflate.findViewById(R.id.money_tv);
        mUserMoney = (TextView) inflate.findViewById(R.id.user_money);
        mYaJinTv = (TextView) inflate.findViewById(R.id.ya_jin_tv);
        mUserYaJin = (TextView) inflate.findViewById(R.id.user_ya_jin);
        mMoneyLayout = (ConstraintLayout) inflate.findViewById(R.id.money_layout);
        mCodeImage = (ImageView) inflate.findViewById(R.id.code_image);
        mLogoBg = (View) inflate.findViewById(R.id.logo_bg);
        mRoomNum = (TextView) inflate.findViewById(R.id.room_num);
        mRoomType = (TextView) inflate.findViewById(R.id.room_type);
        mPayTips = (TextView) inflate.findViewById(R.id.pay_tips);
        mTips = (TextView) inflate.findViewById(R.id.tips);
        mPayYaJinTv = (TextView) inflate.findViewById(R.id.pay_ya_jin_tv);
        BookMessage.DataDTO.RoomListDTO roomListDTO = orderListDTO.getRoom_list().get(0);

        if(orderListDTO!=null){
            try {


                Date startTime=new Date(orderListDTO.getCreate_time()*1000);
                Date endTime=new Date(orderListDTO.getLeave_time_plan()*1000);
                NewRoom.DataDTO.ListDTO resultDTO = DeviceHelper.queryRoomByRoomNum(roomListDTO.getRoom_number());

                mTimeSpace.setBackground(ShapeUtil.getSoldRadiusBg(Color.parseColor("#EF7561"),(getResources().getDimension(R.dimen.dp_7))));
                mRoomType.setText(resultDTO.getRoom_type_name());
                long dayInterval = TimeUtil.getDayInterval(endTime.getTime(), startTime.getTime());
                float onePtrice= orderListDTO.getRoom_price();


                mRoomPrice.setText("共"+dayInterval+"晚 ￥"+String.format("%.2f",onePtrice*dayInterval)+"元");
                SimpleDateFormat dateFormat=new SimpleDateFormat("MM月dd 日");
                mRuzhuShuijianTv.setText(dateFormat.format(startTime)+" - "+dateFormat.format(endTime));
                mTimeSpace.setText(dayInterval+"晚");
                mZhuSuUser.setText(roomListDTO.getEnter_msg().get(0).getName());
                mUserPhone.setText(orderListDTO.getLink_phone());
                mUserYaJin.setText(roomListDTO.getCash_pledge()+ "元");
                //  double price=0;
                //  List<BookMessage.DataDTO.RoomListDTO.RoomPricesDTOX> room_prices = roomListDTO.getRoom_prices();
                /*
                for(BookMessage.DataDTO.RoomListDTO.RoomPricesDTOX p:room_prices){
                    price+=p.getRoom_price();
                }
                 */
//                if(dayprice!=null){
//
//                    for(AvalableRoomPrice.ResultDTO.DaypriceDTO daypriceDTO:dayprice1){
//                        price+=daypriceDTO.getPrice();
//                    }
//
//                }else {
//                    price = (float) orderListDTO.getRoomPrice()*dayInterval;
//                }
                double yajin=roomListDTO.getCash_pledge();
                mUserMoney.setText(String.format("%.2f",onePtrice*dayInterval+yajin)+"元");
                mRoomNum.setText(roomListDTO.getRoom_number());
                mRoomName.setText(resultDTO.getRoom_type_name());
                //    mLogoBg.setBackground(ShapeUtil.getSoldRadiusBg(Color.BLUE,(getResources().getDimension(R.dimen.dp_8))));
                mRoomType.setText(resultDTO.getRoom_type_name());
                mRoomType.setBackground(ShapeUtil.getSoldRadiusBg(Color.parseColor("#006DB1"),getResources().getDimension(R.dimen.dp_8)));
                //      pay((int)(onePtrice*dayInterval+100)*100,type,DeviceHelper.createOrderNo(),orderListDTO);

                pay((double)(onePtrice*dayInterval+ yajin),type,DeviceHelper.createOrderNo(),orderListDTO);

                mPayTips.setText("房费RMB "+String.format("%.2f",onePtrice*dayInterval)+"元 "+" 客房押金RMB "+yajin+"元");
            }catch (Exception e){
                e.printStackTrace();
            }

        }
    }
    private int type;
    public void setData(int type,  BookMessage.DataDTO orderListDTO) {
        this.orderListDTO=orderListDTO;
        this.type=type;

    }

    @Override
    public void onResume() {
        super.onResume();
        MqttControl.addOnMqttMessageListener(this);

    }

    @Override
    public void onPause() {
        super.onPause();
        MqttControl.removeOnMqttMessageListener(this);
    }

    public void pay(double price, int type, String order, BookMessage.DataDTO orderListDTO){
        new Thread(new Runnable() {
            @Override
            public void run() {
//                try {
//                    PrintControl.newInstance().getmPrinter().printString("/n");
//                    PrintControl.newInstance().getmPrinter().printString("/n");
//                    PrintControl.newInstance().getmPrinter().printString("/n");
//                    PrintControl.newInstance().getmPrinter().printString("            福家智能国际酒店");
//                    PrintControl.newInstance().getmPrinter().printString("/n");
//                    PrintControl.newInstance().getmPrinter().printString("/n");
//                    PrintControl.newInstance().getmPrinter().printString("   房间号码："+orderListDTO.getRoomNo());
//                    PrintControl.newInstance().getmPrinter().printString("/n");
//
//                    PrintControl.newInstance().getmPrinter().printString("   房   型："+DeviceHelper.queryRoomByRoomNum(orderListDTO.getRoomNo()).getRoomTypeName());
//                    PrintControl.newInstance().getmPrinter().printString("/n");
//                    PrintControl.newInstance().getmPrinter().printString("   房   费："+orderListDTO.getRoomPrice()+"元");
//                    PrintControl.newInstance().getmPrinter().printString("/n");
//                    PrintControl.newInstance().getmPrinter().printString("   押   金："+100+"元");
//                    PrintControl.newInstance().getmPrinter().printString("/n");
//                    PrintControl.newInstance().getmPrinter().printString("   入住时间："+orderListDTO.getArr());
//                    PrintControl.newInstance().getmPrinter().printString("/n");
//                    PrintControl.newInstance().getmPrinter().printString("   离店时间："+orderListDTO.getDep());
//                    PrintControl.newInstance().getmPrinter().printString("/n");
//                    PrintControl.newInstance().getmPrinter().printString("   联系方式：123456789012");
//                    PrintControl.newInstance().getmPrinter().printString("/n");
//                    PrintControl.newInstance().getmPrinter().printString("   地   址："+"深圳市南山区智恒产业园E01b 308");
//
//                    PrintControl.newInstance().getmPrinter().printString("/n");
//                    PrintControl.newInstance().getmPrinter().printString("/n");
//                    PrintControl.newInstance().getmPrinter().printString("/n"); PrintControl.newInstance().getmPrinter().printString("\n");
//                    PrintControl.newInstance().getmPrinter().fullCut();
//                    CardControl.newInstance().chuKa();
//
//                }catch (Exception e){
//                    e.printStackTrace();
//                }


                String pay = new NetControl().pay(price,type, order,orderListDTO);
                try {
                    Log.i(TAG, "run: "+pay);
                    PayData data= JSON.parseObject(pay, PayData.class);
                    if(data.getRetcode()==0){
                        Log.i(TAG, "run: "+data.getData());
                        PayData.PayMessage payMessage=JSON.parseObject(data.getData(), PayData.PayMessage.class);
                        Bitmap bitmap = ZxingUtil.CreateLogoQRImage(payMessage.getReturn_url(), 400, 400, null);
                        mCodeImage.post(new Runnable() {
                            @Override
                            public void run() {
                                mCodeImage.setImageBitmap(bitmap);
                            }
                        });


                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
                Pointer pmPointer= Printusb.getPointer() ;
                Label_SampleTicket_WithCut(pmPointer,orderListDTO);


            }
        }).start();
    }

    @Override
    public void dataRec(int type, Object data) {
        mPayTips.post(new Runnable() {
            @Override
            public void run() {
                if(type==0 || type==1){
                    FragmentActivity activity = getActivity();
                    if(activity instanceof BookRoomActivity){
                        ((BookRoomActivity) activity).showPaySuccess();
                    }else if(activity instanceof OrderSearchActivity ){
                        ((OrderSearchActivity) activity).showPaySuccess();
                    }
                }



            }
        });
    }
    void Label_SampleTicket_WithCut(Pointer h,BookMessage.DataDTO orderListDTO) {
        Date startTime=new Date(orderListDTO.getCreate_time()*1000);
        Date endTime=new Date(orderListDTO.getLeave_time_plan()*1000);
        SimpleDateFormat dateFormat=new SimpleDateFormat("MM月dd 日");
        long dayInterval = TimeUtil.getDayInterval(endTime.getTime(), startTime.getTime());
        float onePtrice= orderListDTO.getRoom_price();
        double yajin=orderListDTO.getRoom_list().get(0).getCash_pledge();


        AutoReplyPrint.INSTANCE.CP_Pos_SetMultiByteMode(h);
        AutoReplyPrint.INSTANCE.CP_Pos_SetMultiByteEncoding(h, AutoReplyPrint.CP_MultiByteEncoding_UTF8);
        AutoReplyPrint.INSTANCE.CP_Label_PageBegin(h, 0, 0, 576, 240, 0);
        AutoReplyPrint.INSTANCE.CP_Label_DrawBox(h, 0, 0, 576, 240, 1, 1);
        AutoReplyPrint.INSTANCE.CP_Label_DrawText(h, 60, 20, 24, 0, "福家智能国际酒店");
        AutoReplyPrint.INSTANCE.CP_Label_DrawText(h, 60, 60, 24, 0, "   房间号码："+orderListDTO.getRoom_list().get(0).getRoom_number());
        AutoReplyPrint.INSTANCE.CP_Label_DrawText(h, 60, 100, 24, 0, "   房   型："+orderListDTO.getRoom_list().get(0).getRoom_type_name());
        AutoReplyPrint.INSTANCE.CP_Label_DrawText(h, 60, 140, 24, 0, "   房   费："+String.format("%.2f",onePtrice*dayInterval));
        AutoReplyPrint.INSTANCE.CP_Label_DrawText(h, 60, 180, 24, 0, "   押   金："+String.format("%.2f",yajin));
        AutoReplyPrint.INSTANCE.CP_Label_DrawText(h, 60, 220, 24, 0, "   入住时间："+dateFormat.format(startTime));
        AutoReplyPrint.INSTANCE.CP_Label_DrawText(h, 60, 260, 24, 0, "   离店时间："+dateFormat.format(endTime));
        AutoReplyPrint.INSTANCE.CP_Label_DrawText(h, 60, 300, 24, 0, "   联系方式："+orderListDTO.getLink_phone());
        AutoReplyPrint.INSTANCE.CP_Label_PagePrint(h, 1);
        AutoReplyPrint.INSTANCE.CP_Pos_FullCutPaper(h);
            boolean result = AutoReplyPrint.INSTANCE.CP_Pos_QueryPrintResult(h, 30000);
            if (!result)
                Log.i(TAG, "Print failed");
            else
                Log.i(TAG, "Print Success");
    }


}
