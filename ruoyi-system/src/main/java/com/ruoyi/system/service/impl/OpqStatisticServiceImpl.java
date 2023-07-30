package com.ruoyi.system.service.impl;

import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.system.domain.*;
import com.ruoyi.system.mapper.DbTableStatusMapper;
import com.ruoyi.system.mapper.OpqGroupMapper;
import com.ruoyi.system.service.IOpqStatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Author zjzaki
 * @Package com.ruoyi.system.service.impl
 * @Date 2023/7/27 12:24
 */
@Service
public class OpqStatisticServiceImpl implements IOpqStatisticService {

    @Resource(name = "redisCache")
    private RedisCache redisCache;

    @Autowired
    private OpqGroupMapper opqGroupMapper;

    @Autowired
    private DbTableStatusMapper dbTableStatusMapper;

    /**
     * 得到统计的数据
     *
     * @return 统计数据实体
     */
    @Override
    public OpqStatistic getOpqStatisticData() {
        //折线图的统计列表
        List<OpqStatisticGroupMsg> opqStatisticGroupMsgList = new ArrayList<>();
        //redis存储消息统计的键
        String key = "groupMsgCount";
        //今日消息数
        Long message = 0L;
        //数据统计实体
        OpqStatistic opqStatistic = new OpqStatistic();
        //得到缓存中的数据
        Map<String, OpqGroupMsgCount> cacheMap = redisCache.getCacheMap(key);
        //拿到存储的键值,数量就是活跃的群组数
        Set<String> groups = cacheMap.keySet();
        //遍历数据
        for (String group : groups) {
            //根据群号得到统计消息
            OpqGroupMsgCount opqGroupMsgCount = cacheMap.get(group);
            //折线图数据统计
            OpqStatisticGroupMsg opqStatisticGroupMsg = new OpqStatisticGroupMsg();
            //设置群号
            opqStatisticGroupMsg.setGroupCode(Long.parseLong(group));
            //设置今日发言消息数
            opqStatisticGroupMsg.setTodayMsgCount(opqGroupMsgCount.getMsgCount());
            //今日发言数,累加
            message += opqGroupMsgCount.getMsgCount();
            //放入列表中
            opqStatisticGroupMsgList.add(opqStatisticGroupMsg);
        }
        //设置今日发言消息数
        opqStatistic.setMessage(message);
        //设置活跃群组数据
        opqStatistic.setPeoples(groups.size());

        //===================总消息条数
        //消息总数
        Long messageCount = 0L;
        //查询所有的群组
        List<OpqGroup> opqGroups = opqGroupMapper.selectOpqGroupList(new OpqGroup());
        //遍历群组列表设置群名称
        for (OpqGroup opqGroup : opqGroups) {
            //遍历当前折线图统计信息
            for (int i = 0; i < opqStatisticGroupMsgList.size(); i++) {
                //得到当前的统计项
                OpqStatisticGroupMsg opqStatisticGroupMsg = opqStatisticGroupMsgList.get(i);
                //群号相等
                if (opqGroup.getGroupCode().equals(opqStatisticGroupMsg.getGroupCode())) {
                    //设置群聊名称
                    opqStatisticGroupMsg.setGroupName(opqGroup.getGroupName());
                    //修改列表的值
                    opqStatisticGroupMsgList.set(i, opqStatisticGroupMsg);
                }
            }
        }
        //查询群组消息表状态
        List<DbTableStatus> dbTableStatuses = dbTableStatusMapper.showTableStatus(opqGroups);
        //遍历数据
        for (DbTableStatus dbTableStatus : dbTableStatuses) {
            //遍历当前折线图统计信息
            for (int i = 0; i < opqStatisticGroupMsgList.size(); i++) {
                //得到当前的统计项
                OpqStatisticGroupMsg opqStatisticGroupMsg = opqStatisticGroupMsgList.get(i);
                //得到字符串的群号
                String groupCodeStr = dbTableStatus.getName();
                //拆分群号
                groupCodeStr = groupCodeStr.substring(groupCodeStr.lastIndexOf("tb_group_msg_") + "tb_group_msg_".length());
                //转换成long类型
                long groupCode = Long.parseLong(groupCodeStr);
                //群号相等
                if (groupCode == opqStatisticGroupMsg.getGroupCode()) {
                    //设置消息总数
                    opqStatisticGroupMsg.setMsgCount(dbTableStatus.getRows());
                    //修改列表的值
                    opqStatisticGroupMsgList.set(i, opqStatisticGroupMsg);
                }
            }
            //设置群聊消息总数
            messageCount += dbTableStatus.getRows();
        }
        //设置总消息数
        opqStatistic.setMessageCount(messageCount);
        //设置折线图数据
        opqStatistic.setOpqStatisticGroupMsgList(opqStatisticGroupMsgList);
        return opqStatistic;
    }
}
