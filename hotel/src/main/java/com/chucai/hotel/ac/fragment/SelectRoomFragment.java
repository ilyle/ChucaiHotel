package com.chucai.hotel.ac.fragment;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.alibaba.fastjson.JSON;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chucai.hotel.R;
import com.chucai.hotel.bean.NewRoom;
import com.chucai.hotel.bean.RoomPriceBean;
import com.chucai.hotel.bean.RoomStateBean;
import com.chucai.hotel.core.DeviceHelper;
import com.chucai.hotel.http.RequestUtil;
import com.xtf.xtflib.util.ShapeUtil;
import com.xtf.xtflib.widget.common.SpaceItemDecoration;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SelectRoomFragment extends RelativeLayout {
    private static final String TAG = "SelectRoomFragment";
    private int pageType=0;
    private RecyclerView mTopRec;
    private RecyclerView mRoomRec;
    TopRecAdapter topRecAdapter;
    RoomAdapter roomAdapter;
    private long startTime;
    private long endTime;
    private int price_project ;
    private int room_property_id ;
    private int room_type_id;

    private int dirtyAvl;
    OnSelectListener onSelectListener;
    View inflate;
    SimpleDateFormat simpleDateFormat=new SimpleDateFormat("MM月dd日");


    public SelectRoomFragment(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
         inflate = View.inflate(getContext(),R.layout.fragment_select_room, null);
            RelativeLayout.LayoutParams lps=new RelativeLayout.LayoutParams(-1,-1);
          addView(inflate,lps);

    }
    public void setRoom_property_id(int room_property_id) {
        this.room_property_id = room_property_id;
    }

    public void setPrice_project(int price_project) {
        this.price_project=price_project;

    }
    public void setRoom_type_id(int room_type_id) {
        this.room_type_id = room_type_id;
    }


    public void setPageType(int pageType) {
        this.pageType = pageType;
    }

    public void setData(long startTime, long endTime){
        this.startTime=startTime;
        this.endTime=endTime;
        initView(inflate);
    }

    public void setOnSelectListener(OnSelectListener onSelectListener) {
        this.onSelectListener = onSelectListener;
    }

//    public SelectRoomFragment(String startTime, String endTime) {
//        this.startTime=startTime;
//        this.endTime=endTime;
//    }
//
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//
//
//        return inflate;
//    }

    private void initView(View view) {
        mTopRec = (RecyclerView) view.findViewById(R.id.top_rec);
        mTopRec.addItemDecoration(new SpaceItemDecoration(getResources().getDimension(R.dimen.dp_5), 0));
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mTopRec.setLayoutManager(linearLayoutManager);
        List<NewRoom.DataDTO.ListDTO> resultDTOS = new ArrayList<>();
        Map<Integer, NewRoom.DataDTO.ListDTO> allRoomTypeList = DeviceHelper.getAllRoomTypeList();
        for (Integer s : allRoomTypeList.keySet()) {
            resultDTOS.add(allRoomTypeList.get(s));
        }
        topRecAdapter=new TopRecAdapter(resultDTOS);
        mTopRec.setAdapter(topRecAdapter);
        topRecAdapter. settoselectitme(room_type_id);
        mRoomRec = (RecyclerView) view.findViewById(R.id.room_rec);
        mRoomRec.setLayoutManager(new LinearLayoutManager(getContext()));
        roomAdapter=new RoomAdapter(new ArrayList<>());
        mRoomRec.setAdapter(roomAdapter);
        getCanUseRoom(room_property_id,resultDTOS.get(0).getRoom_type()+"",startTime,endTime,dirtyAvl+"");

    }






    //public void getCanUseRoom(String selectType, String startTime, String endTime,String state) {
    public void getCanUseRoom(int room_property_id,String selectType, long startTime, long endTime, String state) {
        new Thread(new Runnable() {
            @Override
            public void run() {


                try {
                    JSONObject jsonObject=new JSONObject();
                    jsonObject.put("room_type", selectType);
                    jsonObject.put("start_time", startTime/1000);
                    jsonObject.put("price_project", price_project);
                    jsonObject.put("enter_time", endTime/1000);
                    jsonObject.put("room_property_id", room_property_id);

                    jsonObject.put("room_status[0]", 1);
                    jsonObject.put("room_status[1]", 2);
                    String post = RequestUtil.requestHttps(RequestUtil.threeURL("/admin/Room/getAllRoomStatus"), "POST",RequestUtil.toKeyVal(jsonObject) );
                    RoomStateBean roomStateBean=JSON.parseObject(post, RoomStateBean.class);
                    jsonObject.remove("start_time");
                    jsonObject.remove("enter_time");
                    jsonObject.remove("room_status");
                    post = RequestUtil.requestHttps("/admin/RoomPrice/getRoomPriceByRoomType", "POST", RequestUtil.toKeyVal(jsonObject));

                    RoomPriceBean roomPriceBean=JSON.parseObject(post,RoomPriceBean.class);
                    RoomPriceBean.DataDTO dataDTO = roomPriceBean.getData().get(0);

                   int totalPrice=0;


                    List<RoomStateBean.DataDTO.ListRoomDTO.RoomListDTO> roomListDTOS=new ArrayList<>();
                    Map<Integer,RoomStateBean.DataDTO.ListRoomDTO.RoomListDTO>  roomListDTOMap=new HashMap<>();
                    for(RoomStateBean.DataDTO.ListRoomDTO listRoomDTO:roomStateBean.getData().getList_room() ){
                        for(RoomStateBean.DataDTO.ListRoomDTO.RoomListDTO roomListDTO:listRoomDTO.getRoom_list()){
                            roomListDTOMap.put(roomListDTO.getRoom_type(),roomListDTO);
                        }
                    }
                    for(Integer key:roomListDTOMap.keySet()){
                        roomListDTOS.add(roomListDTOMap.get(key));
                    }
                 mTopRec.post(new Runnable() {
                     @Override
                     public void run() {
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
                }






            }
        }).start();


    }


    private class TopRecAdapter extends BaseQuickAdapter<NewRoom.DataDTO.ListDTO, BaseViewHolder> {
        private int selectPos = 0;

        public void setSelectPos(int selectPos) {
            this.selectPos = selectPos;
            notifyDataSetChanged();

        }

        public void settoselectitme(int seleid) {
            int i;
            int setlocaid=0;
            NewRoom.DataDTO.ListDTO sselid;
            for (i=0;i<mData.size();i++ )
            {
                sselid=mData.get(i);
                if (sselid.getId()==seleid)
                {
                    setSelectPos(i);
                    return ;
                }
            }

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
                    setSelectPos(index);
                    getCanUseRoom(room_property_id,item.getRoom_type()+"",startTime,endTime,dirtyAvl+"");
                }
            });

        }
    }


    private class RoomAdapter extends BaseQuickAdapter<RoomStateBean.DataDTO.ListRoomDTO,BaseViewHolder>{
        private int selcetPos=-1;
        private int childSelect=-1;
        SpaceItemDecoration spaceItemDecoration;

        @Override
        public void setNewData(@Nullable List<RoomStateBean.DataDTO.ListRoomDTO> data) {
//            selcetPos=-1;
            childSelect=-1;
            super.setNewData(data);
        }

        public void setSelcetPos(int selcetPos) {
            this.selcetPos = selcetPos;

        }


        public RoomAdapter(@Nullable List<RoomStateBean.DataDTO.ListRoomDTO> data) {
            super(data);
            mLayoutResId=R.layout.adapter_select_room_floor_item;
             spaceItemDecoration = new SpaceItemDecoration(getResources().getDimension(R.dimen.dp_8), getResources().getDimension(R.dimen.dp_8));
        }

        @Override
        protected void convert(@NonNull BaseViewHolder helper,RoomStateBean.DataDTO.ListRoomDTO item) {
            final  int pos=mData.indexOf(item);
            helper.setText(R.id.floor_tv,item.getFloor()+"");
            ItemRoomAdapter roomAdapter=new ItemRoomAdapter(item.getRoom_list());
            RecyclerView recyclerView= helper.itemView.findViewById(R.id.rec);
            if(recyclerView.getItemDecorationCount()>0) {
                recyclerView.removeItemDecoration(spaceItemDecoration);
            }
            recyclerView.addItemDecoration(spaceItemDecoration);
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(),4));
            recyclerView.setAdapter(roomAdapter);
            roomAdapter.setOnItemClick(new OnItemClick() {
                @Override
                public void onItemClick(int pos1) {
                    Log.i(TAG, "onItemChildClick: "+pos1);
                    selcetPos=pos;
                    childSelect=pos1;
                    roomAdapter.setSelectPos(childSelect);
                    notifyDataSetChanged();
                    if(onSelectListener!=null){
                        onSelectListener.onSelect(item.getRoom_list().get(childSelect));
                    }
                }
            });
            if(selcetPos==pos){
                roomAdapter.setSelectPos(childSelect);
            }else {
                roomAdapter.setSelectPos(-1);
            }


        }






        class ItemRoomAdapter extends BaseQuickAdapter<RoomStateBean.DataDTO.ListRoomDTO.RoomListDTO,BaseViewHolder>{
            private int selectPos=-1;

            public void setSelectPos(int selectPos) {
                this.selectPos = selectPos;
                notifyDataSetChanged();
            }

            public ItemRoomAdapter(@Nullable List<RoomStateBean.DataDTO.ListRoomDTO.RoomListDTO> data) {
                super(data);
                mLayoutResId=R.layout.adapter_select_room_room_item;
            }

            OnItemClick onItemClick;

            public void setOnItemClick(OnItemClick onItemClick) {
                this.onItemClick = onItemClick;
            }

            @Override
            protected void convert(@NonNull BaseViewHolder helper, RoomStateBean.DataDTO.ListRoomDTO.RoomListDTO item) {
                helper.setText(R.id.room_name,item.getRoom_number());
                if(selectPos==mData.indexOf(item)){
                    helper.setTextColor(R.id.room_name,Color.parseColor("#FFFFFF"));
                    helper.setTextColor(R.id.desc,Color.parseColor("#FFFFFF"));
                    helper.setBackgroundDrawable(R.id.bg, ShapeUtil.getSoldRadiusBg(Color.parseColor("#007DCB"),(int)getResources().getDimension(R.dimen.dp_3)));
                }else {
                    helper.setBackgroundDrawable(R.id.bg, ShapeUtil.getSoldWithStrockRadiusBg(Color.WHITE,2,Color.parseColor("#556A77"),(int)getResources().getDimension(R.dimen.dp_3)));
                    helper.setTextColor(R.id.room_name,Color.parseColor("#333333"));
                    helper.setTextColor(R.id.desc,Color.parseColor("#556A77"));
                }


                helper.setOnClickListener(R.id.bg, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                            if(onItemClick!=null){
                                onItemClick.onItemClick(mData.indexOf(item));
                            }
                    }
                });
//
            }


        }


    }
    public  interface OnItemClick{

        void onItemClick(int pos);
    }






    public interface OnSelectListener{

        void onSelect(RoomStateBean.DataDTO.ListRoomDTO.RoomListDTO resultDTO);

    }
}
