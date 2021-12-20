package com.xtf.xtflib.util;

import android.content.Context;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * 该类实现文件夹压缩成zip文件和zip文件解压
 *
 * @author Administrator
 */
public class ZipUtil {
    private ZipInputStream zipIn;      //解压Zip
    private ZipOutputStream zipOut;     //压缩Zip
    private ZipEntry zipEntry;
    private static int bufSize;    //size of bytes
    private byte[] buf;
    private int readedBytes;

    public ZipUtil() {
        this(512);
    }

    public ZipUtil(int bufSize) {
        ZipUtil.bufSize = bufSize;
        this.buf = new byte[ZipUtil.bufSize];
    }

    //压缩文件夹内的文件
    public void doZip(String zipDirectory) {//zipDirectoryPath:需要压缩的文件夹名
        File file;
        File zipDir;

        zipDir = new File(zipDirectory);
        String zipFileName = zipDirectory + ".zip";//压缩后生成的zip文件名
        try {
            this.zipOut = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(zipFileName)));
            handleDir(zipDir, this.zipOut);
            this.zipOut.close();
        } catch (Exception ioe) {
            ioe.printStackTrace();
        }
    }

    //由doZip调用,递归完成目录文件读取
    private void handleDir(File dir, ZipOutputStream zipOut) throws Exception {
        FileInputStream fileIn;
        File[] files;
        File parentFile = dir.getParentFile();
        files = dir.listFiles();
        if (files.length == 0) {//如果目录为空,则单独创建之.
            //ZipEntry的isDirectory()方法中,目录以"/"结尾.
            this.zipOut.putNextEntry(new ZipEntry(dir.toString() + "/"));
            this.zipOut.closeEntry();
        } else {//如果目录不为空,则分别处理目录和文件.
            for (File fileName : files) {

                if (fileName.isDirectory()) {
                    handleDir(fileName, this.zipOut);
                } else {
                    fileIn = new FileInputStream(fileName);
                    //生成的压缩包存放在原目录下
                    this.zipOut.putNextEntry(new ZipEntry(fileName.getAbsolutePath().substring(parentFile.getAbsolutePath().length()+1,fileName.getAbsolutePath().length())));
                    //此方法存放在该项目目录下
                    //this.zipOut.putNextEntry(new ZipEntry(fileName.toString()));
                    while ((this.readedBytes = fileIn.read(this.buf)) > 0) {
                        this.zipOut.write(this.buf, 0, this.readedBytes);
                    }
                    this.zipOut.closeEntry();
                }
            }
        }
    }

    //解压指定zip文件
    public void unZip(String unZipfileName,String path) {//unZipfileName需要解压的zip文件名

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
                    if(file.exists()){
                        file.delete();
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

    //设置缓冲区大小
    public void setBufSize(int bufSize) {
        ZipUtil.bufSize = bufSize;
    }
//
//    //测试Zip类
//    public static void main(String[] args)throws Exception{
//        Zip zip=new Zip();
//        zip.doZip("d:\\1234");
//        zip.unZip("d:\\1234.zip");
//    }


    public  void unZip(Context context, String assetName, String outputDirectory) throws IOException {
        //创建解压目标目录
        File file = new File(outputDirectory);
        //如果目标目录不存在，则创建
        if (!file.exists()) {
            file.mkdirs();
        }
        InputStream inputStream = null;
        //打开压缩文件
        inputStream = context.getAssets().open(assetName);
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
                //String name = zipEntry.getName();
                //name = name.substring(0, name.length() - 1);
                file = new File(outputDirectory + File.separator + zipEntry.getName());
                file.mkdir();
            } else {
                //如果是文件
                file = new File(outputDirectory + File.separator
                        + zipEntry.getName());
                //创建该文件
                file.createNewFile();
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                while ((count = zipInputStream.read(buffer)) > 0) {
                    fileOutputStream.write(buffer, 0, count);
                }
                fileOutputStream.close();
            }
            //定位到下一个文件入口
            zipEntry = zipInputStream.getNextEntry();
        }
        zipInputStream.close();
    }










    /**
     * 压缩文件和文件夹
     *
     * @param srcFileString 要压缩的文件或文件夹
     * @param zipFileString 压缩完成的Zip路径
     * @throws Exception
     */
    public static void ZipFolder(String srcFileString, String zipFileString) throws Exception {
        //创建ZIP
        ZipOutputStream outZip = new ZipOutputStream(new FileOutputStream(zipFileString));
        //创建文件
        File file = new File(srcFileString);
        //压缩
        ZipFiles(file.getParent()+ File.separator, file.getName(), outZip);
        //完成和关闭
        outZip.finish();
        outZip.close();
    }

    /**
     * 压缩文件
     *
     * @param folderString
     * @param fileString
     * @param zipOutputSteam
     * @throws Exception
     */
    private static void ZipFiles(String folderString, String fileString, ZipOutputStream zipOutputSteam) throws Exception {
        if (zipOutputSteam == null)
            return;
        File file = new File(folderString + fileString);
        if(file.exists()) {
            if (file.isFile()) {
                ZipEntry zipEntry = new ZipEntry(fileString);
                FileInputStream inputStream = new FileInputStream(file);
                zipOutputSteam.putNextEntry(zipEntry);
                int len;
                byte[] buffer = new byte[4096];
                while ((len = inputStream.read(buffer)) != -1) {
                    zipOutputSteam.write(buffer, 0, len);
                }
                zipOutputSteam.closeEntry();
            } else {
                //文件夹
                String fileList[] = file.list();
                //没有子文件和压缩
                if (fileList.length <= 0) {
                    ZipEntry zipEntry = new ZipEntry(fileString + File.separator);
                    zipOutputSteam.putNextEntry(zipEntry);
                    zipOutputSteam.closeEntry();
                }
                //子文件和递归
                for (int i = 0; i < fileList.length; i++) {
                    ZipFiles(folderString + fileString + "/", fileList[i], zipOutputSteam);
                }
            }
        }
    }
}





