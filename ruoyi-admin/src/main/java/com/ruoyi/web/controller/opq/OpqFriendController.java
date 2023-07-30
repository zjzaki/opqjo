package com.ruoyi.web.controller.opq;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.system.domain.OpqFriend;
import com.ruoyi.system.service.OpqFriendService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zjzaki
 * @create 2023年04月17日 23:22:06
 */
@RestController
@RequestMapping("/opq/friend")
public class OpqFriendController extends BaseController {

    @Resource(name = "opqFriendServiceImpl")
    private OpqFriendService opqFriendService;

    @PreAuthorize("@ss.hasPermi('opq:friend:list')")
    @GetMapping("/list")
    public TableDataInfo list(OpqFriend opqFriend) {
        startPage();
        List<OpqFriend> opqFriends = opqFriendService.selectOpqFriendList(opqFriend);
        return getDataTable(opqFriends);
    }
}
