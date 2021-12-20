package com.wechat.action;

import com.wechat.global.GlobalVariables;
import com.wechat.utils.PlaceholderUtils;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;

import static io.restassured.RestAssured.given;

/**
 * @author w29530
 * @date  2021/12/15
 * @desc 用来存储接口对象yaml反序列化出来的action单元
 */
@Data
public class ApiActionModel {

    // 1 声明yaml_action中对应的属性

    private String url;
    private String method;
    private String contentType;
    private ArrayList<String> formalParam;
    private HashMap<String, String> headers;
    private String body;
    private String post;
    private HashMap<String, String> query;
    private String get;
    private HashMap<String, String> actionVariables = new HashMap<>();

    public Response run(ArrayList<String> actualParam) {
        // 创建运行时变量
        String runBody = this.body;
        String runUrl = this.url;
        HashMap<String, String> runQuery = new HashMap<>();
        // 确定url和method
        if (post != null) {
            runUrl = post;
            method = "post";
        } else if (get != null) {
            runUrl = get;
            method = "get";
        }

        // 对可能存在占位符的变量进行全局实参替换
        // 需要创建一个全局变量类 和 替换形参的工具类
        if (runBody != null) {
            runBody = PlaceholderUtils.resolveString(runBody, GlobalVariables.getGlobalVariables());
        }
        if (runUrl != null) {
            runUrl = PlaceholderUtils.resolveString(runUrl, GlobalVariables.getGlobalVariables());
        }
        if (query != null) {
            runQuery.putAll(PlaceholderUtils.resolveMap(query, GlobalVariables.getGlobalVariables()));
        }

        // 将形参和实参进行整合到一个map中
        if (formalParam != null && formalParam.size() > 0 && actualParam != null && formalParam.size() == actualParam.size()) {
            // 对参数进行遍历
            for (int i = 0; i < formalParam.size(); i++) {
                actionVariables.put(formalParam.get(i), actualParam.get(i));
            }
            // 对可能存在占位符的变量根据用例变量进一步再次进行替换
            if (runBody != null) {
                runBody = PlaceholderUtils.resolveString(runBody, actionVariables);
            }
            if (runUrl != null) {
                runUrl = PlaceholderUtils.resolveString(runUrl, actionVariables);
            }
            if (query != null) {
                runQuery.putAll(PlaceholderUtils.resolveMap(query, actionVariables));
            }
        }

        // 根据获取到的请求数据执行请求并获取响应
        RequestSpecification specification = given().log().all();
        if (contentType != null) {
            specification.contentType(contentType);
        }
        if (headers != null) {
            specification.headers(headers);
        }
        if (runQuery != null) {
            specification.formParams(runQuery);
        }
        if (runBody != null) {
            specification.body(runBody);
        }
        Response response = specification.request(method, runUrl).then().log().all().extract().response();
        return response;

    }


}
