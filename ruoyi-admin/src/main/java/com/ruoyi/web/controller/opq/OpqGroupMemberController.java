package com.ruoyi.web.controller.opq;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.system.domain.OpqGroupMember;
import com.ruoyi.system.service.OpqGroupMemberService;
import com.ruoyi.system.service.OpqGroupService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zjzaki
 * @create 2023年04月18日 14:36:51
 */
@RestController
@RequestMapping("/opq/member")
public class OpqGroupMemberController extends BaseController {

    @Resource(name = "opqGroupMemberServiceImpl")
    private OpqGroupMemberService opqGroupMemberService;

    @PreAuthorize("@ss.hasPermi('opq:member:list')")
    @GetMapping("/list")
    public TableDataInfo list(OpqGroupMember opqGroupMember){
        startPage();
        List<OpqGroupMember> opqGroupMembers = opqGroupMemberService.selectOpqGroupMemberList(opqGroupMember);
        return getDataTable(opqGroupMembers);
    }
}
