package com.ruoyi.system.service;

import com.ruoyi.system.domain.OpqTag;

import java.util.List;

/**
 * @author zjzaki
 * @create 2023年04月15日 17:09:29
 */
public interface OpqTagService {

    /**
     * 添加分组
     * @param opqTag 分组信息
     * @return 影响的行数
     */
    int insertTag(OpqTag opqTag);

    /**
     * 批量添加分组
     * @param opqTagList 分组list
     * @return 影响的行数
     */
    int insertTagList(List<OpqTag> opqTagList);

    /**
     * 刷新分组和好友列表
     * @return 影响的行数
     */
    int refreshTagAndFriendList();

    /**
     * 通过tagId查询好友分组
     * @param tagId 分组Id
     * @return 好友分组
     */
    OpqTag selectOpqTagByTagId(Long tagId);

    /**
     * 查询好友分组列表
     * @param opqTag 好友分组
     * @return 好友分组集合
     */
    List<OpqTag> selectOpqTagList(OpqTag opqTag);

    /**
     * 新增好友分组
     * @param opqTag 好友分组
     * @return 结果
     */
    int insertOpqTag(OpqTag opqTag);

    /**
     * 修改好友分组
     *
     * @param opqTag 好友分组
     * @return 结果
     */
    int updateOpqTag(OpqTag opqTag);

    /**
     * 删除好友分组
     *
     * @param tagId 好友分组主键
     * @return 结果
     */
    int deleteOpqTagByTagId(Long tagId);

    /**
     * 批量删除好友分组
     *
     * @param tagIds 需要删除的数据主键集合
     * @return 结果
     */
    int deleteOpqTagByTagIds(Long[] tagIds);
}
