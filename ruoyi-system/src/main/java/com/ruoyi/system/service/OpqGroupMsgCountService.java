package com.ruoyi.system.service;

import com.ruoyi.system.domain.OpqGroupMsgCount;

import java.util.List;

/**
 * @author zjzaki
 * @create 2023年04月29日 21:15:53
 */
public interface OpqGroupMsgCountService {

    /**
     * 得到消息统计列表
     * @param opqGroupMsgCount 消息统计实体
     * @return 查询到的结果
     */
    List<OpqGroupMsgCount> getOpqGroupMsgCountList(OpqGroupMsgCount opqGroupMsgCount);
}
