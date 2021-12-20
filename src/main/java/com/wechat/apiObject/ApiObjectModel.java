package com.wechat.apiObject;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.wechat.action.ApiActionModel;
import lombok.Data;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

/**
 * @author w29530
 * @date   2021/12/15
 * @desc  api_yaml 映射类
 */
@Data
public class ApiObjectModel {
    private String name;
    private HashMap<String , ApiActionModel> actions;

    /**
     * 对yaml文件进行反序列化
     * @param path 文件路径
     * @return 反序列化后的ApiObjectModel对象
     * @throws IOException
     */
    public static ApiObjectModel load(String path) throws IOException {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        return mapper.readValue(new File(path),ApiObjectModel.class);
    }



}
