package com.ruoyi.system.mapper;

import com.ruoyi.system.domain.OpqGroup;
import com.ruoyi.system.domain.OpqGroupMsgCount;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author zjzaki
 * @create 2023年04月21日 23:36:02
 */
@Repository("opqGroupMsgCountMapper")
public interface OpqGroupMsgCountMapper {

    /**
     * 批量插入群聊消息统计
     * @param opqGroupMsgCountList 群聊消息统计列表
     * @return 影响的行数
     */
    int insertOpqGroupMsgCountList(List<OpqGroupMsgCount> opqGroupMsgCountList);

    /**
     * 得到今天的消息统计
     * @param opqGroupList 群聊列表
     * @return 消息统计列表
     */
    List<OpqGroupMsgCount> getOpqGroupMsgCountToday(List<OpqGroup> opqGroupList);

    /**
     * 查询消息统计列表
     * @param opqGroupMsgCount 统计信息实体
     * @return 查询到的列表
     */
    List<OpqGroupMsgCount> getOpqGroupMsgCountList(OpqGroupMsgCount opqGroupMsgCount);
}
