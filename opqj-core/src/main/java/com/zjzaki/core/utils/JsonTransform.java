package com.zjzaki.core.utils;

import com.alibaba.fastjson.JSONObject;
import com.zjzaki.core.pojo.Msg;

/**
 * json格式消息转换
 * @author zjzaki
 * @create 2023年04月06日 17:38:52
 */
public class JsonTransform {

    public static Msg json2Msg(JSONObject msgJsonObject){
        Msg msg = msgJsonObject.toJavaObject(Msg.class);
        return msg;
    }
}
