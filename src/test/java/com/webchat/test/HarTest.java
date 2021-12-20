package com.webchat.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.wechat.action.ApiActionModel;
import com.wechat.apiObject.ApiObjectModel;
import de.sstoehr.harreader.HarReader;
import de.sstoehr.harreader.model.Har;
import de.sstoehr.harreader.model.HarRequest;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.HashMap;

/**
 * @author w29530
 * @date  2021/12/15
 * @desc 根据har文件自动生成yaml文件
 */
public class HarTest {

    Logger logger = LoggerFactory.getLogger(HarTest.class);

    @Test
    void harTest() throws Exception {
        // 加载har文件
        HarReader harReader = new HarReader();
        Har har = harReader.readFromFile(new File("src/main/resources/har/qyapi.weixin.qq.com.har"));
        logger.info("debug");
        ApiObjectModel apiObjectModel = new ApiObjectModel();
        ApiActionModel apiActionModel = new ApiActionModel();
        HashMap<String , ApiActionModel> actions = new HashMap<>();
        HashMap<String,String> queryMap = new HashMap<String, String>();

        // 将har的entries的值封装到apObjectModel中
        har.getLog().getEntries().forEach(entry -> {
            HarRequest request = entry.getRequest();
            request.getQueryString().forEach(query ->{
                queryMap.put(query.getName(),query.getValue());
            });
            apiActionModel.setQuery(queryMap);
            String method = request.getMethod().toString();
            String url = request.getUrl();
            if (method.equalsIgnoreCase("get"))
            {
                apiActionModel.setGet(url);
            }else if (method.equalsIgnoreCase("post")){
                apiActionModel.setPost(url);
            }else {
                apiActionModel.setMethod(method);
                apiActionModel.setUrl(url);
            }
            actions.put(getRequestName(url),apiActionModel);
        });
        apiObjectModel.setName("tokenhelper");
        apiObjectModel.setActions(actions);
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        mapper.writeValue(new File("src/main/resources/har/tokenhelper.yaml"),apiObjectModel);

    }

    public String getRequestName(String url) {
        String[] suburl = url.split("\\u003F")[0].split("/");
        String name = "";
        if (suburl.length > 1) {
            name = suburl[suburl.length - 1];
        }else if(1==suburl.length){
            name = suburl[0];
        }
        return name;
    }

}
