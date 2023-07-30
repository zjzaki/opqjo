package com.ruoyi.system.service.impl;

import com.ruoyi.system.domain.OpqFriend;
import com.ruoyi.system.mapper.OpqFriendMapper;
import com.ruoyi.system.service.OpqFriendService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zjzaki
 * @create 2023年04月17日 23:20:31
 */
@Service("opqFriendServiceImpl")
public class OpqFriendServiceImpl implements OpqFriendService {

    @Resource(name = "opqFriendMapper")
    private OpqFriendMapper opqFriendMapper;
    /**
     * 查询好友列表
     *
     * @param opqFriend 好友实体
     * @return 好友列表
     */
    @Override
    public List<OpqFriend> selectOpqFriendList(OpqFriend opqFriend) {
        return opqFriendMapper.selectOpqFriendList(opqFriend);
    }
}
