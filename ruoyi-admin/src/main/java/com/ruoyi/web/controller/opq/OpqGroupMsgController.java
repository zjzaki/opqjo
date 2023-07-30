package com.ruoyi.web.controller.opq;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.system.domain.OpqGroup;
import com.ruoyi.system.domain.OpqGroupMsg;
import com.ruoyi.system.domain.OpqGroupMsgCount;
import com.ruoyi.system.domain.dto.OpqGroupMsgDto;
import com.ruoyi.system.service.OpqGroupMsgCountService;
import com.ruoyi.system.service.OpqGroupMsgService;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zjzaki
 * @create 2023年04月21日 18:38:30
 */
@RestController
@RequestMapping("/opq/msg")
@EnableScheduling
public class OpqGroupMsgController extends BaseController {

    @Resource(name = "opqGroupMsgServiceImpl")
    private OpqGroupMsgService opqGroupMsgService;

    @Resource(name = "opqGroupMsgCountServiceImpl")
    private OpqGroupMsgCountService opqGroupMsgCountService;

    @GetMapping("/group")
    public AjaxResult getTodayGroupMgsCountList(){
        return success(opqGroupMsgService.getTodayMsgCountList());
    }

    @GetMapping("/group/list")
    public TableDataInfo getOpqGroupMsgCountList(OpqGroupMsgCount opqGroupMsgCount){
        startPage();
        List<OpqGroupMsgCount> opqGroupMsgCountList = opqGroupMsgCountService.getOpqGroupMsgCountList(opqGroupMsgCount);
        return getDataTable(opqGroupMsgCountList);
    }

    @Scheduled(cron = "59 59 23 * * ?")
    public void deleteRedisCacheGroupMsgCount(){
        //获取一次消息，将数据存入数据库
        getTodayGroupMgsCountList();
        //清空redis中存储的消息统计数据
        opqGroupMsgService.deleteRedisCacheGroupMsgCount();
        logger.info("清除缓存中的群聊消息统计");
    }

    @GetMapping("/list")
    public TableDataInfo selectOpqGroupMsgFlashback(OpqGroupMsg opqGroupMsg){
        startPage();
        List<OpqGroupMsgDto> opqGroupMsgDtos = opqGroupMsgService.selectOpqGroupMsgFlashback(opqGroupMsg);

        return getDataTable(opqGroupMsgDtos);
    }
}
