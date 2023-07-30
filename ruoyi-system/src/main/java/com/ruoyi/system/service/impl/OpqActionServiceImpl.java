package com.ruoyi.system.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.ruoyi.system.service.OpqActionService;
import com.zjzaki.core.action.Action;
import com.zjzaki.core.pojo.sendMsg.CgiRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @author zjzaki
 * @create 2023年04月19日 12:16:21
 */
@Service("opqActionServiceImpl")
public class OpqActionServiceImpl implements OpqActionService {


    /**
     * 发送好友文本消息
     *
     * @param cgiRequest 发送实体
     * @return 返回的结果
     */
    @Override
    public JSONObject sendFriendText(CgiRequest cgiRequest) {
        String s = Action.sendFriendText(cgiRequest);
        return JSONObject.parseObject(s);
    }

    /**
     * 发送群组文本消息
     *
     * @param cgiRequest 发消息实体
     * @return 结果
     */
    @Override
    public JSONObject sendGroupText(CgiRequest cgiRequest) {
        String s = Action.sendGroupText(cgiRequest);
        return JSONObject.parseObject(s);
    }

    /**
     * 发送群组图片消息
     *
     * @param cgiRequest 发消息实体
     * @return 结果
     */
    @Override
    public JSONObject sendGroupPic(CgiRequest cgiRequest) {
        String s = Action.sendGroupPic(cgiRequest);
        return JSONObject.parseObject(s);
    }

    /**
     * 禁言群组成员
     *
     * @param cgiRequest 发送实体
     * @return 结果
     */
    @Override
    public JSONObject banTimeGroupMember(CgiRequest cgiRequest) {
        String s = Action.banTimeGroupMember(cgiRequest);
        return JSONObject.parseObject(s);
    }

}
