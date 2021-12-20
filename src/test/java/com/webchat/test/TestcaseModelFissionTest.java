package com.webchat.test;


import com.wechat.apiObject.ApiLoader;
import com.wechat.testcase.TestcaseModelFission;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

/**
 * @author w29530
 * @date 2021/12/15
 * @desc ApiActionModel单元测试
 */
public class TestcaseModelFissionTest {
    Logger logger = LoggerFactory.getLogger(TestcaseModelFissionTest.class);
    static ArrayList<String> actParamList = new ArrayList<>();
    @BeforeAll
    static void before(){

        ApiLoader.load("src\\main\\resources\\api");
    }

    @Test
    void testFission() throws Exception {

        TestcaseModelFission testcaseModelCSV = TestcaseModelFission.load("src/main/resources/testcase_fission/creatdepartment_fission.yaml");
        testcaseModelCSV.run();
        logger.info("debug");
    }



}
