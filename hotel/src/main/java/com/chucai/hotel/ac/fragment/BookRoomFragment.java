package com.chucai.hotel.ac.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chucai.hotel.R;
import com.chucai.hotel.ac.BookRoomActivity;
import com.chucai.hotel.ac.dialog.SelectDateDialog;
import com.chucai.hotel.bean.BookMessage;
import com.chucai.hotel.bean.NewRoom;
import com.chucai.hotel.bean.RoomPriceBean;
import com.chucai.hotel.bean.RoomSaleType;
import com.chucai.hotel.bean.RoomStateBean;
import com.chucai.hotel.core.DeviceHelper;
import com.chucai.hotel.http.RequestUtil;
import com.xtf.xtflib.util.ScreenUtils;
import com.xtf.xtflib.util.ShapeUtil;
import com.xtf.xtflib.util.TimeUtil;
import com.xtf.xtflib.util.ToastUtil;
import com.xtf.xtflib.widget.common.SpaceItemDecoration;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BookRoomFragment extends Fragment {
    private static final String TAG = "BookRoomFragment";
    private RecyclerView mTopRec;
    private LinearLayout mTimeLayout;
    private TextView mDayStartTime;
    private TextView mDayStTv;
    private TextView mZhusuDayTv;
    private TextView mDayEndTime;
    private TextView mDayEnTv;
    private TextView mZhuSuTv;
    private RecyclerView mRoomRec;
    private TextView mRoomName;
    private TextView mRuzhuMessage;
    private Button mSubOrderBtn;
    SimpleDateFormat timeFormate = new SimpleDateFormat("MM月dd日");
    SimpleDateFormat webDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat webTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private int pageType;
    SelectDateDialog selectDateDialog;
    private String drtyAvl = "空闲";
    private Date startInDate;
    private Date endOutDate;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM月dd日");
    private View mBgKuang;
    private TextView mRuzhuPrice;
    private Handler mHandler = new Handler();
    TopRecAdapter topRecAdapter;
    RoomStateBean.DataDTO.ListRoomDTO.RoomListDTO curSelectRoom;
    private List<RoomPriceBean.DataDTO> curPriceList;
    private RecyclerView mSaleRec;
    private RoomSaleType.DataDTO curRoomSaleType;
    private SaleRecAdapter saleRecAdapter;

    public BookRoomFragment(int pageType) {
        this.pageType = pageType;
    }

    RoomAdapter roomAdapter;
    SelectRoomFragment.OnSelectListener onSelectListener;

    String phone;


    public void setOnSelectListener(SelectRoomFragment.OnSelectListener onSelectListener) {
        this.onSelectListener = onSelectListener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragemnt_book_room, container, false);
        initView(inflate);
        initData();

        setOnSelectListener(new SelectRoomFragment.OnSelectListener() {
            @Override
            public void onSelect(RoomStateBean.DataDTO.ListRoomDTO.RoomListDTO resultDTO) {
                curSelectRoom = resultDTO;
                if (resultDTO != null) {
                    mRoomName.setText("" + resultDTO.getRoom_type_name() + "  " + resultDTO.getRoom_number());


//                    List<AvalableRoomPrice.ResultDTO.DaypriceDTO> prices = resultDTO.getPrices();
//                    float totalPrice = 0;
//                    if (prices.size() > 1) {
//                        totalPrice = prices.get(0).getPrice() * (prices.size() - 1);
//                    } else if (prices.size() == 1) {
//                        totalPrice = prices.get(0).getPrice();
//                    }

                    double price = 0;
                    for (RoomPriceBean.DataDTO dataDTO : curPriceList) {
                        price += dataDTO.getPrice();
                    }

                    double totalPrice = price;

                    mRuzhuPrice.setText("￥ " + String.format("%.2f", totalPrice));
                } else {
//                    mRoomName.setText("");
//                    mRuzhuPrice.setText("");
                }
            }
        });
        return inflate;
    }

    private void initData() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mTopRec.setLayoutManager(linearLayoutManager);
        mTopRec.addItemDecoration(new SpaceItemDecoration(getResources().getDimension(R.dimen.dp_5), 0));

