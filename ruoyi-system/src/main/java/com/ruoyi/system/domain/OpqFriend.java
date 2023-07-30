package com.ruoyi.system.domain;

/**
 * @author zjzaki
 * @create 2023年04月15日 15:35:55
 */
public class OpqFriend {

    /**
     * QQ号
     */
    private Long uin;
    /**
     * QQUID
     */
    private String uid;
    /**
     * QQ昵称
     */
    private String nick;
    /**
     * qq头像
     */
    private String head;
    /**
     * 性别
     */
    private String sex;
    /**
     * 个性签名
     */
    private String signature;
    /**
     * 分组ID
     */
    private int tagId;

    public OpqFriend() {
    }

    public OpqFriend(Long uin, String uid, String nick, String head, String sex, String signature, int tagId) {
        this.uin = uin;
        this.uid = uid;
        this.nick = nick;
        this.head = head;
        this.sex = sex;
        this.signature = signature;
        this.tagId = tagId;
    }

    public Long getUin() {
        return uin;
    }

    public void setUin(Long uin) {
        this.uin = uin;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public int getTagId() {
        return tagId;
    }

    public void setTagId(int tagId) {
        this.tagId = tagId;
    }
}
