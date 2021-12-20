package com.xtf.xtflib.util;

/**
 * Created by Administrator on 2018-01-30 030.
 */

import android.content.Context;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class UnzipAssets {

    private ZipInputStream zipIn;      //解压Zip
    private ZipOutputStream zipOut;     //压缩Zip
    private ZipEntry zipEntry;
    private static int bufSize;    //size of bytes
    private byte[] buf;
    private int readedBytes;



    public UnzipAssets() {
        this(512);
    }

    public UnzipAssets(int bufSize) {
        UnzipAssets.bufSize = bufSize;
        this.buf = new byte[UnzipAssets.bufSize];
    }

    /**
     * 解压assets的zip压缩文件到指定目录
     * @throws IOException
     */
    public static void unZip(Context context, String assetName,
                             String outputDirectory,boolean isReWrite) throws IOException {
        //创建解压目标目录
        File file = new File(outputDirectory);
        //如果目标目录不存在，则创建
        if (!file.exists()) {
            file.mkdirs();
        }
        //打开压缩文件
        InputStream inputStream = context.getAssets().open(assetName);
        ZipInputStream zipInputStream = new ZipInputStream(inputStream);
        //读取一个进入点
        ZipEntry zipEntry = zipInputStream.getNextEntry();
        //使用1Mbuffer
        byte[] buffer = new byte[1024 * 1024];
        //解压时字节计数
        int count = 0;
        //如果进入点为空说明已经遍历完所有压缩包中文件和目录
        while (zipEntry != null) {
            //如果是一个目录
            if (zipEntry.isDirectory()) {
                file = new File(outputDirectory + File.separator + zipEntry.getName());
                //文件需要覆盖或者是文件不存在
                if(isReWrite || !file.exists()){
                    file.mkdirs();
                }
            } else {
                //如果是文件
                file = new File(outputDirectory + File.separator
                        + zipEntry.getName());
                //文件需要覆盖或者文件不存在，则解压文件
                if(isReWrite || !file.exists()){
                    file.createNewFile();
                    FileOutputStream fileOutputStream = new FileOutputStream(file);
                    while ((count = zipInputStream.read(buffer)) > 0) {
                        fileOutputStream.write(buffer, 0, count);
                    }
                    fileOutputStream.close();
                }
            }
            //定位到下一个文件入口
            zipEntry = zipInputStream.getNextEntry();
        }
        zipInputStream.close();
    }



    /**
     * 解压assets的zip压缩文件到指定目录
     * @throws IOException
     */
    public static void unZipDir(Context context, String fileName,
                             String outputDirectory,boolean isReWrite) throws IOException {
        //创建解压目标目录
        File file = new File(outputDirectory);
        //如果目标目录不存在，则创建
        if (!file.exists()) {
            file.mkdirs();
        }
        //打开压缩文件
        InputStream inputStream = new FileInputStream(fileName);
        ZipInputStream zipInputStream = new ZipInputStream(inputStream);
        //读取一个进入点
        ZipEntry zipEntry = zipInputStream.getNextEntry();
        //使用1Mbuffer
        byte[] buffer = new byte[1024 * 1024];
        //解压时字节计数
        int count = 0;
        //如果进入点为空说明已经遍历完所有压缩包中文件和目录
        while (zipEntry != null) {
            //如果是一个目录
            if (zipEntry.isDirectory()) {
                file = new File(outputDirectory + File.separator + zipEntry.getName());
                //文件需要覆盖或者是文件不存在
                if(isReWrite || !file.exists()){
                    file.mkdirs();
                }
            } else {
                //如果是文件
                file = new File(outputDirectory + File.separator
                        + zipEntry.getName());
                //文件需要覆盖或者文件不存在，则解压文件
                if(isReWrite || !file.exists()){
                    file.createNewFile();
                    FileOutputStream fileOutputStream = new FileOutputStream(file);
                    while ((count = zipInputStream.read(buffer)) > 0) {
                        fileOutputStream.write(buffer, 0, count);
                    }
                    fileOutputStream.close();
                }
            }
            //定位到下一个文件入口
            zipEntry = zipInputStream.getNextEntry();
        }
        zipInputStream.close();
    }


    //解压指定zip文件
    public  void unZip(String unZipfileName,String path) {//unZipfileName需要解压的zip文件名

        FileOutputStream fileOut;
        File file;
        File target=new File(path);
        if(!target.exists()){
            target.mkdirs();
        }
//        FileUtil.deleteAllFiles(target);
        try {
            this.zipIn = new ZipInputStream(new
                    BufferedInputStream(new FileInputStream(unZipfileName)));
            while ((this.zipEntry = this.zipIn.getNextEntry()) != null) {
                file = new File(target.getAbsolutePath()+"/"+this.zipEntry.getName());
                if (this.zipEntry.isDirectory()) {
                    file.mkdirs();
                } else {
//                    //如果指定文件的目录不存在,则创建之.
//                    File parent = file.getParentFile();
//                    if (!parent.exists()) {
//                        parent.mkdirs();
//                    }
//                    if (!ff.exists()) {
//                        ff.mkdir();
//                    }
//                    if(!file.exists()){
//                        file.createNewFile();
//                    }
                    if(!file.getParentFile().exists()){
                        file.getParentFile().mkdirs();
                    }
                    file.createNewFile();
                    fileOut = new FileOutputStream(file); //此方法存放到该项目目录下
                    while ((this.readedBytes = this.zipIn.read(this.buf)) > 0) {
                        fileOut.write(this.buf, 0, this.readedBytes);
                    }
                    fileOut.close();
                }

                this.zipIn.closeEntry();
            }
        } catch (Exception ioe) {
            ioe.printStackTrace();
        }
    }
}