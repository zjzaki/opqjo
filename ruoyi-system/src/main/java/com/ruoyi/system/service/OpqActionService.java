package com.ruoyi.system.service;

import com.alibaba.fastjson.JSONObject;
import com.zjzaki.core.pojo.sendMsg.CgiRequest;

/**
 * @author zjzaki
 * @create 2023年04月19日 10:40:46
 */
public interface OpqActionService {

    /**
     * 发送好友文本消息
     * @param cgiRequest 发送实体
     * @return 返回的结果
     */
    JSONObject sendFriendText(CgiRequest cgiRequest);

    /**
     * 发送群组文本消息
     * @param cgiRequest 发消息实体
     * @return 结果
     */
    JSONObject sendGroupText(CgiRequest cgiRequest);

    /**
     * 发送群组图片消息
     * @param cgiRequest 发消息实体
     * @return 结果
     */
    JSONObject sendGroupPic(CgiRequest cgiRequest);

    /**
     * 禁言群组成员
     * @param cgiRequest 发送实体
     * @return 结果
     */
    JSONObject banTimeGroupMember(CgiRequest cgiRequest);
}
