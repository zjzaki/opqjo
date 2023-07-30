package com.zjzaki.core.pojo;


import com.alibaba.fastjson.annotation.JSONField;
import com.zjzaki.core.pojo.sendMsg.CgiRequest;

/**
 * 发送消息结构
 * @author zjzaki
 * @create 2023年04月18日 18:03:16
 */
public class SendMsg {

    /**
     * 发送消息cmd
     */
    @JSONField(name = "CgiCmd")
    private String cgiCmd;

    /**
     * 请求体
     */
    @JSONField(name = "CgiRequest")
    private CgiRequest cgiRequest;

    public SendMsg() {
    }

    public SendMsg(String cgiCmd, CgiRequest cgiRequest) {
        this.cgiCmd = cgiCmd;
        this.cgiRequest = cgiRequest;
    }

    public String getCgiCmd() {
        return cgiCmd;
    }

    public void setCgiCmd(String cgiCmd) {
        this.cgiCmd = cgiCmd;
    }

    public CgiRequest getCgiRequest() {
        return cgiRequest;
    }

    public void setCgiRequest(CgiRequest cgiRequest) {
        this.cgiRequest = cgiRequest;
    }
}