//        mRoomRec.setLayoutManager(new GridLayoutManager(getContext(), 2));
        mTimeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectDateDialog.setDialogDate(startInDate, endOutDate);
                selectDateDialog.show();
            }
        });
        mZhuSuTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        startInDate = new Date();
        endOutDate = new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000);
        mDayStartTime.setText(timeFormate.format(startInDate));
        mDayEndTime.setText(timeFormate.format(endOutDate));

        mZhusuDayTv.setText("1晚");
        mZhusuDayTv.setBackground(ShapeUtil.getSoldRadiusBg(Color.parseColor("#EF7561"), ScreenUtils.dip2px(getActivity(), 5)));

        List<NewRoom.DataDTO.ListDTO> resultDTOS = new ArrayList<>();
        Map<Integer, NewRoom.DataDTO.ListDTO> allRoomTypeList = DeviceHelper.getAllRoomTypeList();
        for (Integer s : allRoomTypeList.keySet()) {
            resultDTOS.add(allRoomTypeList.get(s));
        }
        topRecAdapter = new TopRecAdapter(resultDTOS);
        mRuzhuMessage.setText(simpleDateFormat.format(startInDate) + "至" + simpleDateFormat.format(endOutDate) + ",共" + (int) TimeUtil.getDayInterval(endOutDate.getTime(), startInDate.getTime()) + "晚");


