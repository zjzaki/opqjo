package com.zjzaki.core.pojo;

/**
 * 文件资源来源
 * @author zjzaki
 * @create 2023年04月19日 01:25:00
 */
public enum FileType {
    /**
     * 文件本地路径
     */
    FILE_PATH,
    /**
     * 文件网络路径
     */
    FILE_URL,
    /**
     * Base64Buf编码
     */
    BASE_64_BUF
}
