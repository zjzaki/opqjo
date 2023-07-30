package com.zjzaki.core.pojo;

import com.zjzaki.core.pojo.msg.MsgBody;
import com.zjzaki.core.pojo.msg.MsgHead;

/**
 * 消息实体
 * @author zjzaki
 * @create 2023年04月06日 17:03:16
 */
public class Msg {
    /**
     * 消息头
     */
    private MsgHead msgHead;
    /**
     * 消息体
     */
    private MsgBody msgBody;

    /**
     * 当前机器人qq
     */
    private Long botUin;

    public Long getBotUin() {
        return botUin;
    }

    public void setBotUin(Long botUin) {
        this.botUin = botUin;
    }

    public MsgHead getMsgHead() {
        return msgHead;
    }

    public void setMsgHead(MsgHead msgHead) {
        this.msgHead = msgHead;
    }

    public MsgBody getMsgBody() {
        return msgBody;
    }

    public void setMsgBody(MsgBody msgBody) {
        this.msgBody = msgBody;
    }

    @Override
    public String toString() {
        return "Msg{" +
                "msgHead=" + msgHead +
                ", msgBody=" + msgBody +
                '}';
    }
}
