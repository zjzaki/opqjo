package com.ruoyi.system.domain;

/**
 * @author zjzaki
 * @create 2023年04月16日 16:39:56
 */
public class OpqGroupMember {
    /**
     * 群号
     */
    private Long groupUid;
    /**
     * 群成员QQ号
     */
    private Long memberUin;
    /**
     * 群成员UID
     */
    private String memberUid;
    /**
     * 群成员昵称
     */
    private String nick;
    /**
     * 群成员标志 1群主
     */
    private Integer memberFlag;
    /**
     * 等级
     */
    private Integer level;
    /**
     * 最后发言时间
     */
    private Long lastSpeakTime;
    /**
     * 加入时间
     */
    private Long joinTime;
    /**
     * 信誉等级
     */
    private Integer creditLevel;

    public OpqGroupMember() {
    }

    public OpqGroupMember(Long groupUid) {
        this.groupUid = groupUid;
    }

    public OpqGroupMember(Long groupUid, Long memberUin, String memberUid, String nick, Integer memberFlag, Integer level, Long lastSpeakTime, Long joinTime, Integer creditLevel) {
        this.groupUid = groupUid;
        this.memberUin = memberUin;
        this.memberUid = memberUid;
        this.nick = nick;
        this.memberFlag = memberFlag;
        this.level = level;
        this.lastSpeakTime = lastSpeakTime;
        this.joinTime = joinTime;
        this.creditLevel = creditLevel;
    }

    public Long getGroupUid() {
        return groupUid;
    }

    public void setGroupUid(Long groupUid) {
        this.groupUid = groupUid;
    }

    public Long getMemberUin() {
        return memberUin;
    }

    public void setMemberUin(Long memberUin) {
        this.memberUin = memberUin;
    }

    public String getMemberUid() {
        return memberUid;
    }

    public void setMemberUid(String memberUid) {
        this.memberUid = memberUid;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public Integer getMemberFlag() {
        return memberFlag;
    }

    public void setMemberFlag(Integer memberFlag) {
        this.memberFlag = memberFlag;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Long getLastSpeakTime() {
        return lastSpeakTime;
    }

    public void setLastSpeakTime(Long lastSpeakTime) {
        this.lastSpeakTime = lastSpeakTime;
    }

    public Long getJoinTime() {
        return joinTime;
    }

    public void setJoinTime(Long joinTime) {
        this.joinTime = joinTime;
    }

    public Integer getCreditLevel() {
        return creditLevel;
    }

    public void setCreditLevel(Integer creditLevel) {
        this.creditLevel = creditLevel;
    }
}
