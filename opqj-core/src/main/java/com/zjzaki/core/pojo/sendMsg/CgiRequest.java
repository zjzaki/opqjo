package com.zjzaki.core.pojo.sendMsg;

import com.alibaba.fastjson.annotation.JSONField;
import com.zjzaki.core.pojo.msg.AtUin;
import com.zjzaki.core.pojo.msg.Image;

import java.util.List;

/**
 * @author zjzaki
 * @create 2023年04月18日 18:04:45
 */
public class CgiRequest {

    /**
     * 操作码
     */
    @JSONField(name = "OpCode")
    private Integer opCode;

    /**
     * 群号
     */
    @JSONField(name = "Uin")
    private Long uin;

    /**
     * 成员id，字符串类型
     */
    @JSONField(name = "Uid")
    private String uid;

    /**
     * 禁言时间
     */
    @JSONField(name = "BanTime")
    private Integer banTime;

    /**
     * 发送对象 好友/私聊/群组id
     */
    @JSONField(name = "ToUin")
    private Long toUin;

    @JSONField(name = "GroupCode")
    private Long groupCode;

    /**
     * 发送消息的对象类型 1好友 2群组 3私聊
     */
    @JSONField(name = "ToType")
    private Integer toType;

    /**
     * 发送消息内容
     */
    @JSONField(name = "Content")
    private String content;

    /**
     * AtUser数组
     */
    @JSONField(name = "AtUinLists")
    private List<AtUin> atUinLists;

    /**
     * 图片数组
     */
    @JSONField(name = "Images")
    private List<Image> images;

    /**
     * 语音
     */
    @JSONField(name = "Voice")
    private Voice voice;

    /**
     * 文件本地地址
     */
    @JSONField(name = "FilePath")
    private String filePath;

    /**
     * 文件网络地址
     */
    @JSONField(name = "FileUrl")
    private String fileUrl;

    /**
     * Base64Buf编码
     */
    @JSONField(name = "Base64Buf")
    private String base64Buf;

    /**
     * 1好友图片 2群组图片 26好友语音 29群组语音
     */
    @JSONField(name = "CommandId")
    private Integer commandId;

    @JSONField(name = "MsgSeq")
    private Long msgSeq;

    @JSONField(name = "MsgRandom")
    private Long msgRandom;

    @JSONField(name = "Nick")
    private String nick;

    public CgiRequest() {
    }

    public CgiRequest(Long toUin, Integer toType, String content, List<AtUin> atUinLists, List<Image> images, Voice voice) {
        this.toUin = toUin;
        this.toType = toType;
        this.content = content;
        this.atUinLists = atUinLists;
        this.images = images;
        this.voice = voice;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public Long getMsgSeq() {
        return msgSeq;
    }

    public void setMsgSeq(Long msgSeq) {
        this.msgSeq = msgSeq;
    }

    public Long getMsgRandom() {
        return msgRandom;
    }

    public void setMsgRandom(Long msgRandom) {
        this.msgRandom = msgRandom;
    }

    public Integer getOpCode() {
        return opCode;
    }

    public void setOpCode(Integer opCode) {
        this.opCode = opCode;
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

    public Integer getBanTime() {
        return banTime;
    }

    public void setBanTime(Integer banTime) {
        this.banTime = banTime;
    }

    public Integer getCommandId() {
        return commandId;
    }

    public void setCommandId(Integer commandId) {
        this.commandId = commandId;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public String getBase64Buf() {
        return base64Buf;
    }

    public void setBase64Buf(String base64Buf) {
        this.base64Buf = base64Buf;
    }

    public Long getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(Long groupCode) {
        this.groupCode = groupCode;
    }

    public Long getToUin() {
        return toUin;
    }

    public void setToUin(Long toUin) {
        this.toUin = toUin;
    }

    public Integer getToType() {
        return toType;
    }

    public void setToType(Integer toType) {
        this.toType = toType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<AtUin> getAtUinLists() {
        return atUinLists;
    }

    public void setAtUinLists(List<AtUin> atUinLists) {
        this.atUinLists = atUinLists;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public Voice getVoice() {
        return voice;
    }

    public void setVoice(Voice voice) {
        this.voice = voice;
    }
}
