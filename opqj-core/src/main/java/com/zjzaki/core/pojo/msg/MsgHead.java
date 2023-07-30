package com.zjzaki.core.pojo.msg;

/**
 * @author zjzaki
 * @create 2023年04月06日 17:01:41
 */
public class MsgHead {
    /**
     * 消息来源 好友/私聊/群组Uid
     */
    private Long fromUin;
    /**
     * 接收消息对象 机器人
     */
    private Long toUin;
    /**
     * 消息来源类型 1好友 2群组 3私聊
     */
    private Integer fromType;
    /**
     * 触发消息对象 Uid
     */
    private Long senderUin;
    /**
     * 触发消息对象 昵称 群组有值 私聊好友为空
     */
    private String senderNick;
    /**
     * 消息类型
     */
    private Integer msgType;
    /**
     * C2c消息类型
     */
    private Integer c2cCmd;
    /**
     * MsgSeq
     */
    private Long msgSeq;
    private Long msgTime;
    private Long msgRandom;
    private Long msgUid;
    /**
     * 群聊消息 GroupInfo 不为null
     */
    private GroupInfo groupInfo;
    private String c2CTempMessageHead;

    public Long getFromUin() {
        return fromUin;
    }

    public void setFromUin(Long fromUin) {
        this.fromUin = fromUin;
    }

    public Long getToUin() {
        return toUin;
    }

    public void setToUin(Long toUin) {
        this.toUin = toUin;
    }

    public Integer getFromType() {
        return fromType;
    }

    public void setFromType(Integer fromType) {
        this.fromType = fromType;
    }

    public Long getSenderUin() {
        return senderUin;
    }

    public void setSenderUin(Long senderUin) {
        this.senderUin = senderUin;
    }

    public String getSenderNick() {
        return senderNick;
    }

    public void setSenderNick(String senderNick) {
        this.senderNick = senderNick;
    }

    public Integer getMsgType() {
        return msgType;
    }

    public void setMsgType(Integer msgType) {
        this.msgType = msgType;
    }

    public Integer getC2cCmd() {
        return c2cCmd;
    }

    public void setC2cCmd(Integer c2cCmd) {
        this.c2cCmd = c2cCmd;
    }

    public Long getMsgSeq() {
        return msgSeq;
    }

    public void setMsgSeq(Long msgSeq) {
        this.msgSeq = msgSeq;
    }

    public Long getMsgTime() {
        return msgTime;
    }

    public void setMsgTime(Long msgTime) {
        this.msgTime = msgTime;
    }

    public Long getMsgRandom() {
        return msgRandom;
    }

    public void setMsgRandom(Long msgRandom) {
        this.msgRandom = msgRandom;
    }

    public Long getMsgUid() {
        return msgUid;
    }

    public void setMsgUid(Long msgUid) {
        this.msgUid = msgUid;
    }

    public GroupInfo getGroupInfo() {
        return groupInfo;
    }

    public void setGroupInfo(GroupInfo groupInfo) {
        this.groupInfo = groupInfo;
    }

    public String getC2CTempMessageHead() {
        return c2CTempMessageHead;
    }

    public void setC2CTempMessageHead(String c2CTempMessageHead) {
        this.c2CTempMessageHead = c2CTempMessageHead;
    }

    @Override
    public String toString() {
        return "MsgHead{" +
                "fromUin=" + fromUin +
                ", toUin=" + toUin +
                ", fromType=" + fromType +
                ", senderUin=" + senderUin +
                ", senderNick='" + senderNick + '\'' +
                ", msgType=" + msgType +
                ", c2cCmd=" + c2cCmd +
                ", msgSeq=" + msgSeq +
                ", msgTime=" + msgTime +
                ", msgRandom=" + msgRandom +
                ", msgUid=" + msgUid +
                ", groupInfo=" + groupInfo +
                ", c2CTempMessageHead='" + c2CTempMessageHead + '\'' +
                '}';
    }
}
