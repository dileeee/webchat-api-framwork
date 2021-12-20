package com.webchat.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.wechat.apiObject.ApiLoader;
import com.wechat.testcase.TestcaseModelCSV;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.params.provider.Arguments.arguments;

/**
 * @author w29530
 * @date 2021/12/15
 * @desc ApiActionModel单元测试
 */
public class TestcaseModelCSVTest {
    Logger logger = LoggerFactory.getLogger(TestcaseModelCSVTest.class);
    static ArrayList<String> actParamList = new ArrayList<>();
    @BeforeAll
    static void before(){

        ApiLoader.load("src\\main\\resources\\api");
    }

    @Test
    void testCSV() throws Exception {

        TestcaseModelCSV testcaseModelCSV = TestcaseModelCSV.load("src/main/resources/testcase/creatdepartment_csv.yaml");
        testcaseModelCSV.run();
        logger.info("debug");
    }

    @ParameterizedTest
    @MethodSource
    void apiTest(TestcaseModelCSV testcaseModel, String name, String description) throws Exception {
        logger.info("【用例开始执行】");
        logger.info("用例名称： " + name);
        logger.info("用例描述： " + description);
        testcaseModel.run();
        logger.info("debug");
    }


    public static ArrayList<Arguments> apiTest(){
        ApiLoader.load("src/main/resources/api");
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        ArrayList<Arguments> testcaseModels = new ArrayList<>();
        String dirPath = "src/main/resources/testcase/creatdepartment_csv.yaml";
        TestcaseModelCSV testcaseModel = null;
        try {
            testcaseModel = TestcaseModelCSV.load(dirPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        testcaseModels.add(arguments(testcaseModel,testcaseModel.getName(),testcaseModel.getDescription()));
        return testcaseModels;
    }

}
