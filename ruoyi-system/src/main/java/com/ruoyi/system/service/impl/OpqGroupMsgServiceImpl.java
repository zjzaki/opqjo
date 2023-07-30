package com.ruoyi.system.service.impl;

import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.system.domain.OpqGroupMsg;
import com.ruoyi.system.domain.OpqGroupMsgCount;
import com.ruoyi.system.domain.dto.ContactDto;
import com.ruoyi.system.domain.dto.OpqGroupMsgDto;
import com.ruoyi.system.mapper.OpqGroupMsgCountMapper;
import com.ruoyi.system.mapper.OpqGroupMsgMapper;
import com.ruoyi.system.service.OpqGroupMsgService;
import com.zjzaki.core.pojo.Msg;
import com.zjzaki.core.pojo.msg.MsgBody;
import com.zjzaki.core.pojo.msg.MsgHead;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.BiConsumer;

/**
 * @author zjzaki
 * @create 2023年04月16日 21:12:08
 */
@Service("opqGroupMsgServiceImpl")
public class OpqGroupMsgServiceImpl implements OpqGroupMsgService {

    @Resource(name = "opqGroupMsgMapper")
    private OpqGroupMsgMapper opqGroupMsgMapper;

    @Resource(name = "opqGroupMsgCountMapper")
    private OpqGroupMsgCountMapper opqGroupMsgCountMapper;

    @Resource(name = "redisCache")
    private RedisCache redisCache;

    /**
     * 消息统计数据redis key值
     */
    private static final String GROUP_MSG_COUNT_KEY = "groupMsgCount";

    /**
     * 判断表是否存在
     *
     * @param tableName 表名
     * @return 是否存在 true存在 false不存在
     */
    @Override
    public boolean existTable(String tableName) {
        int i = opqGroupMsgMapper.existTable(tableName);
        return i > 0;
    }

    /**
     * 创建群消息表
     *
     * @param tableName 表名
     * @return 影响的行数
     */
    @Override
    public int createNewGroupMsgTable(String tableName) {
        return opqGroupMsgMapper.createNewGroupMsgTable(tableName);
    }

    /**
     * 插入群聊消息
     *
     * @param opqGroupMsg 群聊消息
     * @return 影响的行数
     */
    @Override
    public int insertGroupMsg(OpqGroupMsg opqGroupMsg) {
        return opqGroupMsgMapper.insertGroupMsg(opqGroupMsg);
    }

