package com.ruoyi.system.mapper;

import com.ruoyi.system.domain.OpqTag;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author zjzaki
 * @create 2023年04月15日 15:40:42
 */
@Repository("opqTagMapper")
public interface OpqTagMapper {

    /**
     * 添加OpqTag
     *
     * @param opqTag 实体
     * @return 影响的行数
     */
    int insertTag(OpqTag opqTag);

    /**
     * 批量插入分组
     *
     * @param opqTagList 分组列表
     * @return 影响的行数
     */
    int insertTagList(List<OpqTag> opqTagList);

    /**
     * 查询好友分组
     *
     * @param tagId 分组id
     * @return 结果
     */
    OpqTag selectOpqTagByTagId(Long tagId);

    /**
     * 查询好友分组列表
     *
     * @param opqTag 好友分组实体
     * @return 好友分组集合
     */
    List<OpqTag> selectTagList(OpqTag opqTag);

    /**
     * 新增好友分组
     *
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

    /**
     * 修改分组删除状态为删除
     * @return 影响的行数
     */
    int updateOpqTagDeleteFlag();
}
