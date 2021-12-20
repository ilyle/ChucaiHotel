package com.xtf.xtflib.util;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;


import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.util.List;

public class FileUtil {
    private static final String TAG = "FileUtil";

    public static boolean checkFileAllDownload(List<String> downloadUrlList){
        for(String url:downloadUrlList){
            Log.i(TAG, "checkFileAllDownload: "+url);
            File file=new File(url);
            if(!file.exists()){
                return false;
            }

        }
        Log.i(TAG, "checkFileAllDownload: ");
        return true;
    }


    public static void copyFromAssets(AssetManager assets, String source, String dest, boolean isCover)
            throws IOException {
        Log.i(TAG, "copyFromAssets: "+dest);
//        File  orignFile=new File("file:///android_asset/"+source);
//        Log.i(TAG, "copyFromAssets: "+orignFile.getAbsolutePath()+"    "+orignFile.length());





        File file = new File(dest);
        file.getParentFile().mkdirs();
        if(file.exists()) {
            String md5Three = getMD5Three(assets.open(source));
            String endMd5Tree = getMD5Three(new FileInputStream(file));
            Log.i(TAG, "copyFromAssets: " + md5Three + "   " + endMd5Tree+"   "+source);
            if (!TextUtils.isEmpty(endMd5Tree) && !TextUtils.isEmpty(md5Three)) {
                if (!md5Three.equals(endMd5Tree)) {
                    file.delete();
                    isCover = true;
                }
            }else {
                file.delete();
                isCover = true;
            }
        }
        if (isCover || (!isCover && !file.exists())) {
            InputStream is = null;
            FileOutputStream fos = null;
            try {
                is = assets.open(source);
                String path = dest;
                fos = new FileOutputStream(path);
                byte[] buffer = new byte[1024];
                int size = 0;
                while ((size = is.read(buffer, 0, 1024)) >= 0) {
                    fos.write(buffer, 0, size);
                }
            } finally {
                if (fos != null) {
                    try {
                        fos.close();
                    } finally {
                        if (is != null) {
                            is.close();
                        }
                    }
                }
            }
        }
    }

