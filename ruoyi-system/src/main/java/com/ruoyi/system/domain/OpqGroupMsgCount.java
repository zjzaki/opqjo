package com.ruoyi.system.domain;

import java.util.Date;

/**
 * @author zjzaki
 * @create 2023年04月21日 16:28:20
 */
public class OpqGroupMsgCount {
    /**
     * 群号
     */
    private Long groupCode;
    /**
     * 消息总数
     */
    private Long msgCount;
    /**
     * 日期
     */
    private Date date;
    /**
     * 最后一条消息的时间
     */
    private Long lastSpeakTime;

    private String groupName;

    public OpqGroupMsgCount() {
    }

    public OpqGroupMsgCount(Long groupCode, Long msgCount, Date date, Long lastSpeakTime) {
        this.groupCode = groupCode;
        this.msgCount = msgCount;
        this.date = date;
        this.lastSpeakTime = lastSpeakTime;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Long getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(Long groupCode) {
        this.groupCode = groupCode;
    }

    public Long getMsgCount() {
        return msgCount;
    }

    public void setMsgCount(Long msgCount) {
        this.msgCount = msgCount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Long getLastSpeakTime() {
        return lastSpeakTime;
    }

    public void setLastSpeakTime(Long lastSpeakTime) {
        this.lastSpeakTime = lastSpeakTime;
    }
}
