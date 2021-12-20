package com.chucai.hotel.ac.fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chucai.hotel.R;
import com.chucai.hotel.ac.BookRoomActivity;
import com.chucai.hotel.ac.OrderSearchActivity;
import com.chucai.hotel.ac.SplashActviity;
import com.chucai.hotel.ac.dialog.PhoneHintDialog;
import com.chucai.hotel.bean.BookMessage;
import com.chucai.hotel.bean.getRoomBillList;
import com.chucai.hotel.http.RequestUtil;
import com.xtf.xtflib.util.DialogUtil;
import com.xtf.xtflib.util.PhoneUtil;
import com.xtf.xtflib.util.ShapeUtil;
import com.xtf.xtflib.util.TimeUtil;
import com.xtf.xtflib.util.ToastUtil;
import com.xtf.xtflib.widget.common.SpaceItemDecoration;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@SuppressLint("ValidFragment")
public class PhoneFragement extends Fragment {
    private static final String TAG = "PhoneFragement";
    private TextView mInputPhone;
    private ImageView mDeleteAll;
    private RecyclerView mRecNum;
    private ImageView mHuiShanBtn;
    private Button mOkBtn;
    private int type;
    SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @SuppressLint("ValidFragment")
    public PhoneFragement(int type) {
        this.type = type;
    }







    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_phone, container, false);
        initView(inflate);
        return inflate;


    }

    List<String> dataList=new ArrayList<>();

    private void initView(View inflate) {
        mInputPhone = (TextView) inflate.findViewById(R.id.input_phone);
        mDeleteAll = (ImageView) inflate.findViewById(R.id.delete_all);
        mRecNum = (RecyclerView) inflate.findViewById(R.id.rec_num);
        mHuiShanBtn = (ImageView) inflate.findViewById(R.id.hui_shan_btn);
        mOkBtn = (Button) inflate.findViewById(R.id.ok_btn);
        mRecNum.setLayoutManager(new GridLayoutManager(getActivity(),4));
        mRecNum.addItemDecoration(new SpaceItemDecoration(getContext().getResources().getDimension(R.dimen.dp_0),getContext().getResources().getDimension(R.dimen.dp_3)));
        dataList.clear();
        dataList.add("1");
        dataList.add("2");
        dataList.add("3");
        dataList.add("4");
        dataList.add("5");
        dataList.add("6");
        dataList.add("7");
        dataList.add("8");
        dataList.add("9");
        dataList.add("0");
        MyAdapter adapter=new MyAdapter(dataList);
        mRecNum.setAdapter(adapter);
        mHuiShanBtn.setBackground(ShapeUtil.getPressCheckedBg(getResources().getDrawable(R.drawable.quansan_nor),getResources().getDrawable(R.drawable.quansan_pre)));
//        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
//            @Override
//            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
//                Log.i(TAG, "onItemChildClick: ");
//                String s = dataList.get(position);
//                mInputPhone.setText(mInputPhone.getText().toString()+s);
//            }
//        });
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                Log.i(TAG, "onItemChildClick: ");
                String s = dataList.get(position);
                mInputPhone.setText(mInputPhone.getText().toString()+s);

            }
        });

        mHuiShanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mInputPhone.setText("");
            }
        });
        mDeleteAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!TextUtils.isEmpty(mInputPhone.getText().toString())) {
                    mInputPhone.setText(mInputPhone.getText().toString().substring(0, mInputPhone.getText().length() - 1));
                }
            }
        });
        mOkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    String phone = mInputPhone.getText().toString();
                    if (PhoneUtil.checkMobile(phone)) {
                        getOrderInfo(mInputPhone.getText().toString());
                    } else {
                        ToastUtil.showShort(getContext(), "手机号码输入不正确");
                    }
            }
        });


        mDeleteAll.setImageDrawable(ShapeUtil.getPressCheckedBg(getResources().getDrawable(R.drawable.huisan_icon_nor),getResources().getDrawable(R.drawable.huisan_icon_pre)));


    }

    private class MyAdapter extends BaseQuickAdapter<String,BaseViewHolder>{


        public MyAdapter(@Nullable List data) {
            super(data);
            mLayoutResId=R.layout.adapter_fragment_input_phone_item;
        }



        @Override
        protected void convert(@NonNull BaseViewHolder helper, String item) {
            helper.setText(R.id.btn_num,item);
            helper.setTextColor(R.id.btn_num, ShapeUtil.getPressCheckedColor(Color.parseColor("#333333"),Color.parseColor("#FFFFFF")));
            helper.setBackgroundDrawable(R.id.btn_num,ShapeUtil.getPressCheckedBg(getResources().getDrawable(R.drawable.shuzhi_nor),getResources().getDrawable(R.drawable.shuzhi_pre)));

//            helper.itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Log.i(TAG, "onItemChildClick: ");
////                String s = dataList.get(position);
//                mInputPhone.setText(mInputPhone.getText().toString()+item);
//                }
//            });
            helper.setOnClickListener(R.id.btn_num, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ////                String s = dataList.get(position);
                    Log.i(TAG, "onClick: ----------------");
                    mInputPhone.setText(mInputPhone.getText().toString()+item);
                }
            });
        }
    }


