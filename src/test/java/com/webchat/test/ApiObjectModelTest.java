package com.webchat.test;

import com.wechat.apiObject.ApiObjectModel;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;

/**
 * @author w29530
 * @date 2021/12/15
 * @desc ApiActionModel单元测试
 */
public class ApiObjectModelTest {
    Logger logger = LoggerFactory.getLogger(ApiObjectModelTest.class);

    @Test
    void test() throws IOException {


        ArrayList<String> actParamList = new ArrayList<>();
        // 企业ID ： wwbcc92e0afe51b09e  secret ： MmNdXbFeCNiPJEztv1Kd1TqW6e3Gy6BBgPVRWDJa9fI  agentId ： 3010084
        actParamList.add("wwbcc92e0afe51b09e");
        actParamList.add("MmNdXbFeCNiPJEztv1Kd1TqW6e3Gy6BBgPVRWDJa9fI");

        ApiObjectModel apiObjectModel = ApiObjectModel.load("src\\main\\resources\\api\\tokenhelper.yaml");
        logger.info("debug");

    }

}
