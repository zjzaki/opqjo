package com.ruoyi.web.controller.opq;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.domain.OpqGroup;
import com.ruoyi.system.service.OpqGroupService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author zjzaki
 * @create 2023年04月16日 16:14:20
 */
@RestController
@RequestMapping("/opq/group")
public class OpqGroupController extends BaseController {

    @Resource(name = "opqGroupServiceImpl")
    private OpqGroupService opqGroupService;

    @PreAuthorize("@ss.hasPermi('opq:group:refresh')")
    @GetMapping("/refresh")
    public AjaxResult refreshGroup(){
        return toAjax(opqGroupService.refreshOpqGroupList());
    }

    /**
     * 查询群组列表
     * @param opqGroup 群组实体
     * @return 结果
     */
    @PreAuthorize("@ss.hasPermi('opq:group:list')")
    @GetMapping("/list")
    public TableDataInfo list(OpqGroup opqGroup){
        startPage();
        List<OpqGroup> opqGroups = opqGroupService.selectOpqGroupList(opqGroup);
        return getDataTable(opqGroups);
    }

    @PreAuthorize("@ss.hasPermi('opq:group:alllist')")
    @GetMapping("/allList")
    public AjaxResult allList(OpqGroup opqGroup){
        List<OpqGroup> opqGroups = opqGroupService.selectOpqGroupList(opqGroup);
        return success(opqGroups);
    }

    /**
     * 导出群组列表
     */
    @PreAuthorize("@ss.hasPermi('opq:group:export')")
    @Log(title = "群组管理",businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response,OpqGroup opqGroup){
        List<OpqGroup> opqGroups = opqGroupService.selectOpqGroupList(opqGroup);
        ExcelUtil<OpqGroup> opqGroupExcelUtil = new ExcelUtil<>(OpqGroup.class);
        opqGroupExcelUtil.exportExcel(response,opqGroups,"群组数据");
    }

    /**
     * 获取群组详细信息
     * @param groupCode 群号
     * @return 结果
     */
    @PreAuthorize("@ss.hasPermi('opq:group:query')")
    @GetMapping(value = "/{groupCode}")
    public AjaxResult getInfo(@PathVariable("groupCode") Long groupCode){
        return success(opqGroupService.selectOpqGroupByGroupCode(groupCode));
    }

    /**
     * 新增群组
     */
    @PreAuthorize("@ss.hasPermi('opq:group:add')")
    @Log(title = "群组管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody OpqGroup opqGroup)
    {
        return toAjax(opqGroupService.insertOpqGroup(opqGroup));
    }

    /**
     * 修改群组
     */
    @PreAuthorize("@ss.hasPermi('opq:group:edit')")
    @Log(title = "群组", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody OpqGroup opqGroup)
    {
        return toAjax(opqGroupService.updateOpqGroup(opqGroup));
    }

    /**
     * 删除群组
     */
    @PreAuthorize("@ss.hasPermi('opq:group:remove')")
    @Log(title = "群组", businessType = BusinessType.DELETE)
    @DeleteMapping("/{groupCodes}")
    public AjaxResult remove(@PathVariable Long[] groupCodes)
    {
        return toAjax(opqGroupService.deleteOpqGroupByGroupCodes(groupCodes));
    }
}
