package com.ruoyi.system.service.impl;

import com.ruoyi.system.domain.OpqGroupMsgCount;
import com.ruoyi.system.mapper.OpqGroupMsgCountMapper;
import com.ruoyi.system.service.OpqGroupMsgCountService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zjzaki
 * @create 2023年04月29日 21:17:37
 */
@Service("opqGroupMsgCountServiceImpl")
public class OpqGroupMsgCountServiceImpl implements OpqGroupMsgCountService {

    @Resource(name = "opqGroupMsgCountMapper")
    private OpqGroupMsgCountMapper opqGroupMsgCountMapper;

    /**
     * 得到消息统计列表
     *
     * @param opqGroupMsgCount 消息统计实体
     * @return 查询到的结果
     */
    @Override
    public List<OpqGroupMsgCount> getOpqGroupMsgCountList(OpqGroupMsgCount opqGroupMsgCount) {
        return opqGroupMsgCountMapper.getOpqGroupMsgCountList(opqGroupMsgCount);
    }
}
