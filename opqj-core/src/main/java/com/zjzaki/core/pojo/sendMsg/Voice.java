package com.zjzaki.core.pojo.sendMsg;


import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author zjzaki
 * @create 2023年04月18日 23:26:20
 */
public class Voice {

    /**
     * 文件Md5
     */
    @JSONField(name = "FileMd5")
    private String fileMd5;
    /**
     * 文件大小
     */
    @JSONField(name = "FileSize")
    private Long fileSize;
    /**
     * 文件token
     */
    @JSONField(name = "FileId")
    private String fileId;

    @JSONField(name = "FileToken")
    private String fileToken;

    public Voice() {
    }

    public Voice(String fileMd5, Long fileSize, String fileId) {
        this.fileMd5 = fileMd5;
        this.fileSize = fileSize;
        this.fileId = fileId;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getFileMd5() {
        return fileMd5;
    }

    public void setFileMd5(String fileMd5) {
        this.fileMd5 = fileMd5;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public String getFileToken() {
        return fileToken;
    }

    public void setFileToken(String fileToken) {
        this.fileToken = fileToken;
    }
}
