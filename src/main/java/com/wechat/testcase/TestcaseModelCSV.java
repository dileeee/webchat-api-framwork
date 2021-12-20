package com.wechat.testcase;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.wechat.step.StepResultModel;
import com.wechat.step.TestcaseStepModelCSV;
import com.wechat.utils.CSVParam;
import com.wechat.utils.FakeUtils;
import lombok.Data;
import org.junit.jupiter.api.function.Executable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertAll;

/**
 * @author w29530
 * @date 2021/12/15
 * @desc 定义ApiTestCaseModel类，用来用来存储testcase yaml反序列化出来的TestCase实体对象
 */
@Data
public class TestcaseModelCSV {

    Logger logger = LoggerFactory.getLogger(TestcaseModelCSV.class);

    private String name;
    private String description;
    private ArrayList<TestcaseStepModelCSV> steps;
    private String csvParameter;

    // 用例变量
    private HashMap<String, String> testcaseVariables = new HashMap<>();
    // 软断言集合
    private ArrayList<Executable> assertAllList = new ArrayList<>();
    private String[][] csvFileData;

    /**
     * 对yaml文件进行反序列化
     *
     * @param path yaml文件路径
     * @return 反序列化后的TestcaseModel对象
     * @throws IOException
     */
    public static TestcaseModelCSV load(String path) throws IOException {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        return mapper.readValue(new File(path), TestcaseModelCSV.class);
    }

    public void run() throws Exception {
        // 1、更新用例变量  时间戳
        testcaseVariables.put("getTimeStamp", FakeUtils.getTimeMillis());
        logger.info("更新用例变量 新增: getTimeStamp");
        // 处理CSV文件数据
        if (csvParameter != null) {
            // 获取到csv中文件数据
            csvFileData = CSVParam.readCSVFileData(csvParameter);
        }

        // 2、遍历step 执行run方法
        steps.forEach(step -> {
            ArrayList<String> actualParameter = step.getActualParameter();
            // 判断actualParameter中是否包含csv 若包含则代表需要用到csv中的数据
            if (actualParameter != null && actualParameter.size() > 0 && actualParameter.toString().contains("csv")) {
                // 遍历csv数据 并将数据添加到用例变量中
                // 数据组数决定了需要运行的次数
                Arrays.stream(csvFileData).forEach(data -> {
                    for (int i = 0; i < data.length; i++) {
                        // 将数据以 ${csv0} : data 的形式存入
                        testcaseVariables.put("csv" + i, data[i]);
                    }
                    StepResultModel stepResultModel = step.run(testcaseVariables);
                    // 3、处理结果变量
                    if (stepResultModel.getStepVariables() != null && stepResultModel.getStepVariables().size() > 0) {
                        testcaseVariables.putAll(stepResultModel.getStepVariables());
                        logger.info("更新用例变量 新增：" + stepResultModel.getStepVariables());
                    }
                    // 处理断言集合
                    if (stepResultModel.getAssertList() != null && stepResultModel.getAssertList().size() > 0)
                        assertAllList.addAll(stepResultModel.getAssertList());
                });
            } else {
                StepResultModel stepResultModel = step.run(testcaseVariables);

                // 3、处理结果变量
                if (stepResultModel.getStepVariables() != null && stepResultModel.getStepVariables().size() > 0) {
                    testcaseVariables.putAll(stepResultModel.getStepVariables());
                    logger.info("更新用例变量 新增：" + stepResultModel.getStepVariables());
                }

                // 处理断言集合
                if (stepResultModel.getAssertList() != null && stepResultModel.getAssertList().size() > 0)
                    assertAllList.addAll(stepResultModel.getAssertList());
            }
        });
        // 执行全局断言
        assertAll("全局断言", assertAllList.stream());
    }

}
