package com.zjzaki.core.pojo.msg;

/**
 * 群组信息
 * @author zjzaki
 * @create 2023年04月06日 16:48:48
 */
public class GroupInfo {
    private String groupCard;
    private Long groupCode;
    private Long groupInfoSeq;
    private Integer groupLevel;
    private Integer groupRank;
    private Integer groupType;
    /**
     * 群名称
     */
    private String groupName;

    public Long getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(Long groupCode) {
        this.groupCode = groupCode;
    }

    public String getGroupCard() {
        return groupCard;
    }

    public void setGroupCard(String groupCard) {
        this.groupCard = groupCard;
    }

    public Long getGroupInfoSeq() {
        return groupInfoSeq;
    }

    public void setGroupInfoSeq(Long groupInfoSeq) {
        this.groupInfoSeq = groupInfoSeq;
    }

    public Integer getGroupLevel() {
        return groupLevel;
    }

    public void setGroupLevel(Integer groupLevel) {
        this.groupLevel = groupLevel;
    }

    public Integer getGroupRank() {
        return groupRank;
    }

    public void setGroupRank(Integer groupRank) {
        this.groupRank = groupRank;
    }

    public Integer getGroupType() {
        return groupType;
    }

    public void setGroupType(Integer groupType) {
        this.groupType = groupType;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    @Override
    public String toString() {
        return "GroupInfo{" +
                "groupCard='" + groupCard + '\'' +
                ", groupCode=" + groupCode +
                ", groupInfoSeq=" + groupInfoSeq +
                ", groupLevel=" + groupLevel +
                ", groupRank=" + groupRank +
                ", groupType=" + groupType +
                ", groupName='" + groupName + '\'' +
                '}';
    }
}
