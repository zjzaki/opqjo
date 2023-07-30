package com.ruoyi.system.service.impl;

import com.ruoyi.system.domain.OpqGroupMember;
import com.ruoyi.system.mapper.OpqGroupMemberMapper;
import com.ruoyi.system.service.OpqGroupMemberService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zjzaki
 * @create 2023年04月18日 14:34:15
 */
@Service("opqGroupMemberServiceImpl")
public class OpqGroupMemberServiceImpl implements OpqGroupMemberService {

    @Resource(name = "opqGroupMemberMapper")
    private OpqGroupMemberMapper opqGroupMemberMapper;
    /**
     * 查询群成员列表
     *
     * @param opqGroupMember 群成员
     * @return 群成员列表
     */
    @Override
    public List<OpqGroupMember> selectOpqGroupMemberList(OpqGroupMember opqGroupMember) {

        return opqGroupMemberMapper.selectOpqGroupMemberList(opqGroupMember);
    }

    /**
     * 修改群聊成员最后发言时间
     *
     * @param opqGroupMember 群聊成员
     * @return 影响的行数
     */
    @Override
    public int updateOpqGroupMemberLastSpeakTime(OpqGroupMember opqGroupMember) {
        return opqGroupMemberMapper.updateOpqGroupMemberLastSpeakTime(opqGroupMember);
    }
}
