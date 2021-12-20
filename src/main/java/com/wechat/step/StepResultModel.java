package com.wechat.step;

import com.wechat.global.BaseResult;
import lombok.Data;
import org.junit.jupiter.api.function.Executable;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author w29530
 * @date 2021/12/15
 * @desc 封装请求结果信息  断言 变量 响应
 */
@Data
public class StepResultModel extends BaseResult {
    // 用于存放断言数据
    private ArrayList<Executable> assertList;
    // 用例变量
    private HashMap<String,String> stepVariables;

}
