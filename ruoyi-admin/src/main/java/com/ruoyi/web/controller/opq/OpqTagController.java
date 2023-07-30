package com.ruoyi.web.controller.opq;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.system.domain.OpqTag;
import com.ruoyi.system.service.OpqTagService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zjzaki
 * @create 2023年04月16日 13:58:07
 */
@RestController
@RequestMapping("/opq/tag")
public class OpqTagController extends BaseController {

    @Resource(name = "opqTagServiceImpl")
    private OpqTagService opqTagService;

    @PostMapping
    public AjaxResult add(@RequestBody OpqTag opqTag){
        return toAjax(opqTagService.insertTag(opqTag));
    }

    @PreAuthorize("@ss.hasPermi('opq:tag:refresh')")
    @GetMapping("/refresh")
    public AjaxResult refreshTagAndFriendList(){
        return toAjax(opqTagService.refreshTagAndFriendList());
    }

    @PreAuthorize("@ss.hasPermi('opq:tag:list')")
    @GetMapping("/list")
    public TableDataInfo list(OpqTag opqTag){
        startPage();
        List<OpqTag> opqTags = opqTagService.selectOpqTagList(opqTag);
        return getDataTable(opqTags);
    }

}
