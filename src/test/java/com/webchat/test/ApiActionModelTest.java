package com.webchat.test;

import com.wechat.action.ApiActionModel;
import com.wechat.global.GlobalVariables;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author w29530
 * @date 2021/12/15
 * @desc ApiActionModel单元测试
 */
public class ApiActionModelTest {
    Logger logger = LoggerFactory.getLogger(ApiActionModelTest.class);

    @Test
    void test(){
        ApiActionModel apiActionModel = new ApiActionModel();
        apiActionModel.setGet("https://qyapi.weixin.qq.com/cgi-bin/${url}");

        HashMap<String,String> globalVariables = new HashMap<>();
        globalVariables.put("url","gettoken");
        GlobalVariables.setGlobalVariables(globalVariables);

        HashMap<String,String> query = new HashMap<>();
        query.put("corpid","${corpid}");
        query.put("corpsecret","${corpsecret}");
        apiActionModel.setQuery(query);

        ArrayList<String> formalParam = new ArrayList<>();
        formalParam.add("corpid");
        formalParam.add("corpsecret");
        apiActionModel.setFormalParam(formalParam);

        ArrayList<String> actParamList = new ArrayList<>();
        // ["wwe3dd0dc62bcace3d","786Z6NCAL94KMzsNZWN_XkyPRc7yJE32xOJLgT--QKc"]
        actParamList.add("wwe3dd0dc62bcace3d");
        actParamList.add("786Z6NCAL94KMzsNZWN_XkyPRc7yJE32xOJLgT--QKc");

        apiActionModel.run(actParamList);
        logger.info("debug");
    }

}
