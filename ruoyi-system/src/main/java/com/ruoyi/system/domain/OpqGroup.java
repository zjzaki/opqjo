package com.ruoyi.system.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;

/**
 * @author zjzaki
 * @create 2023年04月16日 15:37:12
 */
public class OpqGroup {
    /**
     * 群号
     */
    @Excel(name = "群号")
    private Long groupCode;
    /**
     * 群名
     */
    @Excel(name = "群名")
    private String groupName;
    /**
     * 总人数
     */
    @Excel(name = "总人数")
    private Integer memberCnt;
    /**
     * 群最高人数
     */
    @Excel(name = "群最高人数")
    private Integer groupCnt;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Long createTime;

    public OpqGroup() {
    }

    public OpqGroup(Long groupCode, String groupName, Integer memberCnt, Integer groupCnt, Long createTime) {
        this.groupCode = groupCode;
        this.groupName = groupName;
        this.memberCnt = memberCnt;
        this.groupCnt = groupCnt;
        this.createTime = createTime;
    }

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

    public Integer getMemberCnt() {
        return memberCnt;
    }

    public void setMemberCnt(Integer memberCnt) {
        this.memberCnt = memberCnt;
    }

    public Integer getGroupCnt() {
        return groupCnt;
    }

    public void setGroupCnt(Integer groupCnt) {
        this.groupCnt = groupCnt;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }
}
