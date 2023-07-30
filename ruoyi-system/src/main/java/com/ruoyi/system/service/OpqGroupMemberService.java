package com.ruoyi.system.service;

import com.ruoyi.system.domain.OpqGroupMember;

import java.util.List;

/**
 * @author zjzaki
 * @create 2023年04月18日 14:33:10
 */
public interface OpqGroupMemberService {

    /**
     * 查询群成员列表
     * @param opqGroupMember 群成员
     * @return 群成员列表
     */
    List<OpqGroupMember> selectOpqGroupMemberList(OpqGroupMember opqGroupMember);

    /**
     * 修改群聊成员最后发言时间
     * @param opqGroupMember 群聊成员
     * @return 影响的行数
     */
    int updateOpqGroupMemberLastSpeakTime(OpqGroupMember opqGroupMember);
}
