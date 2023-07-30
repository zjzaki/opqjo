package com.ruoyi.system.service;

import com.ruoyi.system.domain.OpqStatistic;

/**
 * @Author zjzaki
 * @Package com.ruoyi.system.service
 * @Date 2023/7/27 12:20
 */
public interface IOpqStatisticService {

    /**
     * 得到统计的数据
     * @return 统计数据实体
     */
    OpqStatistic getOpqStatisticData();
}
