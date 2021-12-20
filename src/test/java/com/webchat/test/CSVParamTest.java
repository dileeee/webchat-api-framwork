package com.webchat.test;

import com.wechat.utils.CSVParam;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author w29530
 * @date 2021/12/15
 * @desc csv文件读取测试
 */
public class CSVParamTest {
    Logger logger = LoggerFactory.getLogger(CSVParamTest.class);

    @Test
    void csvTest() throws Exception {
        String[][] csvFileData = CSVParam.readCSVFileData("src\\main\\resources\\data\\create_department.csv");
        logger.info("debug");
    }


}
