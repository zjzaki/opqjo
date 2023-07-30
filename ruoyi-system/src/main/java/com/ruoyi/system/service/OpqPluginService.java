package com.ruoyi.system.service;

import com.ruoyi.system.domain.OpqGroupPlugin;
import com.ruoyi.system.domain.OpqPlugin;

import java.util.List;

/**
 * @author zjzaki
 * @create 2023年05月16日 17:05:08
 */
public interface OpqPluginService {

    /**
     * 增加插件
     * @param opqPlugin 插件实体
     * @return 影响的行数
     */
    int addPlugin(OpqPlugin opqPlugin);

    /**
     * 通过插件名查询插件
     * @param name 插件名
     * @return 查询到的插件实体
     */
    OpqPlugin selectOpqPluginByName(String name);

    /**
     * 查询插件列表
     * @return
     */
    List<OpqPlugin> selectPluginList();

    /**
     * 通过插件名查询该插件开启的群群聊
     * @param pluginId 插件id
     * @return 查询到的结果
     */
    List<OpqGroupPlugin> selectGroupPluginListByPluginId(String pluginId);

    /**
     * 查询群聊插件列表
     * @param opqGroupPlugin 群聊插件实体
     * @return 查询的结果
     */
    List<OpqGroupPlugin> selectGroupPluginList(OpqGroupPlugin opqGroupPlugin);

    /**
     * 开启插件
     * @param opqGroupPlugin 群聊插件
     * @return 影响的行数
     */
    int addGroupPlugin(OpqGroupPlugin opqGroupPlugin);

}
