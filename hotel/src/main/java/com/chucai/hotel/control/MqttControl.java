package com.chucai.hotel.control;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.chucai.hotel.bean.AddbillMessage;
import com.chucai.hotel.bean.BookMessage;
import com.chucai.hotel.core.DeviceHelper;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MqttControl implements MqttCallback {
    private static final String TAG = "MqttControl";
    public static final String MQTT_USER_NAME="admin";
    public static final String MQTT_PASSWORD="password";
    private static final int MQTT_CONNECT_TIME_OUT=8*1000;

    private static MqttControl sMqttControl;
    private static  String  MQTT_URL="tcp://"+"81.71.66.18"+":1883";

    private MqttClient mMqttClient;
    private MqttConnectOptions mMqttOption;

    String mqttTopic[];

    public MqttControl() {
        mqttTopic=   new String[]{
                "android/machine_message/"+DeviceHelper.getsDeviceId(),"android/allmessage"
        };

    }


    public static MqttControl newInstance() {
        if(sMqttControl==null){
            sMqttControl=new MqttControl();
        }
        return sMqttControl;

    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler=new Handler(){


        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    startMqtt();
                    break;

            }
        }
    };


    public void startMqtt(){

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    initAndConnectMqtt();
                } catch (Exception e) {
                    e.printStackTrace();
                    connectMqttDelay();
                }

            }
        }).start();

    }

    private synchronized void initAndConnectMqtt() throws Exception{
        //移除  之前发生的延迟消息，避免多次 重复连接

        if (mMqttClient != null) {
            mMqttClient.setCallback(null);
            mMqttClient.close(true);
            mMqttClient = null;
            mMqttOption = null;

        }

        mMqttClient = new MqttClient(MQTT_URL, DeviceHelper.getsDeviceId()+"_"+System.currentTimeMillis() + "", new MemoryPersistence());
        mMqttOption=new MqttConnectOptions();
//        mMqttOption.setWill(MQTT_HEART_BEAT_MSG,(getHeartBeartString(false)).getBytes(),2,false);
        mMqttOption.setUserName(MQTT_USER_NAME);
        mMqttOption.setPassword(MQTT_PASSWORD.toCharArray());
        mMqttOption.setCleanSession(true);
        mMqttOption.setConnectionTimeout(MQTT_CONNECT_TIME_OUT);
        mMqttOption.setMaxInflight(50);
        mMqttOption.setConnectionTimeout(10);
        mMqttOption.setKeepAliveInterval(30);
        mMqttClient.setCallback(MqttControl.this);
        mMqttClient.connect(mMqttOption);
        mMqttClient.subscribe(mqttTopic,new int[]{2,2});
        Log.i(TAG, "initAndConnectMqtt: ");

    }



    private void connectMqttDelay(){
        mHandler.removeMessages(1);
        mHandler.sendEmptyMessageDelayed(1,5*1000);
    }


    @Override
    public void connectionLost(Throwable throwable) {
        connectMqttDelay();
    }

    @Override
    public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
        Log.i(TAG, "messageArrived: "+s+"          "+new String(mqttMessage.getPayload()));
        if(s.equals(mqttTopic[0])){
            JSONObject jsonObject=new JSONObject(new String(mqttMessage.getPayload()));
            int type = jsonObject.getInt("type");
            if(type==0||type==1){
                try {
                    String data = jsonObject.getString("data").toString();
                    BookMessage.DataDTO orderListDTO= JSON.parseObject(data, BookMessage.DataDTO.class);

                    SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    PrintControl.newInstance().getmPrinter().printString("\n");
                    PrintControl.newInstance().getmPrinter().printString("\n");
                    PrintControl.newInstance().getmPrinter().printString("\n");
                    PrintControl.newInstance().getmPrinter().printString("            福家智能国际酒店\n");
                    PrintControl.newInstance().getmPrinter().printString("\n");
                    PrintControl.newInstance().getmPrinter().printString("\n");
                    BookMessage.DataDTO.RoomListDTO roomListDTO = orderListDTO.getRoom_list().get(0);
                    PrintControl.newInstance().getmPrinter().printString("   房间号码："+roomListDTO.getRoom_number());
                    PrintControl.newInstance().getmPrinter().printString("\n");

                    PrintControl.newInstance().getmPrinter().printString("   房   型："+DeviceHelper.queryRoomByRoomNum(roomListDTO.getRoom_type_name()));
                    PrintControl.newInstance().getmPrinter().printString("\n");
                    int price=0;
                    for(int i=0;i<roomListDTO.getPrice().size();i++){
                        price+=roomListDTO.getPrice().get(i).getPrice();

                    }

                    PrintControl.newInstance().getmPrinter().printString("   房   费："+price+"元");
                    PrintControl.newInstance().getmPrinter().printString("\n");
                    PrintControl.newInstance().getmPrinter().printString("   押   金："+roomListDTO.getCash_pledge()+"元");
                    PrintControl.newInstance().getmPrinter().printString("\n");
                    PrintControl.newInstance().getmPrinter().printString("   入住时间："+simpleDateFormat.format(new Date(orderListDTO.getStay_time()*1000)));
                    PrintControl.newInstance().getmPrinter().printString("\n");
                    PrintControl.newInstance().getmPrinter().printString("   离店时间："+simpleDateFormat.format(new Date(orderListDTO.getLeave_time_plan()*1000)));
                    PrintControl.newInstance().getmPrinter().printString("\n");
                    PrintControl.newInstance().getmPrinter().printString("   联系方式：123456789012");
                    PrintControl.newInstance().getmPrinter().printString("\n");
                    PrintControl.newInstance().getmPrinter().printString("   地   址："+"深圳市南山区智恒产业园E01b 308");

                    PrintControl.newInstance().getmPrinter().printString("\n");
                    PrintControl.newInstance().getmPrinter().printString("\n");
                    PrintControl.newInstance().getmPrinter().printString("\n"); PrintControl.newInstance().getmPrinter().printString("\n");
                    PrintControl.newInstance().getmPrinter().fullCut();
                    CardControl.newInstance().chuKa();
//
//
                    execListener(type,orderListDTO);


                }catch (Exception e){
                    e.printStackTrace();
                }


            }



        }

    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

    }
    static   List<OnMqttMessageListener> onMqttMessageListeners=new ArrayList<>();


    public static void addOnMqttMessageListener(OnMqttMessageListener onMqttMessageListener){
        onMqttMessageListeners.add(onMqttMessageListener);
    }


    public static void execListener(int type,Object data){
        for(OnMqttMessageListener onMqttMessageListener:onMqttMessageListeners){
            onMqttMessageListener.dataRec(type,data);
        }


    }


    public static void removeOnMqttMessageListener(OnMqttMessageListener onMqttMessageListener){
        onMqttMessageListeners.remove(onMqttMessageListener);
    }



    public interface OnMqttMessageListener{
        void dataRec(int type,Object data);

    }

}
