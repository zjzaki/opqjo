package com.ruoyi.system.domain;

/**
 * @author zjzaki
 * @create 2023年04月15日 15:34:27
 */
public class OpqTag {

    /**
     * 分组id
     */
    private Integer tagId;
    /**
     * 分组名
     */
    private String tagName;

    public OpqTag() {
    }

    public OpqTag(Integer tagId, String tagName) {
        this.tagId = tagId;
        this.tagName = tagName;
    }

    public Integer getTagId() {
        return tagId;
    }

    public void setTagId(Integer tagId) {
        this.tagId = tagId;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }
}
