package com.wechat.testcase;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.wechat.step.StepResultModel;
import com.wechat.step.TestcaseStepModel;
import com.wechat.utils.FakeUtils;
import lombok.Data;
import org.junit.jupiter.api.function.Executable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertAll;

/**
 * @author w29530
 * @date 2021/12/15
 * @desc 定义ApiTestCaseModel类，用来用来存储testcase yaml反序列化出来的TestCase实体对象
 */
@Data
public class TestcaseModel {

    Logger logger = LoggerFactory.getLogger(TestcaseModel.class);

    private String name;
    private String description;
    private ArrayList<TestcaseStepModel> steps;

    // 用例变量
    private HashMap<String,String> testcaseVariables = new HashMap<>();
    // 软断言集合
    private ArrayList<Executable> assertAllList = new ArrayList<>();

    /**
     * 对yaml文件进行反序列化
     * @param path yaml文件路径
     * @return 反序列化后的TestcaseModel对象
     * @throws IOException
     */
    public static TestcaseModel load(String path) throws IOException {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        return mapper.readValue(new File(path),TestcaseModel.class);
    }

    public void run(){
        // 1、更新用例变量  时间戳
        testcaseVariables.put("getTimeStamp", FakeUtils.getTimeMillis());
        logger.info("更新用例变量 新增: getTimeStamp");
        // 2、遍历step 执行run方法
        steps.forEach(step -> {
            StepResultModel stepResultModel = step.run(testcaseVariables);
            // 3、处理结果变量
            if (stepResultModel.getStepVariables() != null && stepResultModel.getStepVariables().size() > 0) {
                testcaseVariables.putAll(stepResultModel.getStepVariables());
                logger.info("更新用例变量 新增："+stepResultModel.getStepVariables());
            }

            // 处理断言集合
            if (stepResultModel.getAssertList() != null && stepResultModel.getAssertList().size() > 0) {
                assertAllList.addAll(stepResultModel.getAssertList());
            }

        });

        // 执行全局断言
        assertAll("全局断言",assertAllList.stream());
    }

}
