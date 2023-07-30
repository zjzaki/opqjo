package com.zjzaki.core.pojo.msg;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;

/**
 * 图片信息
 * @author zjzaki
 * @create 2023年04月06日 16:58:48
 */
public class Image {

    @JSONField(name = "FileId")
    private Long fileId;
    @JSONField(name = "FileMd5")
    private String fileMd5;
    @JSONField(name = "FileSize")
    private Integer fileSize;

    private String url;

    public Long getFileId() {
        return fileId;
    }

    public void setFileId(Long fileId) {
        this.fileId = fileId;
    }

    public String getFileMd5() {
        return fileMd5;
    }

    public void setFileMd5(String fileMd5) {
        this.fileMd5 = fileMd5;
    }

    public Integer getFileSize() {
        return fileSize;
    }

    public void setFileSize(Integer fileSize) {
        this.fileSize = fileSize;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}
