package com.ruoyi.system.domain;

import java.util.List;

/**
 * @Author zjzaki
 * @Package com.ruoyi.system.domain
 * @Date 2023/7/27 11:30
 */
public class OpqStatistic {

    /**
     * 活跃的群组数
     */
    private Integer peoples;
    /**
     * 消息总数
     */
    private Long messageCount;

    /**
     * 今日消息数
     */
    private Long message;

    /**
     * 折线图统计数据
     */
    List<OpqStatisticGroupMsg> opqStatisticGroupMsgList;

    public Long getMessage() {
        return message;
    }

    public void setMessage(Long message) {
        this.message = message;
    }

    public Integer getPeoples() {
        return peoples;
    }

    public void setPeoples(Integer peoples) {
        this.peoples = peoples;
    }

    public Long getMessageCount() {
        return messageCount;
    }

    public void setMessageCount(Long messageCount) {
        this.messageCount = messageCount;
    }

    public List<OpqStatisticGroupMsg> getOpqStatisticGroupMsgList() {
        return opqStatisticGroupMsgList;
    }

    public void setOpqStatisticGroupMsgList(List<OpqStatisticGroupMsg> opqStatisticGroupMsgList) {
        this.opqStatisticGroupMsgList = opqStatisticGroupMsgList;
    }
}
