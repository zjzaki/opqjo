package com.ruoyi.system.mapper;

import com.ruoyi.system.domain.OpqGroupMember;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author zjzaki
 * @create 2023年04月18日 14:23:36
 */
@Repository("opqGroupMemberMapper")
public interface OpqGroupMemberMapper {

    /**
     * 查询群组成员列表
     * @param opqGroupMember 群成员实体
     * @return 群成员列表
     */
    List<OpqGroupMember> selectOpqGroupMemberList(OpqGroupMember opqGroupMember);

    /**
     * 添加群成员至数据库中
     * @param opqGroupMemberList 群成员列表
     * @return 影响的行数
     */
    int insertOpqGroupMemberList(List<OpqGroupMember> opqGroupMemberList);

    /**
     * 修改所有的群的状态为删除
     * @return 影响的行数
     */
    int updateAllOpqGroupMemberDeleteFlag();

    /**
     * 通过群聊信息修改数据
     * @param groupUin qq群号码
     * @return 影响的行数
     */
    int updateOpqGroupMemberDeleteFlagByGroupUin(Long groupUin);

    /**
     * 修改群成员最后发言时间
     * @return 影响的行数
     */
    int updateOpqGroupMemberLastSpeakTime(OpqGroupMember opqGroupMember);
}
