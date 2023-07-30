package com.ruoyi.system.domain;

/**
 * @author zjzaki
 * @create 2023年04月16日 20:32:00
 */
public class OpqGroupMsg {

    /**
     * 消息id
     */
    private Long msgId;
    /**
     * 群号
     */
    private Long groupUin;
    /**
     * 机器人id
     */
    private Long botUin;
    /**
     * 触犯消息对象，qq
     */
    private Long senderUin;
    /**
     * 触发消息对象昵称，群组有值，私聊为空
     */
    private String senderNick;
    private Integer flag;
    private String nickName;
    private Integer msgType;
    private Integer c2cCmd;
    /**
     * 消息序列
     */
    private Long msgSeq;
    /**
     * 消息时间
     */
    private Long msgTime;
    /**
     * 消息随机数
     */
    private Long msgRandom;
    /**
     * 消息id
     */
    private Long msgUid;
    private String groupCard;
    private Long groupCode;
    private Long groupInfoSeq;
    private Integer groupLevel;
    private Integer groupRank;
    private Integer groupType;
    private String groupName;
    private Integer subMsgType;
    private String context;
    private String atUinLists;
    private String images;
    private String videos;
    private String voice;
    private String redBagInfo;
    private Integer revokeFlag;
    private Long revokeAdminUin;
    private Long revokeUin;
    private Integer revokeTime;

    public OpqGroupMsg() {
    }

    public OpqGroupMsg(Long msgId, Long groupUin, Long botUin, Long senderUin, String senderNick, Integer flag, String nickName, Integer msgType, Integer c2cCmd, Long msgSeq, Long msgTime, Long msgRandom, Long msgUid, String groupCard, Long groupCode, Long groupInfoSeq, Integer groupLevel, Integer groupRank, Integer groupType, String groupName, Integer subMsgType, String context, String atUinLists, String images, String videos, String voice, String redBagInfo, Integer revokeFlag, Long revokeAdminUin, Long revokeUin, Integer revokeTime) {
        this.msgId = msgId;
        this.groupUin = groupUin;
        this.botUin = botUin;
        this.senderUin = senderUin;
        this.senderNick = senderNick;
        this.flag = flag;
        this.nickName = nickName;
        this.msgType = msgType;
        this.c2cCmd = c2cCmd;
        this.msgSeq = msgSeq;
        this.msgTime = msgTime;
        this.msgRandom = msgRandom;
        this.msgUid = msgUid;
        this.groupCard = groupCard;
        this.groupCode = groupCode;
        this.groupInfoSeq = groupInfoSeq;
        this.groupLevel = groupLevel;
        this.groupRank = groupRank;
        this.groupType = groupType;
        this.groupName = groupName;
        this.subMsgType = subMsgType;
        this.context = context;
        this.atUinLists = atUinLists;
        this.images = images;
        this.videos = videos;
        this.voice = voice;
        this.redBagInfo = redBagInfo;
        this.revokeFlag = revokeFlag;
        this.revokeAdminUin = revokeAdminUin;
        this.revokeUin = revokeUin;
        this.revokeTime = revokeTime;
    }


    public Long getMsgId() {
        return msgId;
    }

    public void setMsgId(Long msgId) {
        this.msgId = msgId;
    }

    public Long getGroupUin() {
        return groupUin;
    }

    public void setGroupUin(Long groupUin) {
        this.groupUin = groupUin;
    }

    public Long getBotUin() {
        return botUin;
    }

    public void setBotUin(Long botUin) {
        this.botUin = botUin;
    }

    public Long getSenderUin() {
        return senderUin;
    }

    public void setSenderUin(Long senderUin) {
        this.senderUin = senderUin;
    }

    public String getSenderNick() {
        return senderNick;
    }

