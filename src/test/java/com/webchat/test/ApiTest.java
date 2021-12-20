package com.webchat.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.wechat.apiObject.ApiLoader;
import com.wechat.testcase.TestcaseModel;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.params.provider.Arguments.arguments;


/**
 * @author w29530
 * @date 2021/12/15
 * @desc
 */
public class ApiTest {
    Logger logger = LoggerFactory.getLogger(ApiTest.class);

    @ParameterizedTest
    @MethodSource
    void apiTest(TestcaseModel testcaseModel, String name, String description){
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
        String dirPath = "src/main/resources/testcase";
        Arrays.stream(new File(dirPath).list()).forEach(file ->{
            try {
                TestcaseModel testcaseModel = TestcaseModel.load(dirPath+"/"+file);
                testcaseModels.add(arguments(testcaseModel,testcaseModel.getName(),testcaseModel.getDescription()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        return testcaseModels;
    }
}
