package com.xtf.xtflib.util;


import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

public class WxSignUntils {

    /**
     * 获取利用反射获取类里面的值和名称
     *
     * @param obj
     * @return
     * @throws IllegalAccessException
     */
    public static Map<String, Object> objectToMap(Object obj) {
        SortedMap<String,Object> sortedMap = new TreeMap<String,Object>();
            Class<?> clazz = obj.getClass();
        try {
            System.out.println(clazz);
            for (Field field : clazz.getDeclaredFields()) {
                field.setAccessible(true);
                String fieldName = field.getName();
                Object value = field.get(obj);
                sortedMap.put(fieldName, value);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return sortedMap;
    }




    /**
     * 生成签名
     */
    public static String createSign(Object obj, String key) {
        Map<String, Object> packageParams = objectToMap(obj);
        StringBuffer sb = new StringBuffer();
        Set es = packageParams.entrySet();// 字典序
        Iterator it = es.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String k = (String) entry.getKey();
            String v = null;
            if (entry.getValue() != null && entry.getValue().getClass() == String.class) {
                v = (String) entry.getValue();
            } else if (entry.getValue() != null && entry.getValue().getClass() == Double.class) {
                Double du = (Double) entry.getValue();
                v = String.valueOf(du);
            } else if (entry.getValue() != null && entry.getValue().getClass() == Integer.class) {
                v = String.valueOf((Integer) entry.getValue());
            } else if (entry.getValue() != null && entry.getValue().getClass() == Long.class) {
                v = String.valueOf(entry.getValue());
            } else if (entry.getValue() != null && entry.getValue().getClass() == List.class) {
                v = String.valueOf(entry.getValue());
            }
            // 为空不参与签名、参数名区分大小写
            if (null != v && !"".equals(v) && !"sign".equals(k) && !"key".equals(k)) {

                sb.append(k + "=" + v + "&");
            }
        }

        sb.append("key=" + key);


        String sign = Md5Utils.md5Java(sb.toString()).toUpperCase();// MD5加密

        return sign;
    }


}
