package com.wechat.step;

import lombok.Data;

/**
 * @author w29530
 * @date   2021/12/15
 * @desc  assert映射对象
 */
@Data
public class AssertModel {
    /*
    asserts:
      - actual: errcode
        matcher: equalTo
        expect: 2
        reason: 'getToken错误码校验！'
     */
    private String actual;
    private String matcher;
    private String expect;
    private String reason;

}
