package com.wechat.step;

import com.wechat.apiObject.ApiLoader;
import com.wechat.global.GlobalVariables;
import com.wechat.utils.FakeUtils;
import com.wechat.utils.PlaceholderUtils;
import io.restassured.response.Response;
import lombok.Data;
import org.junit.jupiter.api.function.Executable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Pattern;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

/**
 * @author w29530
 * @date 2021/12/15
 * @desc 用例具体测试步骤
 */
@Data
public class TestcaseStepModel {
    // 创建testcase_yaml 文件对应属性

    private String api;
    private String action;
    private ArrayList<String> actualParameter;
    private HashMap<String,String> save;
    private HashMap<String,String> saveGlobal;
    /**
     *     需要创建assertModel
     */
    private ArrayList<AssertModel> asserts;

    /**
     * 需要创建一个 stepResult 承接执行后返回的结果 里面需要封装响应 断言列表 用例变量
     */
    private StepResultModel stepResultModel = new StepResultModel();
    private HashMap<String,String> stepVariables = new HashMap<>();
    private ArrayList<String> finalActualParameter = new ArrayList<>();
    private ArrayList<Executable> assertList = new ArrayList<>();

    Logger logger = LoggerFactory.getLogger(TestcaseStepModel.class);
    public StepResultModel run(HashMap<String,String> testcaseVariable){
        // 1 先将实参中存在的占位符进行替换  时间戳
        if (actualParameter != null){
            logger.info("进行基本参数替换 时间戳 随机数");
            for (String actualParam: actualParameter
                 ) {
                if (actualParam.contains("${random")){
                    // 获取随机数位数
                    String regex = "[^0-9]";
                    Pattern pattern = Pattern.compile(regex);
                    Integer len = Integer.valueOf(pattern.matcher(actualParam).replaceAll(""));
                    String random = FakeUtils.getRandom(len);
                    testcaseVariable.put("random"+len,random);
                    logger.info("生成"+len+"位的随机数: "+random+"");
                }
            }
            finalActualParameter.addAll(PlaceholderUtils.resolveList(actualParameter,testcaseVariable));
        }

        // 2 通过ApiLoader 获取所有用例文件 在根据 api 和 action 拿到对应action执行run方法获取响应
        Response response = ApiLoader.run(api, action).run(finalActualParameter);

        // 3 存储变量
        // 先存储局部变量
        if (save != null){
            save.forEach((variableKey,path) -> {
                stepVariables.put(variableKey,response.path(path).toString());
            });
        }
        // 存储全局变量
        if (saveGlobal !=null){
            saveGlobal.forEach((variableKey,path) ->{
                GlobalVariables.getGlobalVariables().put(variableKey,response.path(path).toString());
            });
        }


        // 4 封装断言列表
        if (asserts != null){
            asserts.forEach(assertModel -> {
                assertList.add(() -> {
                    if (assertModel.getMatcher().equals("equalTo")) {
                        assertThat(assertModel.getReason(), response.path(assertModel.getActual()).toString(), equalTo(assertModel.getExpect()));
                    }else if (assertModel.getMatcher().equals("containsString")) {
                        assertThat(assertModel.getReason(), response.path(assertModel.getActual()).toString(), containsString(assertModel.getExpect()));
                    }
                });
            });
        }
        // 5 封装result
        stepResultModel.setAssertList(assertList);
        stepResultModel.setStepVariables(stepVariables);
        stepResultModel.setResponse(response);
        return stepResultModel;

    }


}
