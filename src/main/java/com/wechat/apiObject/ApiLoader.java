package com.wechat.apiObject;

import com.wechat.action.ApiActionModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author w29530
 * @date  2021/12/15
 * @desc 加载目录下所有yaml文件进行反序列化并执行action
 */
public class ApiLoader {

    static Logger logger = LoggerFactory.getLogger(ApiLoader.class);
    private static ArrayList<ApiObjectModel> apiObjectModelList = new ArrayList<>();


    /**
     * 加载目录下所有yaml文件 调用ApiObjectModel.load进行反序列化
     * @param dirPath
     */
    public static void load(String dirPath){
        Arrays.stream(new File(dirPath).list()).forEach(filePath ->{
            ApiObjectModel model = null;
            try {
                model = ApiObjectModel.load(dirPath+"/"+filePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
            apiObjectModelList.add(model);
        });
    }

    /**
     * 根据apiName和 actionName 获取到对应的action并返回
     * @param apiName 接口Name
     * @param actionName 接口方法Name
     * @return 对应方法
     */
    public static ApiActionModel run(String apiName, String actionName){
        final ApiActionModel[] apiActionModel = new ApiActionModel[1];
        // 1、遍历调用ApiObjectModel激活 并根据apiName 和 actionName获取对应action执行run方法
        apiObjectModelList.stream().filter(apiObject ->apiObject.getName().equals(apiName) ).forEach(apiObjectModel -> {
            apiActionModel[0] = apiObjectModel.getActions().get(actionName);
        });

        if (apiActionModel[0] != null){
            return apiActionModel[0];
        }else {
            logger.error("没有找到"+apiName+"对应的"+actionName+"action");
        }
        return null;
    }
}
