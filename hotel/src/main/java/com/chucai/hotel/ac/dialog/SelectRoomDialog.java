package com.chucai.hotel.ac.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chucai.hotel.R;
import com.chucai.hotel.ac.OrderSearchActivity;
import com.chucai.hotel.ac.fragment.SelectRoomFragment;
import com.chucai.hotel.bean.BookMessage;
import com.chucai.hotel.bean.NewRoom;
import com.chucai.hotel.bean.PriceData;
import com.chucai.hotel.bean.RoomPriceBean;
import com.chucai.hotel.bean.RoomStateBean;
import com.xtf.xtflib.util.ShapeUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class SelectRoomDialog extends Dialog {
    private TextView mRoomNum;
    private BookMessage.DataDTO orderListDTO;
    private SelectRoomFragment mContent;
    private TextView mRoomState;
    private TextView mSelectMessage;
    private RelativeLayout mFinishBtn;
    private RoomStateBean.DataDTO.ListRoomDTO.RoomListDTO selectRoomResult;
    OrderSearchActivity orderSearchActivity;


    View view;
    private TextView mPriceTv;

    public SelectRoomDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        view = View.inflate(getContext(), R.layout.dialog_select_room, null);
        orderSearchActivity = (OrderSearchActivity) context;
        setContentView(view);

        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        attributes.width = (int) getContext().getResources().getDimension(R.dimen.dp_587);
        attributes.height = (int) getContext().getResources().getDimension(R.dimen.dp_828);
        attributes.gravity = Gravity.CENTER;
        getWindow().setAttributes(attributes);
        view.setBackground(ShapeUtil.getSoldRadiusBg(Color.WHITE, getContext().getResources().getDimension(R.dimen.dp_10)));

    }


    public void setOrderListDTO(BookMessage.DataDTO orderListDTO) {
        this.orderListDTO = orderListDTO;

        initView(view);
        mContent.setPageType(0);
        BookMessage.DataDTO.RoomListDTO roomListDTO = orderListDTO.getRoom_list().get(0);
        long start_time = orderListDTO.getCreate_time() ;
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("MM月dd日");
        long start=start_time*1000;
        long end=start_time*1000+orderListDTO.getStay_time()*24*3600*1000;


        BookMessage.DataDTO.RoomInfoDTO prditem=orderListDTO.getRoom_info();
        List<BookMessage.DataDTO.RoomInfoDTO.RoomServiceListDTO> rmserverlist=prditem.getRoom_service_list();

        mContent.setRoom_property_id(rmserverlist.get(0).getRoom_property_id());
        mContent.setPrice_project(prditem.getPrice_project());
        mContent.setRoom_type_id(rmserverlist.get(0).getRoom_type_id());
     //   mContent.setRoom_prices(prditem.getRoom_prices());
        mContent.setData(start, end);
    }

    private void initView(View view) {
        mRoomNum = (TextView) view.findViewById(R.id.room_num);
        mRoomState = (TextView) view.findViewById(R.id.room_state);
        mSelectMessage = (TextView) view.findViewById(R.id.select_message);
        mFinishBtn = (RelativeLayout) view.findViewById(R.id.finish_btn);
        mContent = (SelectRoomFragment) findViewById(R.id.content);
        mContent.setOnSelectListener(new SelectRoomFragment.OnSelectListener() {
            @Override
            public void onSelect(RoomStateBean.DataDTO.ListRoomDTO.RoomListDTO  resultDTO) {
                selectRoomResult = resultDTO;
                if (resultDTO != null) {
                    mRoomNum.setText(resultDTO.getRoom_number());
                    mSelectMessage.setText("已选择：" + resultDTO.getRoom_type_name());

                    int totalPrice=orderListDTO.getBill_money()-orderListDTO.getAlready_pay();
                    orderListDTO.setRoom_price(totalPrice);


//                    for(AvalableRoomPrice.ResultDTO.DaypriceDTO daypriceDTO:prices){
//                        totalPrice+=daypriceDTO.getPrice();
//                    }
//                    if(prices.size()>1){
//                        totalPrice=prices.get(0).getPrice()*(prices.size()-1);
//                    }   else if(prices.size()==1){
//                        totalPrice=prices.get(0).getPrice();
//                    }


                    mPriceTv.setText("￥ "+String.format("%.2f",totalPrice) );

                } else {
                    mRoomNum.setText("");
                    mSelectMessage.setText("");
                }
            }
        });


        mRoomState = (TextView) findViewById(R.id.room_state);
        mSelectMessage = (TextView) findViewById(R.id.select_message);
        mFinishBtn = (RelativeLayout) findViewById(R.id.finish_btn);
        setCancelable(false);
        mFinishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectRoomResult == null) {
                    dismiss();
                } else {
//                    orderListDTO.setDayprice(selectRoomResult.getPrices());
//                    orderListDTO.setRoomNo(selectRoomResult.getRoomNo());
                    OrderSearchActivity activity = (OrderSearchActivity) orderSearchActivity;
                    activity.showCheckRoomFagement(orderListDTO);
                    dismiss();

                }

            }
        });

        mPriceTv = (TextView) findViewById(R.id.price_tv);
    }
}
