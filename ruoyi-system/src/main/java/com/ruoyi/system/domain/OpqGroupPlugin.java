package com.ruoyi.system.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author zjzaki
 * @create 2023年05月16日 15:34:09
 */
@ApiModel(value = "OpqGroupPlugin",description = "群聊插件实体")
public class OpqGroupPlugin {

    /**
     * 群号
     */
    @ApiModelProperty("群号")
    private Long groupCode;

    /**
     * 群名
     */
    @ApiModelProperty("群聊名称")
    private String groupName;

    /**
     * 插件id
     */
    @ApiModelProperty("插件id")
    private String pluginId;

    /**
     * 多个插件id
     */
    @ApiModelProperty("插件id数组")
    private List<String> pluginIds;

    /**
     * 插件管理员
     */
    @ApiModelProperty("插件管理员")
    private List<Long> admins;

    public List<String> getPluginIds() {
        return pluginIds;
    }

    public void setPluginIds(List<String> pluginIds) {
        this.pluginIds = pluginIds;
    }

    public Long getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(Long groupCode) {
        this.groupCode = groupCode;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }


    public String getPluginId() {
        return pluginId;
    }

    public void setPluginId(String pluginId) {
        this.pluginId = pluginId;
    }

    public List<Long> getAdmins() {
        return admins;
    }

    public void setAdmins(List<Long> admins) {
        this.admins = admins;
    }
}
