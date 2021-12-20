package com.wechat.global;

import java.util.HashMap;

/**
 * @author w29530
 * @date   2021/12/15
 * @desc
 */
public class GlobalVariables {

    static HashMap<String,String> globalVariables = new HashMap<>();

    public static HashMap<String, String> getGlobalVariables() {
        return globalVariables;
    }

    public static void setGlobalVariables(HashMap<String, String> globalVariables) {
        GlobalVariables.globalVariables = globalVariables;
    }
}
