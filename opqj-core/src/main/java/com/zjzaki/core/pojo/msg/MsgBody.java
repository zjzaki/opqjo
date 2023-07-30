package com.zjzaki.core.pojo.msg;

import java.util.List;

/**
 * @author zjzaki
 * @create 2023年04月06日 17:01:05
 */
public class MsgBody {
    /**
     * 0为单一或复合类型消息(文字 At 图片 自由组合)
     * 12 Xml消息 19 Video消息 51 JSON卡片消息
     */
    private Integer subMsgType;
    /**
     * 接受的内容 文字/XML/JSON
     */
    private String content;
    /**
     * @的群员列表
     */
    private List<AtUin> atUinLists;
    /**
     * 图片数组
     */
    private List<Image> images;
    /**
     * 视频
     */
    private String video;
    /**
     * 语言
     */
    private String voice;

    public Integer getSubMsgType() {
        return subMsgType;
    }

    public void setSubMsgType(Integer subMsgType) {
        this.subMsgType = subMsgType;
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

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getVoice() {
        return voice;
    }

    public void setVoice(String voice) {
        this.voice = voice;
    }

    @Override
    public String toString() {
        return "MsgBody{" +
                "subMsgType=" + subMsgType +
                ", content='" + content + '\'' +
                ", atUinLists=" + atUinLists +
                ", images=" + images +
                ", video='" + video + '\'' +
                ", voice='" + voice + '\'' +
                '}';
    }
}
