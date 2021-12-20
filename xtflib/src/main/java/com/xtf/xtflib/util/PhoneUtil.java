package com.xtf.xtflib.util;

public class PhoneUtil {
    //输入手机号码检查是否有误
    public static boolean checkMobile(String mobile) {
        if(mobile.equals(null)){
				/*^匹配开始地方$匹配结束地方，[3|4|5|7|8]选择其中一个{4,8},\d从[0-9]选择
				{4,8}匹配次数4~8	，java中/表示转义，所以在正则表达式中//匹配/,/匹配""*/
            //验证手机号码格式是否正确
        }else if(!mobile.matches("^1[3|4|5|7|8][0-9]\\d{4,8}$")){
            System.out.println("手机号输入有误，请重新输入");
            return false;
        }else{
            System.out.println("恭喜您中奖了");
            return true;
        }
        return false;
    }
}