//    private void checkPhone(String phone){
//      final   Dialog dialog = DialogUtil.showWaitDialog(getActivity());
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//
//                try {
////                    JSONObject jsonObject=new JSONObject();
////                    jsonObject.put("tExpressCode",phone);
////                    jsonObject.put("tStatus",3);
////                    String request = RequestUtil.request(RequestUtil.DEFAULT_PATH + "preTrade/ph", "POST", RequestUtil.getReqJson("listWithCondition", jsonObject).toString());
////                    OrderBean orderBean= JSON.parseObject(request,OrderBean.class);
//                JSONObject jsonObject=new JSONObject();
//                    jsonObject.put("link_phone",phone);
//                    jsonObject.put("bill_status",3+"");
//
//                    String post = RequestUtil.requestHttps(RequestUtil.threeURL("/admin/RoomBill/getRoomBillList"), "POST", jsonObject.toString());
//                    BookMessage bookMessage=JSON.parseObject(post,BookMessage.class);
//
//
//
//                    mOkBtn.post(new Runnable() {
//                        @Override
//                        public void run() {
//                            dialog.dismiss();
//                            if(bookMessage.getData().getRoom_list().size()>0) {
//                                OrderSearchActivity orderSearchActivity = (OrderSearchActivity) getActivity();
//                                List<BookMessage.DataDTO.RoomListDTO> room_list = bookMessage.getData().getRoom_list();
//                                orderSearchActivity.showCheckRoomFagement(data.get(0));
//                            }else {
//                                ToastUtil.showShort(getContext(),"未查询到可以预定的房间");
//                            }
//                        }
//                    });
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    mOkBtn.post(new Runnable() {
//                        @Override
//                        public void run() {
//                            dialog.dismiss();
//                            ToastUtil.showShort(getContext(),"请稍后再试");
//                        }
//                    });
//                }
//
//            }
//        }).start();
//
//    }


    public void getOrderInfo(String data){
        final   Dialog dialog = DialogUtil.showWaitDialog(getActivity());
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    try {
//                        JSONObject jsonObject=new JSONObject();
//                        jsonObject.put("GrogshopId",DeviceHelper.getsHOTELID());
//                        jsonObject.put("IDCard","");
//                        jsonObject.put("Phone",data);
//                        jsonObject.put("Name","");
//                        jsonObject.put("Name","");
//                        jsonObject.put("AccNo","");
//                        jsonObject.put("ReseNo","");
//                        jsonObject.put("OTANo","");
//                        jsonObject.put("Status","");
//
//                        String request = RequestUtil.request(RequestUtil.secUrl("GetOrderInfo"), "GET", RequestUtil.toKeyVal(jsonObject));
//                        Log.i(TAG, "run: "+request);
//                        BookMessage bookMessage=JSON.parseObject(request,BookMessage.class);
//                        mOkBtn.post(new Runnable() {
//                            @Override
//                            public void run() {
//                                try {
//                                    List<BookMessage.ResultDTO.OrderListDTO> orderListDTOS=new ArrayList<>();
//                                    List<BookMessage.ResultDTO> result = bookMessage.getResult();
//                                    for(BookMessage.ResultDTO resultDTO:result){
//                                        List<BookMessage.ResultDTO.OrderListDTO> orderList = resultDTO.getOrderList();
//                                        for(BookMessage.ResultDTO.OrderListDTO orderListDTO:orderList){
//                                            String arr = orderListDTO.getDep();
//                                            Date parse = simpleDateFormat.parse(arr);
//                                            if(parse.getTime()>System.currentTimeMillis()&& !TimeUtil.isSameDay(parse,new Date())){
//                                                orderListDTOS.add(orderListDTO);
//                                            }
//                                        }
//                                    }
//                                    if(type==0){
//                                        if(orderListDTOS.isEmpty()){
//                                            BookRoomActivity bookRoomActivity= (BookRoomActivity) getActivity();
//                                            bookRoomActivity.showBookRoomFragment(mInputPhone.getText().toString());
//                                        }else {
//                                            PhoneHintDialog phoneHintDialog=new PhoneHintDialog(getActivity(),R.style.dim_dialog);
//                                            phoneHintDialog.setData("系统识识别到你手机绑定的订单信息，你\n" +
//                                                    "使用现房入住或订单入住\n","订单入住","现房入住");
//                                            phoneHintDialog.show();
//                                            phoneHintDialog.setOnBtnClick(new PhoneHintDialog.OnBtnClick() {
//                                                @Override
//                                                public void onLeftBtnClick() {
//
//                                                    phoneHintDialog.dismiss();
//                                                    Intent intent=new Intent(getActivity(),OrderSearchActivity.class);
//                                                    intent.putExtra("phone",mInputPhone.getText().toString());
//                                                    intent.putExtra("data",JSON.toJSONString(orderListDTOS));
//                                                    startActivity(intent);
//                                                    getActivity().finish();
//                                                }
//
//                                                @Override
//                                                public void onRightBtnClick() {
//                                                    phoneHintDialog.dismiss();
//                                                    BookRoomActivity bookRoomActivity= (BookRoomActivity) getActivity();
//                                                    bookRoomActivity.showBookRoomFragment(mInputPhone.getText().toString());
//                                                }
//                                            });
//
//                                        }
//                                    }else {
//                                        if(orderListDTOS.isEmpty()){
//                                            mOkBtn.post(new Runnable() {
//                                                @Override
//                                                public void run() {
//                                                    PhoneHintDialog phoneHintDialog=new PhoneHintDialog(getActivity(),R.style.dim_dialog);
//                                                    phoneHintDialog.setData("系统识未识别到你手机绑定的订单信息，你\n" +
//                                                            "使用现房入住或订单入住\n","现房入住","取消");
//                                                    phoneHintDialog.show();
//                                                    phoneHintDialog.setOnBtnClick(new PhoneHintDialog.OnBtnClick() {
//                                                        @Override
//                                                        public void onLeftBtnClick() {
//                                                            phoneHintDialog.dismiss();
//                                                            Intent intent=new Intent(getActivity(),BookRoomActivity.class);
//                                                            intent.putExtra("phone",mInputPhone.getText().toString());
//                                                            getActivity().startActivity(intent);
//                                                            getActivity().finish();
//                                                        }
//
//                                                        @Override
//                                                        public void onRightBtnClick() {
//
//                                                        }
//                                                    });
//
//                                                }
//                                            });
//
//                                        }else {
//                                            mOkBtn.post(new Runnable() {
//                                                @Override
//                                                public void run() {
//                                                    OrderSearchActivity orderSearchActivity= (OrderSearchActivity) getActivity();
//                                                    orderSearchActivity.showBookComfineFragment(orderListDTOS);
//                                                }
//                                            });
//
//
//
//                                        }
//                                    }
//
//
//
//                                }catch (Exception e){
//                                    e.printStackTrace();
//                                }
//                            }
//                        });
//
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }finally {
//                        mOkBtn.post(new Runnable() {
//                            @Override
//                            public void run() {
//                                dialog.dismiss();
//                            }
//                        });
//                    }
//
//
//                }
//            }).start();


        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("link_phone", data);
                    jsonObject.put("bill_status", 3 + "");

                    String post = RequestUtil.requestHttps(RequestUtil.threeURL("/admin/RoomBill/getRoomBillList"), "POST", jsonObject.toString());
                    getRoomBillList rdbookMessage = JSON.parseObject(post, getRoomBillList.class);

                    List<BookMessage.DataDTO.RoomListDTO> orderListDTOS;
                    BookMessage.DataDTO data1;
                    if(rdbookMessage.getCode()==0) {
                        if (rdbookMessage.getData().getList().size()>0) {
                            JSONObject jsonbook = new JSONObject();
                            jsonbook.put("bill_id", rdbookMessage.getData().getList().get(0).getId());
                            String post1 = RequestUtil.requestHttps(RequestUtil.threeURL("/admin/RoomBill/getRoomBillDetail"), "POST", jsonbook.toString());
                            BookMessage bookMessage = JSON.parseObject(post1, BookMessage.class);
                            orderListDTOS = bookMessage.getData().getRoom_list();
                            data1 = bookMessage.getData();
                            BookMessage.DataDTO.RoomListDTO result = orderListDTOS.get(0);
                            int price = data1.getBill_money() - data1.getAlready_pay();
                            data1.setRoom_price(price);



                        } else
                        {
                            orderListDTOS= new ArrayList<>();
                            data1=new BookMessage.DataDTO();
                        }

/*
                    BookMessage bookMessage = JSON.parseObject(post, BookMessage.class);

                   if(bookMessage.getCode()==0) {
                       List<BookMessage.DataDTO.RoomListDTO> orderListDTOS = new ArrayList<>();
                       List<BookMessage.DataDTO.RoomListDTO> result = bookMessage.getData().getRoom_list();


                       for (BookMessage.DataDTO.RoomListDTO roomInfoDTO : result) {


                           if (roomInfoDTO.getStart_time() * 1000 > System.currentTimeMillis() && !TimeUtil.isSameDay(new Date(roomInfoDTO.getStart_time() * 1000), new Date())) {
                               orderListDTOS.add(roomInfoDTO);
                           }
                       }

                       BookMessage.DataDTO data1 = bookMessage.getData();
                       data1.setLink_phone(mInputPhone.getText().toString());
                       data1.setRoom_list(orderListDTOS);

 */
                       mInputPhone.post(new Runnable() {
                           @Override
                           public void run() {
                               dialog.dismiss();

                               if (type == 0) {
                                   Log.i(TAG, "run: ---------------------");
                                   if (orderListDTOS.isEmpty()) {
                                       BookRoomActivity bookRoomActivity = (BookRoomActivity) getActivity();
                                       bookRoomActivity.showBookRoomFragment(mInputPhone.getText().toString());
                                   } else {
                                       PhoneHintDialog phoneHintDialog = new PhoneHintDialog(getActivity(), R.style.dim_dialog);
                                       phoneHintDialog.setData("系统识识别到你手机绑定的订单信息，你\n" +
                                               "使用现房入住或订单入住\n", "订单入住", "现房入住");
                                       phoneHintDialog.show();
                                       phoneHintDialog.setOnBtnClick(new PhoneHintDialog.OnBtnClick() {
                                           @Override
                                           public void onLeftBtnClick() {

                                               phoneHintDialog.dismiss();
                                               Intent intent = new Intent(getActivity(), OrderSearchActivity.class);
                                               intent.putExtra("phone", mInputPhone.getText().toString());
                                               intent.putExtra("data", data1);
                                               startActivity(intent);
                                               getActivity().finish();
                                           }

                                           @Override
                                           public void onRightBtnClick() {
                                               phoneHintDialog.dismiss();
                                               BookRoomActivity bookRoomActivity = (BookRoomActivity) getActivity();
                                               bookRoomActivity.showBookRoomFragment(mInputPhone.getText().toString());
                                           }
                                       });

                                   }
                               } else {
                                   Log.i(TAG, "run: ---------------------");
                                   if (orderListDTOS.isEmpty()) {
                                       mOkBtn.post(new Runnable() {
                                           @Override
                                           public void run() {
                                               PhoneHintDialog phoneHintDialog = new PhoneHintDialog(getActivity(), R.style.dim_dialog);
                                               phoneHintDialog.setData("系统识未识别到你手机绑定的订单信息，你\n" +
                                                       "使用现房入住或订单入住\n", "现房入住", "取消");
                                               phoneHintDialog.show();
                                               phoneHintDialog.setOnBtnClick(new PhoneHintDialog.OnBtnClick() {
                                                   @Override
                                                   public void onLeftBtnClick() {
                                                       phoneHintDialog.dismiss();
                                                       Intent intent = new Intent(getActivity(), BookRoomActivity.class);
                                                       intent.putExtra("phone", mInputPhone.getText().toString());
                                                       getActivity().startActivity(intent);
                                                       getActivity().finish();
                                                   }

                                                   @Override
                                                   public void onRightBtnClick() {

                                                   }
                                               });

                                           }
                                       });

                                   } else {
                                       mOkBtn.post(new Runnable() {
                                           @Override
                                           public void run() {

                                               OrderSearchActivity orderSearchActivity = (OrderSearchActivity) getActivity();
                                               orderSearchActivity.showBookComfineFragment(data1);
                                           }
                                       });


                                   }


                               }

                           }
                       });
                   }else {
                       SplashActviity.getRoomMessage(getActivity());
                       mHuiShanBtn.post(new Runnable() {
                           @Override
                           public void run() {

                               dialog.dismiss();
                           }
                       });

                       return;
                   }


                }catch (Exception e){
                e.printStackTrace();}

            }

        }).start();


    }


    public void queryPhoneType(String phome) throws Exception{
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("keyword",phome);

        String post = RequestUtil.requestHttps(RequestUtil.threeURL("admin/RoomBill/getCommonUsersByPhoneOrName"), "POST", RequestUtil.toKeyVal(jsonObject));




    }

}
