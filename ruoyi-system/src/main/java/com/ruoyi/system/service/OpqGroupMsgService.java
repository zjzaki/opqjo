package com.ruoyi.system.service;

import com.ruoyi.system.domain.OpqGroupMsg;
import com.ruoyi.system.domain.OpqGroupMsgCount;
import com.ruoyi.system.domain.dto.OpqGroupMsgDto;
import com.zjzaki.core.pojo.Msg;

import java.util.List;

/**
 * @author zjzaki
 * @create 2023年04月16日 21:10:42
 */
public interface OpqGroupMsgService {

    /**
     * 判断表是否存在
     * @param tableName 表名
     * @return 是否存在 true存在 false不存在
     */
    boolean existTable(String tableName);

    /**
     * 创建群消息表
     * @param tableName 表名
     * @return 影响的行数
     */
    int createNewGroupMsgTable(String tableName);

    /**
     * 插入群聊消息
     * @param opqGroupMsg 群聊消息
     * @return 影响的行数
     */
    int insertGroupMsg(OpqGroupMsg opqGroupMsg);

    /**
     * 消息统计放入redis
     * @param msg 消息
     * @return 是否放入成功
     */
    boolean insertRedisCacheMsgCount(Msg msg);

    /**
     * 得到今天发言的消息统计
     * @return hashMap格式的消息统计
     */
    List<OpqGroupMsgCount> getTodayMsgCountList();

    /**
     * 批量插入消息统计至数据库中
     * @param opqGroupMsgCountList  消息统计数据列表
     * @return 影响的行数
     */
    int insertOpqGroupMsgCountList(List<OpqGroupMsgCount> opqGroupMsgCountList);

    /**
     * 清楚缓存中的消息统计数据
     * @return 是否清除成功
     */
    boolean deleteRedisCacheGroupMsgCount();

    /**
     * 反序查询消息列表
     * @param opqGroupMsg 群消息
     * @return 消息列表
     */
    List<OpqGroupMsgDto> selectOpqGroupMsgFlashback(OpqGroupMsg opqGroupMsg);

}
