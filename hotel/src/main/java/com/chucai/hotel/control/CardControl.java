package com.chucai.hotel.control;

import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;

import com.act.F1_api.F1CardReader;
import com.act.F1_api.R1Exception;

public class CardControl {
    private static final String TAG = "CardControl";
    F1CardReader f1CardReader;

  static   CardControl sCardControl;


    public static CardControl newInstance() {
        if (sCardControl==null){
            sCardControl=new CardControl();

        }


        return sCardControl;
    }

    public void init(){
        f1CardReader=new F1CardReader();
        try {
            f1CardReader.connect("/dev/ttyS1",9600);
            SystemClock.sleep(3000);
            f1CardReader.reset();
//            SystemClock.sleep(3000);
//            chuKa();
//            f1CardReader.F1_SetEntryMode()
        } catch (Exception e) {
            e.printStackTrace();
            Log.i(TAG, "init: 0000000");
        }

    }


    public void huiShouCard(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    f1CardReader.F1_SetEntryMode((byte) 0x32);
                    f1CardReader.F1_Capture();
                } catch (R1Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }



    public void setNoCardMode(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    f1CardReader.F1_SetEntryMode((byte) 0x30);

                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        }).start();

    }





    public void chuKa(){

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    f1CardReader.F1_Dispense((byte)0x34);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();



    }







}
