package com.ruoyi.system.mapper;

import com.ruoyi.system.domain.OpqGroup;
import com.ruoyi.system.domain.OpqGroupMember;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 群组Mapper接口
 *
 * @author zjzaki
 * @create 2023年04月16日 15:40:48
 */
@Repository("opqGroupMapper")
public interface OpqGroupMapper {

    /**
     * 批量添加群至数据库中
     *
     * @param opqGroupList 群列表
     * @return 影响的行数
     */
    int insertOpqGroupList(List<OpqGroup> opqGroupList);


    /**
     * 查询群组
     *
     * @param groupCode 群号
     * @return 群组实体
     */
    OpqGroup selectOpqGroupByGroupCode(Long groupCode);

    /**
     * 查询群组列表
     *
     * @param opqGroup 群组
     * @return 群组列表
     */
    List<OpqGroup> selectOpqGroupList(OpqGroup opqGroup);

    /**
     * 插入群组
     *
     * @param opqGroup 群组实体
     * @return 结果
     */
    int insertOpqGroup(OpqGroup opqGroup);

    /**
     * 修改群组
     *
     * @param opqGroup 群组实体
     * @return 结果
     */
    int updateOpqGroup(OpqGroup opqGroup);

    /**
     * 删除群组（退群）
     *
     * @param groupCode 群号
     * @return 结果
     */
    int deleteOpqGroupByGroupCode(Long groupCode);

    /**
     * 批量删除群组
     *
     * @param groupCode 群号
     * @return 结果
     */
    int deleteOpqGroupByGroupCodes(Long[] groupCode);

    /**
     * 修改群组状态为删除
     *
     * @return
     */
    int updateAllGroupDeleteFlag();
}
