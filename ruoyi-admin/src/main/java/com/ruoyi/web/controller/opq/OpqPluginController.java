package com.ruoyi.web.controller.opq;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.opq.PluginLoader;
import com.ruoyi.system.domain.OpqGroupPlugin;
import com.ruoyi.system.domain.OpqPlugin;
import com.ruoyi.system.service.OpqPluginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.aspectj.weaver.loadtime.Aj;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.jar.JarFile;

/**
 * @author zjzaki
 * @create 2023年05月16日 20:17:54
 */
@Api("插件管理")
@RestController
@RequestMapping("/opq/plugin")
public class OpqPluginController extends BaseController {

    @Resource(name = "opqPluginServiceImpl")
    private OpqPluginService opqPluginService;

    @ApiOperation("重载插件")
    @PreAuthorize("@ss.hasPermi('opq:plugin:refresh')")
    @GetMapping("/refresh")
    public AjaxResult refreshPlugin() {

        PluginLoader instance = PluginLoader.getInstance();
        boolean b = instance.loadPluginJar();
        // 得到加载的插件列表
        List<OpqPlugin> opqPluginList = instance.getOpqPluginList();
        //得到所有jar包
        List<JarFile> jarFilesList = instance.getJarFilesList();
        // 遍历插件列表
        for (int i = 0; i < jarFilesList.size(); i++) {
            OpqPlugin opqPlugin = opqPluginList.get(i);
            //查询数据库，判断是否存在该插件
            OpqPlugin plugin = opqPluginService.selectOpqPluginByName(opqPlugin.getName());
            if (plugin == null) {
                //生成UUID
                String uuid = UUID.randomUUID().toString().replace("-", "");
                //设置uuid
                opqPlugin.setId(uuid);
                //设置启用
                opqPlugin.setEnable("1");
                //调用业务层方法，插入数据库
                opqPluginService.addPlugin(opqPlugin);
            } else if ("0".equals(plugin.getEnable())) {
//                instance.closeJarFile(i);
//                JarFile jarFile = jarFilesList.get(i);
//                String name = jarFile.getName();
                jarFilesList.remove(i);
                opqPluginList.remove(i);
            }
        }
        return toAjax(b);
    }

    @ApiOperation("获取插件列表")
    @PreAuthorize("@ss.hasPermi('opq:plugin:list')")
    @GetMapping("/list")
    public TableDataInfo list() {
        List<OpqPlugin> opqPlugins = opqPluginService.selectPluginList();
        return getDataTable(opqPlugins);
    }

    @ApiOperation("得到开启插件的群聊")
    @PreAuthorize("@ss.hasPermi('opq:groupPlugin:list')")
    @GetMapping("/groupPluginList")
    public TableDataInfo groupPluginList(OpqGroupPlugin opqGroupPlugin) {
        List<OpqGroupPlugin> opqGroupPlugins = opqPluginService.selectGroupPluginList(opqGroupPlugin);
        return getDataTable(opqGroupPlugins);
    }

    @ApiOperation("群聊开启插件")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "groupCode", value = "群号", dataType = "Long", dataTypeClass = Long.class, required = true),
            @ApiImplicitParam(name = "groupName", value = "群聊名称", dataType = "String", dataTypeClass = String.class, required = true),
            @ApiImplicitParam(name = "pluginId", value = "插件id", dataType = "String", dataTypeClass = String.class, required = false),
            @ApiImplicitParam(name = "pluginIds", value = "插件id数组", dataType = "List", dataTypeClass = List.class, required = false),
            @ApiImplicitParam(name = "admins", value = "插件管理员", dataType = "List", dataTypeClass = List.class, required = false)
    })
    @PreAuthorize("@ss.hasPermi('opq:groupPlugin:add')")
    @PostMapping
    public AjaxResult addGroupPlugin(@RequestBody OpqGroupPlugin opqGroupPlugin) {

        return toAjax(opqPluginService.addGroupPlugin(opqGroupPlugin));
    }

    @ApiOperation("通过群号和插件id删除插件开启")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "groupCode", value = "群号", dataType = "Long", dataTypeClass = Long.class, required = true),
            @ApiImplicitParam(name = "pluginId", value = "插件id", dataType = "String", dataTypeClass = String.class, required = true)
    })
    @PreAuthorize("@ss.hasPermi('opq:groupPlugin:remove')")
    @DeleteMapping
    public AjaxResult deleteGroupPlugin(Long groupCode, String pluginId) {
        if (groupCode == null) {
            return error("群号不能为空");
        }
        if ("".equals(pluginId)) {
            return error("插件id不能为空");
        }
        return toAjax(opqPluginService.deleteGroupPluginByPluginIdAndGroupCode(groupCode, pluginId));
    }
}
