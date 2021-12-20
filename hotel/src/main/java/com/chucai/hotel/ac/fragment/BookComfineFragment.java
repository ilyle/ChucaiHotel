package com.chucai.hotel.ac.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.alibaba.fastjson.JSON;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chucai.hotel.R;
import com.chucai.hotel.ac.OrderSearchActivity;
import com.chucai.hotel.ac.dialog.SelectRoomDialog;
import com.chucai.hotel.bean.BookMessage;
import com.chucai.hotel.core.DeviceHelper;
import com.xtf.xtflib.util.ScreenUtils;
import com.xtf.xtflib.util.ShapeUtil;
import com.xtf.xtflib.util.TimeUtil;
import com.xtf.xtflib.widget.common.SpaceItemDecoration;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BookComfineFragment extends Fragment {
    private RecyclerView mRec;



//    private NiceImageView roomImage;
//    private TextView roomName;
//    private TextView roomSize;
//    private TextView roomChuang;
//    private TextView roomPrice;
//    private TextView ruzhuTime;
//    private TextView distanceDay;
//    private TextView ruzhuName;
//    private TextView ruzhuPhone;
//    private TextView ruzhuMoney;
//    private Button ruzhuBtn;


//    @Override
//    protected void onFinishInflate() {
//        super.onFinishInflate();
//
//    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(getContext(),R.layout.fragment_book_comfine, null);
        initView(view);
        return view;
    }

    private void initView(View view) {
//        roomImage = (NiceImageView) view.findViewById(R.id.room_image);
//        roomName = (TextView) view.findViewById(R.id.room_name);
//        roomSize = (TextView) view.findViewById(R.id.room_size);
//        roomChuang = (TextView) view.findViewById(R.id.room_chuang);
//        roomPrice = (TextView) view.findViewById(R.id.room_price);
//        ruzhuTime = (TextView) view.findViewById(R.id.ruzhu_time);
//        distanceDay = (TextView) view.findViewById(R.id.distance_day);
//        ruzhuName = (TextView) view.findViewById(R.id.ruzhu_name);
//        ruzhuPhone = (TextView) view.findViewById(R.id.ruzhu_phone);
//        ruzhuMoney = (TextView) view.findViewById(R.id.ruzhu_money);
//        ruzhuBtn = (Button) view.findViewById(R.id.ruzhu_btn);
//        roomChuang.
        mRec = (RecyclerView) view.findViewById(R.id.rec);
        mRec.setLayoutManager(new LinearLayoutManager(getContext()));
        mRec.setAdapter(new MyAdapter(bookMessage.getRoom_list()));
        mRec.addItemDecoration(new SpaceItemDecoration(getContext().getResources().getDimension(R.dimen.dp_0),getContext().getResources().getDimension(R.dimen.dp_8)));

    }

    private BookMessage.DataDTO bookMessage;


    public void setOrderListDTOS(BookMessage.DataDTO orderListDTOS) {
        this.bookMessage = orderListDTOS;

    }

    private class MyAdapter extends BaseQuickAdapter<BookMessage.DataDTO.RoomListDTO, BaseViewHolder> {
        SimpleDateFormat simpleDateFormate = new SimpleDateFormat("MM月dd日");


        public MyAdapter(@Nullable List<BookMessage.DataDTO.RoomListDTO> data) {
            super(data);
            mLayoutResId = R.layout.adapter_book_comfine_item;
        }

        @Override
        protected void convert(@NonNull BaseViewHolder helper, BookMessage.DataDTO.RoomListDTO item) {
            helper.setText(R.id.order_no_tv, "订单编号：" + item.getId());
            helper.setBackgroundDrawable(R.id.content, ShapeUtil.getSoldWithStrockRadiusBg(Color.WHITE, 1, Color.parseColor("#D3D7D9"), ScreenUtils.dip2px(getContext(), 6)));
            helper.setText(R.id.name, "住客信息：" + bookMessage.getLink_man());
            helper.setText(R.id.phone, "手机号码：" + bookMessage.getLink_phone());

            if(!TextUtils.isEmpty(item.getRoom_number())) {
                helper.setText(R.id.gui_ge, "房间规格：" + DeviceHelper.queryRoomByRoomNum(item.getRoom_number()).getRoom_type_name());
            }else {
                helper.setText(R.id.gui_ge, "房间规格：");
            }
            ImageView image = helper.itemView.findViewById(R.id.iamge);
            helper.setBackgroundDrawable(R.id.ru_zhu_btn, ShapeUtil.getSoldRadiusBg(Color.parseColor("#EF7561"), ScreenUtils.dip2px(getContext(), 3)));
            helper.setOnClickListener(R.id.ru_zhu_btn, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SelectRoomDialog selectRoomDialog=new SelectRoomDialog(getContext(),R.style.dim_dialog);
                    BookMessage.DataDTO dto= JSON.parseObject(JSON.toJSONString(bookMessage),BookMessage.DataDTO.class);


                    List<BookMessage.DataDTO.RoomListDTO> roomListDTOS=new ArrayList<>();
                    roomListDTOS.add(item);
                    dto.setRoom_list(roomListDTOS);
                    if(TextUtils.isEmpty(item.getRoom_number())){


                        selectRoomDialog.setOrderListDTO(dto);
                        selectRoomDialog.show();

                    }else {

                        OrderSearchActivity activity = (OrderSearchActivity) getActivity();
                        activity.showCheckRoomFagement(dto);
                    }

                }
            });
            try {
                String format1 = simpleDateFormate.format(new Date(item.getStart_time()*1000));
                String format2 = simpleDateFormate.format(new Date(item.getStart_time()*1000+item.getStay_time()*24*3600*1000));


                helper.setText(R.id.in_time, "入住时间：" + format1);
                helper.setText(R.id.out_time, "离店时间：" + format2);
                int s =item.getStay_time();

                helper.setText(R.id.day, "" + s + "晚");
                helper.setBackgroundDrawable(R.id.day, ShapeUtil.getSoldRadiusBg(Color.parseColor("#53A7DB"), (int) getResources().getDimension(R.dimen.dp_14)));

            } catch (Exception e) {
                e.printStackTrace();
                helper.setText(R.id.in_time, "入住时间：") ;
                helper.setText(R.id.out_time, "离店时间：" );
                helper.setText(R.id.day, "" + 0 + "晚");
            }


        }
    }

}
