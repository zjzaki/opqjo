package com.ruoyi.system.service;

import com.ruoyi.system.domain.OpqGroup;

import java.util.List;

/**
 * 群聊Service接口
 * @author zjzaki
 * @create 2023年04月16日 15:45:52
 */
public interface OpqGroupService {

    /**
     * 刷新群组成员信息
     * @param groupUin 群号
     * @return 结果
     */
    int refreshOpqGroupMember(Long groupUin);

    /**
     * 刷新群组信息
     * @return 影响的行数
     */
    int refreshOpqGroupList();

    /**
     * 添加群成员信息
     * @param groupUid 群号
     * @return 影响的行数
     */
    int insertOpqGroupMemberList(Long groupUid);

    /**
     * 查询群组
     * @param groupCode 群号
     * @return 群组实体
     */
    OpqGroup selectOpqGroupByGroupCode(Long groupCode);

    /**
     * 查询群组列表
     * @param opqGroup 群组
     * @return 群组列表
     */
    List<OpqGroup> selectOpqGroupList(OpqGroup opqGroup);

    /**
     * 插入群组
     * @param opqGroup 群组实体
     * @return 结果
     */
    int insertOpqGroup(OpqGroup opqGroup);

    /**
     * 修改群组
     * @param opqGroup 群组实体
     * @return 结果
     */
    int updateOpqGroup(OpqGroup opqGroup);

    /**
     * 删除群组（退群）
     * @param groupCode 群号
     * @return 结果
     */
    int deleteOpqGroupByGroupCode(Long groupCode);

    /**
     * 批量删除群组
     * @param groupCode 群号
     * @return 结果
     */
    int deleteOpqGroupByGroupCodes(Long[] groupCode);
}
