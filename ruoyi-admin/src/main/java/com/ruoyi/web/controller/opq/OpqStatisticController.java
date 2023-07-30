package com.ruoyi.web.controller.opq;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.system.domain.OpqStatistic;
import com.ruoyi.system.service.IOpqStatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author zjzaki
 * @Package com.ruoyi.web.controller.opq
 * @Date 2023/7/27 12:43
 */
@RestController
@RequestMapping("/opq/statistic")
public class OpqStatisticController extends BaseController {

    @Autowired
    private IOpqStatisticService opqStatisticService;

    /**
     * 查询数据统计
     * @return 数据统计的json数据
     */
    @PreAuthorize("@ss.hasPermi('opq:statistic:data')")
    @GetMapping("/data")
    AjaxResult getOpqStatisticData(){
        OpqStatistic opqStatisticData = opqStatisticService.getOpqStatisticData();

        return success(opqStatisticData);
    }
}