//        topRecAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
//            @Override
//            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
//
//            }
//        });

        mTopRec.setAdapter(topRecAdapter);
        selectDateDialog = new SelectDateDialog(getActivity());
        curRoomSaleType=DeviceHelper.getsRoomSaleTypeDataList().get(0);

        getCanUseRoom(curRoomSaleType.getId(),resultDTOS.get(0).getRoom_type() + "", startInDate.getTime(), endOutDate.getTime(), drtyAvl);
     //   getCanUseRoom(curRoomSaleType.getId(),item.getRoom_type() + "", startInDate.getTime(), endOutDate.getTime(), drtyAvl + "");

        selectDateDialog.setOnFinishClick(new SelectDateDialog.OnFinishClick() {
            @Override
            public void onDateSelec(Date d, Date e) {
                if (d != null && e != null) {
                    mDayStartTime.setText(simpleDateFormat.format(d));
                    mDayEndTime.setText(simpleDateFormat.format(e));
                    startInDate = d;
                    endOutDate = e;
                    mZhusuDayTv.setText((int) TimeUtil.getDayInterval(e.getTime(), d.getTime()) + "晚");
                    mRuzhuMessage.setText(simpleDateFormat.format(d) + "至" + simpleDateFormat.format(e) + ",共" + (int) TimeUtil.getDayInterval(e.getTime(), d.getTime()) + "晚");
                    getCanUseRoom(curRoomSaleType.getId(),topRecAdapter.getSelectRoomResult().getRoom_type() + "", startInDate.getTime(), endOutDate.getTime(), drtyAvl + "");

                }


            }
        });
        curRoomSaleType=DeviceHelper.getsRoomSaleTypeDataList().get(0);
        saleRecAdapter=new SaleRecAdapter(DeviceHelper.getsRoomSaleTypeDataList());
        mSaleRec.setAdapter(saleRecAdapter);

    }


    private void initView(View inflate) {
        mTopRec = (RecyclerView) inflate.findViewById(R.id.top_rec);
        mTimeLayout = (LinearLayout) inflate.findViewById(R.id.time_layout);
        mDayStartTime = (TextView) inflate.findViewById(R.id.day_start_time);
        mDayStTv = (TextView) inflate.findViewById(R.id.day_st_tv);
        mZhusuDayTv = (TextView) inflate.findViewById(R.id.zhusuDay_tv);
        mDayEndTime = (TextView) inflate.findViewById(R.id.day_end_time);
        mDayEnTv = (TextView) inflate.findViewById(R.id.day_en_tv);
        mZhuSuTv = (TextView) inflate.findViewById(R.id.zhu_su_tv);
        mRoomRec = (RecyclerView) inflate.findViewById(R.id.room_rec);
        mRoomName = (TextView) inflate.findViewById(R.id.room_name);
        mRuzhuMessage = (TextView) inflate.findViewById(R.id.ruzhu_message);
        mSubOrderBtn = (Button) inflate.findViewById(R.id.sub_order_btn);


        mBgKuang = (View) inflate.findViewById(R.id.bg_kuang);

        mBgKuang.setBackground(ShapeUtil.getSoldWithStrockRadiusBg(Color.WHITE, ScreenUtils.dip2px(getActivity(), 1), Color.parseColor("#666666"), ScreenUtils.dip2px(getActivity(), 5)));
        mRuzhuPrice = (TextView) inflate.findViewById(R.id.ruzhu_price);
        roomAdapter = new RoomAdapter(new ArrayList<>());
        mRoomRec.setLayoutManager(new LinearLayoutManager(getContext()));
        mRoomRec.setAdapter(roomAdapter);

        mSubOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (curSelectRoom != null) {

                    BookMessage.DataDTO book = new BookMessage.DataDTO();
                    book.setLink_phone(phone);
                    book.setPrice_project(1);
                    book.setEnter_time((int) (startInDate.getTime() / 1000));
                    book.setCreate_time( (startInDate.getTime() / 1000));

                    book.setStay_time((int) TimeUtil.getDayInterval(startInDate.getTime(), endOutDate.getTime()));
                    book.setBill_source(1);
                    book.setLeave_time_plan((int) (endOutDate.getTime() / 1000));
                    BookMessage.DataDTO.RoomListDTO orderListDTO = new BookMessage.DataDTO.RoomListDTO();
                    orderListDTO.setRoom_number(curSelectRoom.getRoom_number());

                    //  book.setRoom_service_selected(1);


                    orderListDTO.setRoom_id(curSelectRoom.getId());
                    orderListDTO.setId(curSelectRoom.getId());

                    int price = 0;
                    List<BookMessage.DataDTO.RoomInfoDTO.RoomPricesDTO> pricesls=new ArrayList<>();
                    BookMessage.DataDTO.RoomInfoDTO.RoomPricesDTO tmpprice;

                    for (RoomPriceBean.DataDTO dataDTO : curPriceList) {
                        price += dataDTO.getPrice();
                        tmpprice=new BookMessage.DataDTO.RoomInfoDTO.RoomPricesDTO();
                        tmpprice.setRoom_price(dataDTO.getPrice());
                        tmpprice.setDate(dataDTO.getDate());
                        pricesls.add(tmpprice);

                    }

                    orderListDTO.setRoom_price(price);
                    book.setRoom_price(price);
                    orderListDTO.setRoom_prices(pricesls);
                    orderListDTO.setPrice(JSON.parseArray(JSON.toJSONString(curPriceList, SerializerFeature.WriteMapNullValue), BookMessage.DataDTO.RoomListDTO.PriceOriginalDTO.class));
                    orderListDTO.setPrice_original(JSON.parseArray(JSON.toJSONString(curPriceList, SerializerFeature.WriteMapNullValue), BookMessage.DataDTO.RoomListDTO.PriceOriginalDTO.class));
                    orderListDTO.setRoom_service_list(JSON.parseArray(JSON.toJSONString(curSelectRoom.getRoom_service(), SerializerFeature.WriteMapNullValue), BookMessage.DataDTO.RoomListDTO.RoomServiceListDTO.class));
                    orderListDTO.setEnd_time( (endOutDate.getTime() / 1000));
                    orderListDTO.setStart_time( (startInDate.getTime() / 1000));
                    orderListDTO.setCreate_time( (startInDate.getTime() / 1000));
                    orderListDTO.setRoom_type_id(curSelectRoom.getRoom_type_id());
                    orderListDTO.setRoom_type_name(curSelectRoom.getRoom_type_name());
                    orderListDTO.setCash_pledge_original(curSelectRoom.getCash_pledge());
                    orderListDTO.setCash_pledge(curSelectRoom.getCash_pledge());
                    orderListDTO.setStatus(curSelectRoom.getStatus());
                    orderListDTO.setRoom_service_selected(""+curSelectRoom.getRoom_service().get(0).getId());



                    List<BookMessage.DataDTO.RoomListDTO> roomListDTOS = new ArrayList<>();
                    roomListDTOS.add(orderListDTO);
                    book.setRoom_list(roomListDTOS);


                    BookRoomActivity bookRoomActivity = (BookRoomActivity) getActivity();
                    orderListDTO.setStay_time((int) TimeUtil.getDayInterval(startInDate.getTime(), endOutDate.getTime()));
                    bookRoomActivity.showCheckRoomFagement(book);


                } else {
                    ToastUtil.showShort(getContext(), "请选择房间");
                }


            }
        });
        mSaleRec =inflate. findViewById(R.id.sale_rec);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mSaleRec.setLayoutManager(linearLayoutManager);

    }

    public void setData(String phone) {
        this.phone = phone;
    }

    private class TopRecAdapter extends BaseQuickAdapter<NewRoom.DataDTO.ListDTO, BaseViewHolder> {
        private int selectPos = 0;

        public void setSelectPos(int selectPos) {
            this.selectPos = selectPos;
            notifyDataSetChanged();

        }


        public NewRoom.DataDTO.ListDTO getSelectRoomResult() {
            return mData.get(selectPos);
        }

        public TopRecAdapter(@Nullable List<NewRoom.DataDTO.ListDTO> data) {
            super(data);
            mLayoutResId = R.layout.adapter_frgment_top_rec_item;
        }

        @Override
        protected void convert(@NonNull BaseViewHolder helper, NewRoom.DataDTO.ListDTO item) {
            int index = mData.indexOf(item);
            if (selectPos == index) {
                helper.setBackgroundRes(R.id.tv, R.drawable.xuanzhong_pre);
                helper.setTextColor(R.id.tv, Color.parseColor("#FFFFFF"));
            } else {
                helper.setBackgroundRes(R.id.tv, R.drawable.xuanzhong_nor);
                helper.setTextColor(R.id.tv, Color.parseColor("#556A77"));
            }
            helper.setText(R.id.tv, item.getRoom_type_name());
            helper.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    topRecAdapter.setSelectPos(index);
//                    getRoomDetail(item.getGId());
                    NewRoom.DataDTO.ListDTO item = topRecAdapter.getData().get(index);
                    getCanUseRoom(curRoomSaleType.getId(),item.getRoom_type() + "", startInDate.getTime(), endOutDate.getTime(), drtyAvl + "");

                }
            });

        }
    }

    //    private class RoomAdapter extends BaseQuickAdapter<RoomMessage.DataDTOX.DataDTO, BaseViewHolder> {
