package com.wechat.global;

import io.restassured.response.Response;

/**
 * @author w29530
 * @date   2021/12/15
 * @desc 存储响应信息
 */
public class BaseResult {

    private Response response;

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }
}
