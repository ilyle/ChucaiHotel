package com.chucai.hotel.control;

import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.os.Handler;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.chucai.hotel.bean.AddbillMessage;
import com.chucai.hotel.bean.BookMessage;
import com.chucai.hotel.bean.TostayMessage;
import com.chucai.hotel.core.DeviceHelper;
import com.chucai.hotel.http.RequestUtil;
import com.xtf.xtflib.util.NetUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class NetControl {


    public void lockRoom(String roomNo, int type, Handler handler){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    JSONObject jsonObject=new JSONObject();
                    jsonObject.put("GrogshopId", DeviceHelper.getsHOTELID());
                    jsonObject.put("UserCode", DeviceHelper.getsDeviceId());
                    jsonObject.put("type",type);
                    jsonObject.put("type",roomNo);
                    jsonObject.put("Reason", "自助机锁房");
                    jsonObject.put("lockIP", NetUtil.getIPAddress(DeviceHelper.getContext()));
                    String request = RequestUtil.request(RequestUtil.secUrl("LockRoom"), "POST", RequestUtil.toKeyVal(jsonObject));

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();

    }

    AddbillMessage.DataDTO frmjontonewjon(BookMessage.DataDTO orderListDTO)
    {
        BookMessage.DataDTO pnewstru=JSON.parseObject(JSON.toJSONString(orderListDTO, SerializerFeature.WriteMapNullValue), BookMessage.DataDTO.class);
        pnewstru.setRoom_list(null);
        AddbillMessage.DataDTO tmpstru=  JSON.parseObject(JSON.toJSONString(pnewstru, SerializerFeature.WriteMapNullValue), AddbillMessage.DataDTO.class);
        List<AddbillMessage.DataDTO.RoomListDTO> nrmlist= new ArrayList();
        AddbillMessage.DataDTO.RoomListDTO tmpadd;
        List< BookMessage.DataDTO.RoomListDTO> proomlist=orderListDTO.getRoom_list();
        int ii;
        String prmstr;
        String pold;
        String nstrs='"'+"room_service"+'"'+':'+"[]";
        for (ii=0;ii<proomlist.size();ii++)
        {
            prmstr=JSON.toJSONString(proomlist.get(ii)) ;
            pold='"'+"room_service"+'"'+':'+'"'+proomlist.get(0).getRoom_service()+'"';
            prmstr=prmstr.replace(pold,nstrs);
            tmpadd= JSON.parseObject(prmstr, AddbillMessage.DataDTO.RoomListDTO.class);
            tmpadd.setRoom_service(JSON.parseArray(JSON.toJSONString(orderListDTO.getRoom_list().get(ii).getRoom_service_list(), SerializerFeature.WriteMapNullValue), AddbillMessage.DataDTO.RoomListDTO.RoomServiceDTO.class));
            tmpadd.setCustom_cash_pledge(null);
            nrmlist.add(tmpadd);
        }
        tmpstru.setRoom_list(nrmlist);
        tmpstru.setRoom_property_id(orderListDTO.getRoom_list().get(0).getRoom_service_list().get(0).getRoom_property_id());
        return tmpstru;


    }
    TostayMessage.DataDTO datatotostaystru(BookMessage.DataDTO orderListDTO)
    {
        BookMessage.DataDTO pnewstru=JSON.parseObject(JSON.toJSONString(orderListDTO, SerializerFeature.WriteMapNullValue), BookMessage.DataDTO.class);
        pnewstru.setRoom_list(null);
        TostayMessage.DataDTO tmpstru=  JSON.parseObject(JSON.toJSONString(pnewstru, SerializerFeature.WriteMapNullValue), TostayMessage.DataDTO.class);
        List<TostayMessage.DataDTO.RoomListDTO> nrmlist= new ArrayList();
        TostayMessage.DataDTO.RoomListDTO tmpadd;
        List< BookMessage.DataDTO.RoomListDTO> proomlist=orderListDTO.getRoom_list();
        int ii;
        String prmstr;
        String pold;
        String nstrs='"'+"room_service"+'"'+':'+"[]";
        List<TostayMessage.DataDTO.RoomListDTO.PriceOriginalDTO> price;
        List<TostayMessage.DataDTO.RoomListDTO.PriceOriginalDTO> price_original;
        TostayMessage.DataDTO.RoomInfoDTO.RoomPricesDTO pricess;
        TostayMessage.DataDTO.RoomListDTO.PriceOriginalDTO ptmpprice;
        for (ii=0;ii<proomlist.size();ii++)
        {
            prmstr=JSON.toJSONString(proomlist.get(ii)) ;
            pold='"'+"room_service"+'"'+':'+'"'+proomlist.get(0).getRoom_service()+'"';
            prmstr=prmstr.replace(pold,nstrs);
            tmpadd= JSON.parseObject(prmstr, TostayMessage.DataDTO.RoomListDTO.class);
            pricess=tmpadd.getRoom_prices().get(0);
            price=new ArrayList();
            ptmpprice=new TostayMessage.DataDTO.RoomListDTO.PriceOriginalDTO();
            ptmpprice.setDate(pricess.getDate());
            ptmpprice.setPrice(pricess.getRoom_price());
            price.add(ptmpprice);
            tmpadd.setPrice(price);
            price_original=new ArrayList();
            ptmpprice=new TostayMessage.DataDTO.RoomListDTO.PriceOriginalDTO();
            ptmpprice.setDate(pricess.getDate());
            ptmpprice.setPrice(pricess.getOriginal_room_price());
            price_original.add(ptmpprice);
            tmpadd.setPrice_original(price_original);

            nrmlist.add(tmpadd);
        }

        tmpstru.setRoom_list(nrmlist);
        tmpstru.setRoom_property_id(orderListDTO.getRoom_list().get(0).getRoom_service_list().get(0).getRoom_property_id());

        return tmpstru;
    }

    public String  pay(double price, int type, String outTradeNo, BookMessage.DataDTO orderListDTO){

      //  Log.d("pyq chk ","type="+type) ;

//        http://192.168.0.158:8058/page/pay?machineId=机器号&money=100&outTradeNo=123456
        try {
            JSONObject jsonObject=new JSONObject();

/*
            JSONArray check= new JSONArray();
            if (type==0)
            jsonObject.put("bill_msg",JSON.toJSONString(frmjontonewjon(orderListDTO), SerializerFeature.WriteMapNullValue));
            else
            jsonObject.put("bill_msg",JSON.toJSONString(datatotostaystru(orderListDTO), SerializerFeature.WriteMapNullValue));
            JSONObject jsonchked = new JSONObject();
            jsonchked.put("account_id",70);
            jsonchked.put("receive_money",price);
            jsonchked.put("auth_code","");
            jsonchked.put("memo","");
            check.put(jsonchked);
            jsonObject.put("check",check);
            String request;
            if (type==1)
            request = RequestUtil.requestHttps(RequestUtil.threeURL("/admin/RoomBill/toStay"), "POST", RequestUtil.toKeyVal(jsonObject));
            else
            request = RequestUtil.requestHttps(RequestUtil.threeURL("/admin/RoomBill/addOffLineBill"), "POST", RequestUtil.toKeyVal(jsonObject));


 */



///admin/RoomBill/addOffLineBill


            jsonObject.put("machineId",DeviceHelper.getsDeviceId());
            jsonObject.put("money",price);
            jsonObject.put("type",type);
            jsonObject.put("outTradeNo",outTradeNo);
            jsonObject.put("token",DeviceHelper.getAuthToken());
            jsonObject.put("header",DeviceHelper.getsCookies());
            // jsonObject.put("data",JSON.toJSONString(frmjontonewjon(orderListDTO), SerializerFeature.WriteMapNullValue));
            if (type==1)
                jsonObject.put("data", JSON.toJSONString(datatotostaystru( orderListDTO), SerializerFeature.WriteMapNullValue) );
            else
                jsonObject.put("data", JSON.toJSONString(frmjontonewjon( orderListDTO), SerializerFeature.WriteMapNullValue));
            //    String request =JSON.toJSONString(datatotostaystru( orderListDTO), SerializerFeature.WriteMapNullValue) ;

            String request = RequestUtil.request(RequestUtil.formateUrl("page/pay"), "POST", RequestUtil.toKeyVal(jsonObject));



            return request;


        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
        /*
        //type=1
/admin/RoomBill/addOffLineBill
bill_msg=
{"bill_source":1,"coupon_discounts":0,"coupon_id":null,"intermediaries_id":0,"leave_time_plan":1627356258,"link_man":"潘裕强","link_phone":"13512345678","memo":null,"other_bill_code":null,"price_project":1,"room_list":[{"cash_pledge":130.11,"cash_pledge_original":130.11,"custom_cash_pledge":null,"custom_room_price":null,"enter_msg":[{"card_type":"1","gender":"1","id_card":"44092119690224403X","link_phone":"13512345678","name":"潘裕强"}],"id":200,"price":[{"date":"2021-07-26","price":680}],"price_original":[{"date":"2021-07-26","price":680}],"room_id":200,"room_number":"408","room_service":[{"before_end_time":"18:00","cancelable":1,"create_time":1623381652,"date":null,"id":622,"is_show":1,"member_grade_id":1,"price":0,"room_property_id":1,"room_type_id":17,"service_content":null,"service_name":"无套餐","status":1,"sys_setting":1,"week":null}],"room_service_selected":622,"room_type_id":17,"room_type_name":"标准单间"}],"room_property_id":1,"stay_time":1}&check=[{"account_id":70,"receive_money":810.11,"auth_code":"","memo":""}]

//type=0
/admin/RoomBill/toStay
bill_msg={"add_reduce_money":0,"add_reduce_reason":"","admin_id_close":null,"admin_id_create":1,"admin_name":"1","admin_name_close":null,"admin_name_create":"1","already_pay":0,"already_pay_cash_pledge":0,"amount_reduction":0,"bill_code":"1627270722550JIXZEID","bill_money":1980,"bill_source":1,"bill_source_name":"步入","bill_status":3,"bill_status_name":"待入住","cash_pledge":100.16,"common_code":"4c242bfc55096995fec3edc9dda3cc80","connect_code":"","consume_type":1,"consume_type_stay_time":1,"create_time":1627270722,"enter_time":null,"enter_time_plan":"1627272000","evaluate_status":0,"extend_hours":null,"id":132,"immobilization":0,"intermediaries_id":0,"is_arrage":0,"last_edit_time":1627270722,"leave_time":null,"leave_time_plan":1627358399,"link_man":"潘裕强","link_phone":"13512345678","memo":"","old_consume_type":null,"original_cash_pledge":100.16,"other_bill_code":"","over_memo":null,"over_money":null,"pay_type_id":0,"pay_type_name":null,"price_project":1,"room_count":1,"room_info":{"already_consume":0,"already_pay":0,"bill_id":132,"bill_money":1980,"cash_pledge":100.16,"create_time":1627270722,"custom_cash_pledge":100,"end_time":1627358399,"enter_code":"","id":165,"intermediaries_id":0,"lock_id":"","main_room":1,"max_user_count":2,"new_room_number":null,"original_room_price":1980,"password_status":0,"permission_begin":0,"permission_end":0,"price_project":1,"qr_url":"","room_code":null,"room_id":0,"room_number":"","room_price":1980,"room_prices":[{"date":"2021-07-26","original_room_price":1980,"room_price":1980}],"room_service":"1","room_service_list":[{"before_end_time":"18:00","cancelable":1,"create_time":1620980992,"date":null,"id":1,"is_show":1,"member_grade_id":1,"price":0,"room_property_id":1,"room_type_id":1,"service_content":null,"service_name":"无套餐","status":1,"sys_setting":1,"week":null}],"room_service_name":"无套餐","room_service_price":0,"room_service_selected":1,"room_type_id":1,"room_type_name":"大床房","start_time":1627270722,"status":1,"stay_time":1,"type_code":"","unit":2,"update_time":1627270722,"user_count":0,"user_list":[],"wifi_name":null,"wifi_password":null},"room_list":[{"already_consume":0,"already_pay":0,"bill_id":132,"bill_money":1980,"cash_pledge":100.16,"cash_pledge_original":0,"create_time":1627270722,"custom_cash_pledge":100.16,"custom_room_price":null,"end_time":1627358399,"enter_code":"","enter_msg":[{"card_type":1,"gender":1,"id_card":"44092119690224403X","link_phone":"13512345678","name":"潘裕强"}],"id":165,"intermediaries_id":0,"lock_id":"","main_room":1,"max_user_count":2,"new_room_number":null,"original_room_price":1980,"password_status":0,"permission_begin":0,"permission_end":0,"price":null,"price_original":null,"price_project":1,"qr_url":"","room_code":null,"room_id":198,"room_number":"1002","room_price":1980,"room_prices":[{"date":"2021-07-26","original_room_price":1980,"room_price":1980}],"room_service":[],"room_service_list":[{"before_end_time":"18:00","cancelable":1,"create_time":1620980992,"date":null,"id":1,"is_show":1,"member_grade_id":1,"price":0,"room_property_id":1,"room_type_id":1,"service_content":null,"service_name":"无套餐","status":1,"sys_setting":1,"week":null}],"room_service_name":"无套餐","room_service_price":0,"room_service_selected":1,"room_type_id":1,"room_type_name":"大床房","start_time":1627270722,"status":1,"stay_time":1,"type_code":"","unit":2,"update_time":1627270722,"user_count":0,"user_list":[],"wifi_name":null,"wifi_password":null}],"room_price":1980,"room_property_id":1,"room_property_name":"全日房","room_type_ids":[],"shop_id":6,"source_sign":"buru","stay_time":1,"stay_type":1,"third_part_bill_id":null,"total_commission_price":0,"unit":2,"user_count":0,"user_coupon_id":0,"user_type":1}&check=[{"account_id":70,"receive_money":2080.16,"auth_code":"","memo":""}]
         */

    }


    private void payOrder(BookMessage.DataDTO.RoomInfoDTO.RoomPricesDTO orderListDTO,int type){



    }

}