//        public RoomAdapter(int layoutResId, @Nullable List<RoomMessage.DataDTOX.DataDTO> data) {
//            super(layoutResId, data);
//            mLayoutResId = R.layout.adpter_fragment_book_room_room_item;
//
//        }
//
//        @Override
//        protected void convert(@NonNull BaseViewHolder helper, RoomMessage.DataDTOX.DataDTO item) {
//            Glide.with(getActivity()).load(item.getGCover()).into((ImageView) helper.itemView.findViewById(R.id.iamge));
//        }
//    }
    private class SaleRecAdapter extends BaseQuickAdapter<RoomSaleType.DataDTO, BaseViewHolder> {
        private int selectPos = 0;

        public void setSelectPos(int selectPos) {
            this.selectPos = selectPos;
            notifyDataSetChanged();

        }


        public RoomSaleType.DataDTO getSelectRoomResult() {
            return mData.get(selectPos);
        }

        public SaleRecAdapter(@Nullable List<RoomSaleType.DataDTO> data) {
            super(data);
            mLayoutResId = R.layout.adapter_frgment_top_rec_item;
        }

        @Override
        protected void convert(@NonNull BaseViewHolder helper, RoomSaleType.DataDTO item) {
            int index = mData.indexOf(item);
            if (selectPos == index) {
                helper.setBackgroundRes(R.id.tv, R.drawable.xuanzhong_pre);
                helper.setTextColor(R.id.tv, Color.parseColor("#FFFFFF"));
            } else {
                helper.setBackgroundRes(R.id.tv, R.drawable.xuanzhong_nor);
                helper.setTextColor(R.id.tv, Color.parseColor("#556A77"));
            }
            helper.setText(R.id.tv, item.getName());
            helper.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    topRecAdapter.setSelectPos(index);
//                    getRoomDetail(item.getGId());
                    RoomSaleType.DataDTO listDTO = mData.get(index);
                    getCanUseRoom(curRoomSaleType.getId(),curSelectRoom.getRoom_type() + "", startInDate.getTime(), endOutDate.getTime(), drtyAvl + "");

                }
            });

        }
    }


    private class RoomAdapter extends BaseQuickAdapter<RoomStateBean.DataDTO.ListRoomDTO, BaseViewHolder> {
        private int selcetPos = -1;
        private int childSelect = -1;
        SpaceItemDecoration spaceItemDecoration;

        @Override
        public void setNewData(@Nullable List<RoomStateBean.DataDTO.ListRoomDTO> data) {
//            selcetPos=-1;
            childSelect = -1;
            super.setNewData(data);
        }

        public void setSelcetPos(int selcetPos) {
            this.selcetPos = selcetPos;

        }

        public RoomAdapter(@Nullable List<RoomStateBean.DataDTO.ListRoomDTO> data) {
            super(data);
            mLayoutResId = R.layout.adapter_select_room_floor_item;
            spaceItemDecoration = new SpaceItemDecoration(getResources().getDimension(R.dimen.dp_8), getResources().getDimension(R.dimen.dp_8));
        }

        @Override
        protected void convert(@NonNull BaseViewHolder helper, RoomStateBean.DataDTO.ListRoomDTO item) {
            final int pos = mData.indexOf(item);
            helper.setText(R.id.floor_tv, item.getFloor() + "");
            ItemRoomAdapter roomAdapter = new ItemRoomAdapter(item.getRoom_list());
            RecyclerView recyclerView = helper.itemView.findViewById(R.id.rec);
            if (recyclerView.getItemDecorationCount() > 0) {
                recyclerView.removeItemDecoration(spaceItemDecoration);
            }
            recyclerView.addItemDecoration(spaceItemDecoration);
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 4));
            recyclerView.setAdapter(roomAdapter);
            roomAdapter.setOnItemClick(new SelectRoomFragment.OnItemClick() {
                @Override
                public void onItemClick(int pos1) {
                    Log.i(TAG, "onItemChildClick: " + pos1);
                    selcetPos = pos;
                    childSelect = pos1;
                    roomAdapter.setSelectPos(childSelect);
                    notifyDataSetChanged();
                    if (onSelectListener != null) {
                        onSelectListener.onSelect(item.getRoom_list().get(childSelect));
                    }
                }
            });
            if (selcetPos == pos) {
                roomAdapter.setSelectPos(childSelect);
            } else {
                roomAdapter.setSelectPos(-1);
            }


        }


        class ItemRoomAdapter extends BaseQuickAdapter<RoomStateBean.DataDTO.ListRoomDTO.RoomListDTO, BaseViewHolder> {
            private int selectPos = -1;

            public void setSelectPos(int selectPos) {
                this.selectPos = selectPos;
                notifyDataSetChanged();
            }

            public ItemRoomAdapter(@Nullable List<RoomStateBean.DataDTO.ListRoomDTO.RoomListDTO> data) {
                super(data);
                mLayoutResId = R.layout.adapter_select_room_room_item;
            }

            SelectRoomFragment.OnItemClick onItemClick;

            public void setOnItemClick(SelectRoomFragment.OnItemClick onItemClick) {
                this.onItemClick = onItemClick;
            }

            @Override
            protected void convert(@NonNull BaseViewHolder helper, RoomStateBean.DataDTO.ListRoomDTO.RoomListDTO item) {
                helper.setText(R.id.room_name, item.getRoom_number());
                if (selectPos == mData.indexOf(item)) {
                    helper.setTextColor(R.id.room_name, Color.parseColor("#FFFFFF"));
                    helper.setTextColor(R.id.desc, Color.parseColor("#FFFFFF"));
                    helper.setBackgroundDrawable(R.id.bg, ShapeUtil.getSoldRadiusBg(Color.parseColor("#007DCB"), (int) getResources().getDimension(R.dimen.dp_3)));
                } else {
                    helper.setBackgroundDrawable(R.id.bg, ShapeUtil.getSoldWithStrockRadiusBg(Color.WHITE, 2, Color.parseColor("#556A77"), (int) getResources().getDimension(R.dimen.dp_3)));
                    helper.setTextColor(R.id.room_name, Color.parseColor("#333333"));
                    helper.setTextColor(R.id.desc, Color.parseColor("#556A77"));
                }


                helper.setOnClickListener(R.id.bg, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onItemClick != null) {
                            onItemClick.onItemClick(mData.indexOf(item));
                        }
                    }
                });