    public void setSenderNick(String senderNick) {
        this.senderNick = senderNick;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Integer getMsgType() {
        return msgType;
    }

    public void setMsgType(Integer msgType) {
        this.msgType = msgType;
    }

    public Integer getC2cCmd() {
        return c2cCmd;
    }

    public void setC2cCmd(Integer c2cCmd) {
        this.c2cCmd = c2cCmd;
    }

    public Long getMsgSeq() {
        return msgSeq;
    }

    public void setMsgSeq(Long msgSeq) {
        this.msgSeq = msgSeq;
    }

    public Long getMsgTime() {
        return msgTime;
    }

    public void setMsgTime(Long msgTime) {
        this.msgTime = msgTime;
    }

    public Long getMsgRandom() {
        return msgRandom;
    }

    public void setMsgRandom(Long msgRandom) {
        this.msgRandom = msgRandom;
    }

    public Long getMsgUid() {
        return msgUid;
    }

    public void setMsgUid(Long msgUid) {
        this.msgUid = msgUid;
    }

    public String getGroupCard() {
        return groupCard;
    }

    public void setGroupCard(String groupCard) {
        this.groupCard = groupCard;
    }

    public Long getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(Long groupCode) {
        this.groupCode = groupCode;
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

    public Integer getSubMsgType() {
        return subMsgType;
    }

    public void setSubMsgType(Integer subMsgType) {
        this.subMsgType = subMsgType;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getAtUinLists() {
        return atUinLists;
    }

    public void setAtUinLists(String atUinLists) {
        this.atUinLists = atUinLists;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getVideos() {
        return videos;
    }

    public void setVideos(String videos) {
        this.videos = videos;
    }

    public String getVoice() {
        return voice;
    }

    public void setVoice(String voice) {
        this.voice = voice;
    }

    public String getRedBagInfo() {
        return redBagInfo;
    }

    public void setRedBagInfo(String redBagInfo) {
        this.redBagInfo = redBagInfo;
    }

    public Integer getRevokeFlag() {
        return revokeFlag;
    }

    public void setRevokeFlag(Integer revokeFlag) {
        this.revokeFlag = revokeFlag;
    }

    public Long getRevokeAdminUin() {
        return revokeAdminUin;
    }

    public void setRevokeAdminUin(Long revokeAdminUin) {
        this.revokeAdminUin = revokeAdminUin;
    }

    public Long getRevokeUin() {
        return revokeUin;
    }

    public void setRevokeUin(Long revokeUin) {
        this.revokeUin = revokeUin;
    }

    public Integer getRevokeTime() {
        return revokeTime;
    }

    public void setRevokeTime(Integer revokeTime) {
        this.revokeTime = revokeTime;
    }


    @Override
    public String toString() {
        return "OpqGroupMsg{" +
                "msgId=" + msgId +
                ", groupUin=" + groupUin +
                ", botUin=" + botUin +
                ", senderUin=" + senderUin +
                ", senderNick='" + senderNick + '\'' +
                ", flag=" + flag +
                ", nickName='" + nickName + '\'' +
                ", msgType=" + msgType +
                ", c2cCmd=" + c2cCmd +
                ", msgSeq=" + msgSeq +
                ", msgTime=" + msgTime +
                ", msgRandom=" + msgRandom +
                ", msgUid=" + msgUid +
                ", groupCard='" + groupCard + '\'' +
                ", groupCode=" + groupCode +
                ", groupInfoSeq=" + groupInfoSeq +
                ", groupLevel=" + groupLevel +
                ", groupRank=" + groupRank +
                ", groupType=" + groupType +
                ", groupName='" + groupName + '\'' +
                ", subMsgType=" + subMsgType +
                ", context='" + context + '\'' +
                ", atUinLists='" + atUinLists + '\'' +
                ", images='" + images + '\'' +
                ", videos='" + videos + '\'' +
                ", voice='" + voice + '\'' +
                ", redBagInfo='" + redBagInfo + '\'' +
                ", revokeFlag=" + revokeFlag +
                ", revokeAdminUin=" + revokeAdminUin +
                ", revokeUin=" + revokeUin +
                ", revokeTime=" + revokeTime +
                '}';
    }
}
