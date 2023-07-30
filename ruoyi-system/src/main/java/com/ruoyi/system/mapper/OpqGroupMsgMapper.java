package com.ruoyi.system.mapper;

import com.ruoyi.system.domain.OpqGroupMsg;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author zjzaki
 * @create 2023年04月16日 20:50:07
 */
@Repository("opqGroupMsgMapper")
public interface OpqGroupMsgMapper {

    /**
     * 插入群消息
     * @param opqGroupMsg 群消息实体
     * @return 影响的行数
     */
    int insertGroupMsg(OpqGroupMsg opqGroupMsg);

    /**
     * 查询表是否存在
     * @param tableName 表名
     * @return 查到的总结果
     */
    int existTable(@Param("tableName") String tableName);

    /**
     * 创建群组消息新表
     * @param tableName 表名
     * @return 影响的行数
     */
    int createNewGroupMsgTable(@Param("tableName") String tableName);

    /**
     * 查询群组消息列表
     * @param opqGroupMsg 群组消息
     * @return 群组消息列表
     */
    List<OpqGroupMsg> selectOpqGroupMsgFlashback(OpqGroupMsg opqGroupMsg);
}