    /**
     * 消息统计放入redis
     *
     * @param msg 消息
     * @return 是否放入成功
     */
    @Override
    public boolean insertRedisCacheMsgCount(Msg msg) {
        //得到消息头
        MsgHead msgHead = msg.getMsgHead();
        //得到消息体
        MsgBody msgBody = msg.getMsgBody();
        //创建一个存储群组消息的hashMap
        Map<String, OpqGroupMsgCount> longOpqGroupMsgCountMap = new HashMap<>(16);
        //获取今天的时间戳
        Date date = new Date();
        //创建日期格式化
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        //格式化日期
        String format = simpleDateFormat.format(date);
        Date parse = null;
        try {
            //将日期格式转化为Date格式的
            parse = simpleDateFormat.parse(format);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
        // 如果键值不存在，创建一个新的值
        if (!redisCache.hasKey(GROUP_MSG_COUNT_KEY)) {

            longOpqGroupMsgCountMap.put(msgHead.getGroupInfo().getGroupCode() + "",
                    new OpqGroupMsgCount(msgHead.getGroupInfo().getGroupCode(), 1L, parse, msgHead.getMsgTime()));
            //放入redis中
            redisCache.setCacheMap(GROUP_MSG_COUNT_KEY, longOpqGroupMsgCountMap);
            return true;
        } else {
            //得到redis中存储的数据
            Map<String, OpqGroupMsgCount> cacheMap = redisCache.getCacheMap(GROUP_MSG_COUNT_KEY);
            OpqGroupMsgCount opqGroupMsgCount = cacheMap.get(msgHead.getGroupInfo().getGroupCode() + "");
            if (opqGroupMsgCount == null) {
                cacheMap.put(msgHead.getGroupInfo().getGroupCode() + "",
                        new OpqGroupMsgCount(msgHead.getGroupInfo().getGroupCode(), 1L, parse, msgHead.getMsgTime()));
            } else {
                //设置消息加一
                opqGroupMsgCount.setMsgCount(opqGroupMsgCount.getMsgCount() + 1);
                //设置最后一条消息的时间
                opqGroupMsgCount.setLastSpeakTime(msgHead.getMsgTime());
                //放入map中
                cacheMap.put(msgHead.getGroupInfo().getGroupCode() + "", opqGroupMsgCount);
            }
            //放入redis中
            redisCache.setCacheMap(GROUP_MSG_COUNT_KEY, cacheMap);
            return true;
        }
    }

    /**
     * 得到今天群发言的消息统计
     *
     * @return 群发言统计的列表
     */
    @Override
    public List<OpqGroupMsgCount> getTodayMsgCountList() {
        ArrayList<OpqGroupMsgCount> opqGroupMsgCountArrayList = new ArrayList<>();
        //得到缓存中的数据
        Map<String, OpqGroupMsgCount> cacheMap = redisCache.getCacheMap(GROUP_MSG_COUNT_KEY);
        //遍历数据
        cacheMap.forEach(new BiConsumer<String, OpqGroupMsgCount>() {
            @Override
            public void accept(String s, OpqGroupMsgCount opqGroupMsgCount) {
                opqGroupMsgCountArrayList.add(opqGroupMsgCount);
            }
        });
        if (opqGroupMsgCountArrayList.size() != 0) {
            //插入数据库中
            insertOpqGroupMsgCountList(opqGroupMsgCountArrayList);
        }
        return opqGroupMsgCountArrayList;
    }

    /**
     * 批量插入消息统计至数据库中
     *
     * @return 影响的行数
     */
    @Override
    public int insertOpqGroupMsgCountList(List<OpqGroupMsgCount> opqGroupMsgCountList) {
        return opqGroupMsgCountMapper.insertOpqGroupMsgCountList(opqGroupMsgCountList);
    }

    /**
     * 清除redis中的消息统计数据
     *
     * @return 是否清除成功
     */
    @Override
    public boolean deleteRedisCacheGroupMsgCount() {
        return redisCache.deleteObject(GROUP_MSG_COUNT_KEY);
    }

    /**
     * 反序查询消息列表
     *
     * @param opqGroupMsg 群消息
     * @return 消息列表
     */
    @Override
    public List<OpqGroupMsgDto> selectOpqGroupMsgFlashback(OpqGroupMsg opqGroupMsg) {

        ArrayList<OpqGroupMsgDto> opqGroupMsgDtos = new ArrayList<>();

        //todo 判断该群是否存在消息表

        //查询消息列表
        List<OpqGroupMsg> opqGroupMsgs = opqGroupMsgMapper.selectOpqGroupMsgFlashback(opqGroupMsg);
        //遍历消息列表，并且包装成前端识别的
        for (OpqGroupMsg groupMsg : opqGroupMsgs) {

            OpqGroupMsgDto opqGroupMsgDto = new OpqGroupMsgDto();
            //调用工具类拷贝值
            BeanUtils.copyProperties(groupMsg, opqGroupMsgDto);
            //设置消息的值
            opqGroupMsgDto.setId(opqGroupMsgDto.getMsgId());
            opqGroupMsgDto.setStatus("succeed");
            //todo 判断消息类型
            opqGroupMsgDto.setType("text");
            opqGroupMsgDto.setContent(groupMsg.getContext());
            opqGroupMsgDto.setSendTime(groupMsg.getMsgTime() * 1000);
            opqGroupMsgDto.setToContactId(groupMsg.getGroupUin());
            //构造来自的用户
            ContactDto contactDto = new ContactDto();
            contactDto.setId(groupMsg.getSenderUin());
            contactDto.setDisplayName(groupMsg.getSenderNick());
            contactDto.setAvatar("http://q1.qlogo.cn/g?b=qq&nk=" + groupMsg.getSenderUin() + "&s=640");
            //设置发消息的用户
            opqGroupMsgDto.setFromUser(contactDto);
            //添加到列表中
            opqGroupMsgDtos.add(opqGroupMsgDto);
        }
        //反序列表
        Collections.reverse(opqGroupMsgDtos);
        return opqGroupMsgDtos;
    }
}