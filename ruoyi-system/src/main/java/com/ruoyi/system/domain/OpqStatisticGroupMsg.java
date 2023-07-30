package com.ruoyi.system.domain;

/**
 * @Author zjzaki
 * @Package com.ruoyi.system.domain
 * @Date 2023/7/28 10:29
 */
public class OpqStatisticGroupMsg {
    /**
     * 群号
     */
    private Long groupCode;
    /**
     * 群名
     */
    private String groupName;
    /**
     * 今日消息统计
     */
    private Long todayMsgCount;
    /**
     * 总消息统计
     */
    private Long msgCount;

    public Long getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(Long groupCode) {
        this.groupCode = groupCode;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Long getTodayMsgCount() {
        return todayMsgCount;
    }

    public void setTodayMsgCount(Long todayMsgCount) {
        this.todayMsgCount = todayMsgCount;
    }

    public Long getMsgCount() {
        return msgCount;
    }

    public void setMsgCount(Long msgCount) {
        this.msgCount = msgCount;
    }
}
