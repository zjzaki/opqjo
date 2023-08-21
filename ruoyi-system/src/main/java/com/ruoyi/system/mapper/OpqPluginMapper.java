package com.ruoyi.system.mapper;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.system.domain.OpqGroupPlugin;
import com.ruoyi.system.domain.OpqPlugin;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author zjzaki
 * @create 2023年05月16日 15:38:34
 */
@Repository("opqPluginMapper")
public interface OpqPluginMapper {

    /**
     * 增加插件
     * @param opqPlugin 插件
     * @return 影响的行数
     */
    int addPlugin(OpqPlugin opqPlugin);

    /**
     * 通过插件名查询插件信息
     * @param name 插件名
     * @return 查到的插件实体
     */
    OpqPlugin selectOpqPluginByName(String name);

    /**
     * 查询插件列表
     * @return 插件列表
     */
    List<OpqPlugin> selectPluginList();

    /**
     *  通过插件名查询该插件开启的群群聊
     * @param pluginId 插件id
     * @return 开启的群聊插件id
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
     * @param opqGroupPlugin 实体
     * @return 影响的行数
     */
    int addGroupPlugin(OpqGroupPlugin opqGroupPlugin);

    /**
     * 批量开启群聊插件
     * @param opqGroupPluginList
     * @return
     */
    int addGroupPlugins(List<OpqGroupPlugin> opqGroupPluginList);

    /**
     * 关闭插件
     * @param groupCode 群号
     * @return 影响的行数
     */
    int deleteGroupPlugin(Long groupCode);

    /**
     * 通过群号和插件id删除（关闭插件）
     * @return 影响的行数
     */
    int deleteGroupPluginByPluginIdAndGroupCode(@Param("groupCode") Long groupCode,@Param("pluginId") String pluginId);
}