//
            }


        }


    }


    public void getCanUseRoom(int room_property_id,String selectType, long startTime, long endTime, String state) {
        new Thread(new Runnable() {
            @Override
            public void run() {


                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("room_type", selectType);
                    jsonObject.put("start_time", startTime/1000);
                    jsonObject.put("price_project", 1);
                    jsonObject.put("enter_time", endTime/1000);
                    jsonObject.put("room_property_id", room_property_id);

                    jsonObject.put("room_status[0]", 1);
                    jsonObject.put("room_status[1]", 2);
                    String post = RequestUtil.requestHttps(RequestUtil.threeURL("/admin/RoomStatus/getUseableRoom"), "POST", RequestUtil.toKeyVal(jsonObject));
                    RoomStateBean roomStateBean = JSON.parseObject(post, RoomStateBean.class);
                    jsonObject.remove("start_time");
                    jsonObject.remove("enter_time");
                    jsonObject.remove("room_status");

//                    enter_time	是	string	入住时间
//                    days	是	string	天数
//                    price_project	是	string	价格方案
//                    intermediaries_id	是	string	协议id
//                    room_property_id	是	string	售卖方式
//                    room_type_id	是	string	房型

                    jsonObject.put("enter_time",startTime/1000);
                    jsonObject.put("days", TimeUtil.getDayInterval(startTime, endTime));
                    jsonObject.put("price_project", 1);
                    jsonObject.put("intermediaries_id", null);
                    jsonObject.put("room_property_id", 1);
                    jsonObject.put("room_type_id", selectType);


                    post = RequestUtil.requestHttps(RequestUtil.threeURL("/admin/RoomPrice/getPriceByDate"), "POST", RequestUtil.toKeyVal(jsonObject));

                    RoomPriceBean roomPriceBean = JSON.parseObject(post, RoomPriceBean.class);
                    curPriceList = roomPriceBean.getData();

                    List<RoomStateBean.DataDTO.ListRoomDTO.RoomListDTO> roomListDTOS = new ArrayList<>();
                    Map<Integer, RoomStateBean.DataDTO.ListRoomDTO.RoomListDTO> roomListDTOMap = new HashMap<>();
                    for (RoomStateBean.DataDTO.ListRoomDTO listRoomDTO : roomStateBean.getData().getList_room()) {
                        for (RoomStateBean.DataDTO.ListRoomDTO.RoomListDTO roomListDTO : listRoomDTO.getRoom_list()) {
                            roomListDTOMap.put(roomListDTO.getRoom_type(), roomListDTO);
                        }
                    }
                    for (Integer key : roomListDTOMap.keySet()) {
                        roomListDTOS.add(roomListDTOMap.get(key));
                    }
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            Log.i(TAG, "run: 2222222222222" + roomStateBean.getData().getList_room().size());

                            List<RoomStateBean.DataDTO.ListRoomDTO> list_room = roomStateBean.getData().getList_room();
                            roomAdapter.setNewData(list_room);
                        }
                    });


//                    org.json.JSONObject jsonObject= new JSONObject();
//                    jsonObject.put("GrogshopId", DeviceHelper.getsHOTELID());
//                    jsonObject.put("StartTime",startTime);
//                    jsonObject.put("EndTime",endTime);
//                    jsonObject.put("RoomType",code);
//                    jsonObject.put("mode",pageType);
//                    jsonObject.put("RateCode","");
//                    jsonObject.put("DirtyAvl",dirtyAvl);
//                    String request = RequestUtil.request(RequestUtil.secUrl("GetCanUsableRoomsInfo"), "GET", RequestUtil.toKeyVal(jsonObject));
//                    AvalableRoomPrice roomPrice = JSON.parseObject(request, AvalableRoomPrice.class);
//
//                    List<AvalableRoomPrice.ResultDTO> result = roomPrice.getResult();
//                    float totalPrice=0;
//
//                    for (AvalableRoomPrice.ResultDTO roomPrice1 : result) {
//                        List<AvalableRoomPrice.ResultDTO.RoomListDTO> roomList = roomPrice1.getRoomList();
//                        List<AvalableRoomPrice.ResultDTO.DaypriceDTO> dayprice1 = roomPrice1.getDayprice();
//                        List<List<RoomStateBean.DataDTO.ListRoomDTO.RoomListDTO>> lists=new ArrayList<>();
//                        Map<String,String> flowmap=new HashMap<>();
//                        for(AvalableRoomPrice.ResultDTO.RoomListDTO roomListDTO:roomList){
//                            RoomStateBean.DataDTO.ListRoomDTO.RoomListDTO resultDTO = DeviceHelper.queryRoomByRoomNum(roomListDTO.getRoomNo());
//                            flowmap.put(resultDTO.getFloor(),resultDTO.getFloor());
//                        }
//                        Log.i(TAG, "run: "+flowmap.size());
//
//                        for(String s:flowmap.keySet()){
//                            List<RoomStateBean.DataDTO.ListRoomDTO.RoomListDTO> list=new ArrayList<>();
//                            int index=0;
//                            for(AvalableRoomPrice.ResultDTO.RoomListDTO roomListDTO:roomList){
//                                RoomStateBean.DataDTO.ListRoomDTO.RoomListDTO resultDTO = DeviceHelper.queryRoomByRoomNum(roomListDTO.getRoomNo());
//                                if(s.equals(resultDTO.getFloor())){
//                                    resultDTO.setPrices(dayprice1);
//                                    list.add(resultDTO);
//                                }
//
//                                index++;
//                            }
//                            Log.i(TAG, "run: "+list.size());
//                            Collections.sort(list, new Comparator<RoomStateBean.DataDTO.ListRoomDTO.RoomListDTO>() {
//                                @Override
//                                public int compare(RoomStateBean.DataDTO.ListRoomDTO.RoomListDTO o1, RoomStateBean.DataDTO.ListRoomDTO.RoomListDTO o2) {
//                                    int roomNum=   Integer.parseInt(o1.getRoomNo());
//                                    int roomNum2=   Integer.parseInt(o2.getRoomNo());
//                                    return roomNum-roomNum2;
//                                }
//                            });
//                            lists.add(list);
//                        }
//                        mTopRec.post(new Runnable() {
//                            @Override
//                            public void run() {
//                                Log.i(TAG, "run: "+JSON.toJSONString(lists));
//                                Collections.reverse(lists);
//                                roomAdapter.setNewData(lists);
//                            }
//                        });
//
//
//                    }
//                    mTopRec.post(new Runnable() {
//                        @Override
//                        public void run() {
//                            if(result.isEmpty()){
//                                roomAdapter.setNewData(new ArrayList<>());
//                                if(onSelectListener!=null) {
//                                    onSelectListener.onSelect(null);
//                                }
//
//                            }
//                        }
//                    });


                } catch (Exception e) {
                    e.printStackTrace();
                    Log.i(TAG, "run: ");
                }


            }
        }).start();


    }


//    private void getRoomTypes(){
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    JSONObject jsonObject=new JSONObject();
//                    jsonObject.put("gSId",17653);
//                    String request = RequestUtil.request(RequestUtil.formateUrl("preGoods/ph"), "POST", RequestUtil.getReqJson("listWithCondition", jsonObject).toString());
//                    RoomType roomType = JSON.parseObject(request, RoomType.class);
//                    if(roomType.getRetcode()==0){
//
//                    }
//
//
//                }catch (Exception e){
//                    e.printStackTrace();
//                }
//
//
//            }
//        }).start();
//
//
//
//    }
//
//    public void getRoomDetail(int id){
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    try {
//                        JSONObject jsonObject=new JSONObject();
//                        jsonObject.put("gSId",17653);
//                        jsonObject.put("gId",id);
//                        String request = RequestUtil.request(RequestUtil.formateUrl("preGoods/ph"), "POST", RequestUtil.getReqJson("listByPage", jsonObject).toString());
//
//
//
//                    }catch (Exception e){
//                        e.printStackTrace();
//                    }
//
//
//
//                }
//            }).start();
//
//
//    }


}
