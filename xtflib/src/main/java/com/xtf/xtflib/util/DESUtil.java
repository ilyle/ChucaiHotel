package com.xtf.xtflib.util;

import android.os.SystemClock;

import java.security.spec.KeySpec;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

/**
 * Created by Administrator on 2017/6/10 0010.
 */

public class DESUtil {

    public static final String TRANSFORMATION = "DES";

    public static final String algorithm = "DES";

    public static String decryptDES(String encrty, String key) {
        String str = null;
        try {
            byte[] input = Base64.decode(encrty);

            // 指定加密工具
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);

            KeySpec keySpec = new DESKeySpec(key.getBytes());
            SecretKey certificate = SecretKeyFactory.getInstance(algorithm).generateSecret(keySpec);
            // 初始化加密工具
            cipher.init(Cipher.DECRYPT_MODE, certificate);
            // 解析加密过的工具
            byte[] bs = cipher.doFinal(input);
            SystemClock.sleep(20);
            str = new String(bs,"utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }

    /**
     * des 加密算法
     *
     * @param data
     * @param key
     * @return
     */
    public static String encryptDES(String data, String key) {
        String encrty = null;

        try {
            // 创建Cipher 用户加密类 指定为des加密方式 对称加密
            Cipher ciphle = Cipher.getInstance(TRANSFORMATION);
            // 设置密钥规范对象
            KeySpec keySpec = new DESKeySpec(key.getBytes());
            // 创建密钥创建工场
            SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance(algorithm);
            // 密钥工场根据你的密钥规范对象创建一个 密钥
            SecretKey encrtykey = secretKeyFactory.generateSecret(keySpec);
            // 初始化ciphle对象 指定需要操作的方式 加密还是解密 指定密钥
            ciphle.init(Cipher.ENCRYPT_MODE, encrtykey);

            // desc加密算法 ？加密key字段呢
            byte[] bs = ciphle.doFinal(data.getBytes());
            encrty = Base64.encode(bs);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return encrty;

    }

//    /**
//     * 按指定8位KEY加密数据 3DES_CBC_EN
//     *
//     * @param sData
//     *            输入数据
//     * @return 加密后的数据
//     */
//    public static String enDES3(String sData) {
//        try {
//            String sKey = "Hf2101gZ";
//            byte[] nKey = (sKey + sKey + sKey).getBytes();
//            byte[] nKeyVS = sKey.getBytes();
//            byte[] nDataArr = sData.getBytes();
//            IvParameterSpec ivPara = new IvParameterSpec(nKeyVS);
//            SecretKey deskey = new SecretKeySpec(nKey, "DESede"); // 生成密钥21
//            Cipher cipher = Cipher.getInstance("DESede/CBC/PKCS5Padding"); // 实例化负责加密/解密的Cipher工具类22
//            cipher.init(Cipher.ENCRYPT_MODE, deskey, ivPara); // 初始化为加密模式23
//            String sRet = byte2Hex(cipher.doFinal(nDataArr));
//            return sRet;
//        } catch (java.security.NoSuchAlgorithmException e1) {
//            e1.printStackTrace();
//        } catch (javax.crypto.NoSuchPaddingException e2) {
//            e2.printStackTrace();
//        } catch (java.lang.Exception e3) {
//            e3.printStackTrace();
//        }
//        return "";
//    }



}
