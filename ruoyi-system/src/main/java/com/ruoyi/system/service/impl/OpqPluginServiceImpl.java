package com.ruoyi.system.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.ruoyi.system.domain.OpqGroupPlugin;
import com.ruoyi.system.domain.OpqPlugin;
import com.ruoyi.system.mapper.OpqPluginMapper;
import com.ruoyi.system.service.OpqPluginService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zjzaki
 * @create 2023年05月16日 17:07:23
 */
@Service("opqPluginServiceImpl")
public class OpqPluginServiceImpl implements OpqPluginService {

    @Resource(name = "opqPluginMapper")
    private OpqPluginMapper opqPluginMapper;

    /**
     * 增加插件
     *
     * @param opqPlugin 插件实体
     * @return 影响的行数
     */
    @Override
    public int addPlugin(OpqPlugin opqPlugin) {
        return opqPluginMapper.addPlugin(opqPlugin);
    }

    /**
     * 通过插件名查询插件
     *
     * @param name 插件名
     * @return 查询到的插件实体
     */
    @Override
    public OpqPlugin selectOpqPluginByName(String name) {
        return opqPluginMapper.selectOpqPluginByName(name);
    }

    /**
     * 查询插件列表
     *
     * @return
     */
    @Override
    public List<OpqPlugin> selectPluginList() {
        return opqPluginMapper.selectPluginList();
    }

    /**
     * 通过插件名查询该插件开启的群群聊
     *
     * @param pluginId 插件id
     * @return 查询到的结果
     */
    @Override
    public List<OpqGroupPlugin> selectGroupPluginListByPluginId(String pluginId) {
        return opqPluginMapper.selectGroupPluginListByPluginId(pluginId);
    }

    /**
     * 查询群聊插件列表
     *
     * @param opqGroupPlugin 群聊插件实体
     * @return 查询的结果
     */
    @Override
    public List<OpqGroupPlugin> selectGroupPluginList(OpqGroupPlugin opqGroupPlugin) {
        return opqPluginMapper.selectGroupPluginList(opqGroupPlugin);
    }

    /**
     * 开启插件
     *
     * @param opqGroupPlugin 群聊插件
     * @return 影响的行数
     */
    @Override
    public int addGroupPlugin(OpqGroupPlugin opqGroupPlugin) {
        //清除该群的所有群聊插件
        int code = opqPluginMapper.deleteGroupPlugin(opqGroupPlugin.getGroupCode());
        List<OpqGroupPlugin> newGroupPluginList = new ArrayList<>();
        //处理数据
        List<String> pluginIds = opqGroupPlugin.getPluginIds();
        if (pluginIds.size() != 0) {
            for (String pluginId : pluginIds) {
                OpqGroupPlugin tempOpqGroupPlugin = new OpqGroupPlugin();
                tempOpqGroupPlugin.setGroupCode(opqGroupPlugin.getGroupCode());
                tempOpqGroupPlugin.setGroupName(opqGroupPlugin.getGroupName());
                tempOpqGroupPlugin.setPluginId(pluginId);
                newGroupPluginList.add(tempOpqGroupPlugin);
            }
            return opqPluginMapper.addGroupPlugins(newGroupPluginList);
        } else {
            return code;
        }
    }

    /**
     * 通过群号和插件id删除（关闭插件）
     *
     * @param groupCode
     * @param pluginId
     * @return 影响的行数
     */
    @Override
    public int deleteGroupPluginByPluginIdAndGroupCode(Long groupCode, String pluginId) {
        return opqPluginMapper.deleteGroupPluginByPluginIdAndGroupCode(groupCode, pluginId);
    }
}