    public static String getMD5Three(InputStream inputStream) {
        BigInteger bi = null;
        try {
            byte[] buffer = new byte[8192];
            int len = 0;
            MessageDigest md = MessageDigest.getInstance("MD5");
            BufferedInputStream fis = new BufferedInputStream(inputStream);
            while ((len = fis.read(buffer)) != -1) {
                md.update(buffer, 0, len);
            }
            fis.close();
            byte[] b = md.digest();
            bi = new BigInteger(1, b);


            if(bi!=null) {
                return bi.toString(16);
            }else {

                return null;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static boolean isConnect(String url) {
        boolean flag = false;
        int counts = 0;
        if (url == null || url.length() <= 0) {
            return flag;
        }
        while (counts < 2) {
            try {
                HttpURLConnection connection = (HttpURLConnection) new URL(url)
                        .openConnection();
                int state = connection.getResponseCode();
                if (state == 200) {
                    // String realurl = connection.getURL().toString();
                    flag = true;
                }
                break;
            } catch (Exception ex) {
                counts++;
                continue;
            }
        }
        return flag;
    }



    /**
     * @param assetpath  asset下的路径
     * @param SDpath     SDpath下保存路径
     */
    public static void AssetToSD(Context context, String assetpath, String SDpath ){

        AssetManager asset=context.getAssets();
        //循环的读取asset下的文件，并且写入到SD卡
        String[] filenames=null;
        FileOutputStream out = null;
        InputStream in=null;
        try {
            filenames = asset.list(assetpath);
            if(filenames.length>0){//说明是目录
                //创建目录
                getDirectory(assetpath);

                for(String fileName:filenames){
                    AssetToSD(context,assetpath+"/"+fileName, SDpath+"/"+fileName);
                }
            }else{//说明是文件，直接复制到SD卡
                File SDFlie=new File(SDpath);
                String  path=assetpath.substring(0, assetpath.lastIndexOf("/"));
                getDirectory(path);

                if(!SDFlie.exists()){
                    SDFlie.createNewFile();
                }
                //将内容写入到文件中
                in=asset.open(assetpath);
                out= new FileOutputStream(SDFlie);
                byte[] buffer = new byte[1024];
                int byteCount=0;
                while((byteCount=in.read(buffer))!=-1){
                    out.write(buffer, 0, byteCount);
                }
                out.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            try {
                if(out!=null) {
                    out.close();
                }
                if(in!=null) {
                    in.close();
                }
                if(asset!=null) {
                    asset.close();
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
    }
    //分级建立文件夹
    public static void getDirectory(String path){
        //对SDpath进行处理，分层级建立文件夹
        String[]  s=path.split("/");
        String str= Environment.getExternalStorageDirectory().toString();
        for (int i = 0; i < s.length; i++) {
            str=str+"/"+s[i];
            File file=new File(str);
            if(!file.exists()){
                file.mkdir();
            }
        }

    }


    public static void copyAssertToSD(Context context,String path,String filename){
//获取资产管理者
        AssetManager am = context.getAssets();
        try {
//打开资产目录下的文件
            InputStream inputStream = am.open(filename);
            File file=new File(path,filename);
            FileOutputStream fos=new FileOutputStream(file);
            int len=0;
            byte[] buffer=new byte[1024];
            while((len=inputStream.read(buffer))!=-1){
                fos.write(buffer,0,len);
            }
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public static String readTxtData(String file,String charset) {
        if(new File(file).exists()){
            InputStreamReader inputStreamReader = null;
            try {
                inputStreamReader = new InputStreamReader(new FileInputStream(file), charset);
                BufferedReader reader = new BufferedReader(inputStreamReader);
                StringBuffer  content=new StringBuffer();
                String line;
                while ((line = reader.readLine()) != null) {
                    content.append(line);
                }
                reader.close();
                inputStreamReader.close();
                return content.toString();
            } catch (Exception e) {

            }

        }
        return "";
    }

    public static String readTxtData(InputStream file) {
        InputStreamReader inputStreamReader = null;
        try {
            inputStreamReader = new InputStreamReader(file);
            BufferedReader reader = new BufferedReader(inputStreamReader);
            StringBuffer content=new StringBuffer();
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line+"");
            }
            reader.close();
            inputStreamReader.close();
            file.close();
            return content.toString();
        } catch (Exception e) {

        }finally {
            try {
                inputStreamReader.close();
                file.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return "";
    }
    /**
     22      * 复制单个文件
     23      * @param oldPath 要复制的文件名
     24      * @param newPath 目标文件名
     25      */
    public static void copyfile(String oldPath, String newPath) {
        int hasRead = 0;
        File oldFile = new File(oldPath);
        if (oldFile.exists()) {
            try {
                FileInputStream fis = new FileInputStream(oldFile);//读入原文件
                FileOutputStream fos = new FileOutputStream(newPath);
                byte[] buffer = new byte[1024];
                while ((hasRead = fis.read(buffer)) != -1) {//当文件没有读到结尾
                    fos.write(buffer, 0, hasRead);//写文件
                }
                fis.close();
            } catch (Exception e) {
                System.out.println("复制单个文件操作出错！");
                e.printStackTrace();
            }
        }
    }

    /**
     *
     * @param oldPath 要复制的文件夹路径
     @param newPath 目标文件夹路径
     */
    public static void copyDirectory(String oldPath, String newPath) {
        File f1 = new File(oldPath);
        File[] files = f1.listFiles();//listFiles能够获取当前文件夹下的所有文件和文件夹
        for (int i = 0; i < files.length; i++) {
            if (files[i].isDirectory()) {
                File dirNew = new File(newPath + File.separator + files[i].getName());
                dirNew.mkdir();//在目标文件夹中创建文件夹
                //递归
                copyDirectory(oldPath + File.separator + files[i].getName(), newPath + File.separator + files[i].getName());
            } else {
                String filePath = newPath + File.separator + files[i].getName();
                copyfile(files[i].getAbsolutePath(), filePath);
            }

        }
    }

    /**
     * 获取文件夹大小
     * @param file File实例
     * @return long
     */
    public static long getFolderSize(File file){

        long size = 0;
        try {
            File[] fileList = file.listFiles();
            if(fileList!=null) {
                for (int i = 0; i < fileList.length; i++) {
                    if (fileList[i].isDirectory()) {
                        size = size + getFolderSize(fileList[i]);
                    } else {
                        size = size + fileList[i].length();
                        Log.i(TAG, "getFolderSize: "+fileList[i].getName());

                    }
                }
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //return size/1048576;
        return size;
    }
}
