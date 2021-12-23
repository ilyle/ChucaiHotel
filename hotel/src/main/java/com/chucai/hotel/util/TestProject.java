package com.chucai.hotel.util;

import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

public class TestProject {

    public static String readTxtData(String file,String charset) {

        return "";
    }


    public static void execRuntimeCmd(String cmd)  {
        try {

            Process su = Runtime.getRuntime().exec("su");
            OutputStream outputStream = su.getOutputStream();
            outputStream.write(cmd.getBytes());
            outputStream.flush();
            outputStream.close();
            su.destroy();

        }catch (Exception e){
            e.printStackTrace();
        }

    }


    public void doTask(String fileName){
        try {
        if(new File(fileName).exists()){
            InputStreamReader inputStreamReader = null;
            try {
                inputStreamReader = new InputStreamReader(new FileInputStream(fileName), "utf-8");
                BufferedReader reader = new BufferedReader(inputStreamReader);
//                StringBuffer  content=new StringBuffer();
                String line;
                while ((line = reader.readLine()) != null) {
//                    content.append(line);
                    Log.i("xtf", "doTask: "+line);
                    if (!TextUtils.isEmpty(line)) {
                        if(!line.startsWith("#")) {
                            execRuntimeCmd(line);
                        }
                    }



                }
                reader.close();
                inputStreamReader.close();

            } catch (Exception e) {
            }
        }
        }catch (Exception e){

        }


    }
}
