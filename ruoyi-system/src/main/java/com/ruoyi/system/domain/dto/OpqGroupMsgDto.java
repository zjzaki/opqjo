package com.ruoyi.system.domain.dto;

import com.ruoyi.system.domain.OpqGroupMsg;

/**
 * 消息体
 * @author zjzaki
 * @create 2023年06月05日 22:38:40
 */
public class OpqGroupMsgDto extends OpqGroupMsg {

    /**
     * 消息唯一id
     */
    private Long id;
    /**
     * 消息状态 消息发送的状态：going | failed | succeed
     */
    private String status;
    /**
     * 消息类型：file | image | text | event
     */
    private String type;
    /**
     * 消息发送时间 消息发送时间，13位毫秒
     */
    private Long sendTime;
    /**
     * 消息内容 	消息内容，如果type=file，此属性表示文件的URL地址
     */
    private String content;
    /**
     * 文件大小
     */
    private Integer fileSize;
    /**
     * 文件名称
     */
    private String fileName;
    /**
     * 接收消息的联系人ID
     */
    private Long toContactId;
    /**
     * 消息发送人的信息
     */
    private ContactDto fromUser;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getSendTime() {
        return sendTime;
    }

    public void setSendTime(Long sendTime) {
        this.sendTime = sendTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getFileSize() {
        return fileSize;
    }

    public void setFileSize(Integer fileSize) {
        this.fileSize = fileSize;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Long getToContactId() {
        return toContactId;
    }

    public void setToContactId(Long toContactId) {
        this.toContactId = toContactId;
    }

    public ContactDto getFromUser() {
        return fromUser;
    }

    public void setFromUser(ContactDto fromUser) {
        this.fromUser = fromUser;
    }

    @Override
    public String toString() {
        return "OpqGroupMsgDto{" +
                "id=" + id +
                ", status='" + status + '\'' +
                ", type='" + type + '\'' +
                ", sendTime=" + sendTime +
                ", content='" + content + '\'' +
                ", fileSize=" + fileSize +
                ", fileName='" + fileName + '\'' +
                ", toContactId=" + toContactId +
                ", fromUser=" + fromUser +
                '}';
    }
}
