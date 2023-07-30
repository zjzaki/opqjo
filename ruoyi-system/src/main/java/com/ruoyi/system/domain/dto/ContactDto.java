package com.ruoyi.system.domain.dto;

/**
 * 联系人
 * @author zjzaki
 * @create 2023年06月05日 23:05:51
 */
public class ContactDto {

    /**
     * 唯一id
     */
    private Long id;
    /**
     * 显示名称
     */
    private String displayName;
    /**
     * 头像
     */
    private String avatar;
    /**
     * 通讯录索引，传入字母或数字进行排序，索引可以显示自定义文字“[1]群组”
     */
    private String index;
    /**
     * 未读消息数
     */
    private Integer unread;
    /**
     * 最近一条消息的时间戳，13位毫秒
     */
    private Long lastSendTime;
    /**
     * 最近一条消息内容
     */
    private String lastContent;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public Integer getUnread() {
        return unread;
    }

    public void setUnread(Integer unread) {
        this.unread = unread;
    }

    public Long getLastSendTime() {
        return lastSendTime;
    }

    public void setLastSendTime(Long lastSendTime) {
        this.lastSendTime = lastSendTime;
    }

    public String getLastContent() {
        return lastContent;
    }

    public void setLastContent(String lastContent) {
        this.lastContent = lastContent;
    }
}
