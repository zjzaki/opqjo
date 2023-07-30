package com.ruoyi.system.domain;

/**
 * 插件实体
 * @author zjzaki
 * @create 2023年05月16日 15:32:21
 */
public class OpqPlugin {

    /**
     * 插件id
     */
    private String id;

    /**
     * 插件名
     */
    private String name;

    /**
     * 插件描述
     */
    private String desc;

    /**
     * 插件是否启用 1启用
     */
    private String enable;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getEnable() {
        return enable;
    }

    public void setEnable(String enable) {
        this.enable = enable;
    }
}
