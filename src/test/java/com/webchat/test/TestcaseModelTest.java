package com.webchat.test;

import com.wechat.apiObject.ApiLoader;
import com.wechat.testcase.TestcaseModel;
import org.junit.jupiter.api.BeforeAll;
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
public class TestcaseModelTest {
    Logger logger = LoggerFactory.getLogger(TestcaseModelTest.class);
    static ArrayList<String> actParamList = new ArrayList<>();
    @BeforeAll
    static void before(){

        ApiLoader.load("src\\main\\resources\\api");
    }

    @Test
    void test() throws IOException {


        TestcaseModel testcaseModel = TestcaseModel.load("src\\main\\resources\\testcase\\creatdepartment.yaml");
        testcaseModel.run();

        logger.info("debug");


    }

}
