package com.wechat.utils;


import java.util.Random;

/**
 * @author w29530
 * @date   2021/12/15
 * @desc
 */
public class FakeUtils {

    /**
     * 获取时间戳
     */
    public static String getTimeMillis(){
       return String.valueOf(System.currentTimeMillis());
    }

    /**
     * 获取制定长度随机数字符串
     * @param length 随机数长度
     */
    public static String getRandom(int length){
        String val = "";
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            val += String.valueOf(random.nextInt(10));
        }
        return val;
    }

}
