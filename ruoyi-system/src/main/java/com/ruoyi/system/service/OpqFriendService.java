package com.ruoyi.system.service;

import com.ruoyi.system.domain.OpqFriend;

import java.util.List;

/**
 * @author zjzaki
 * @create 2023年04月17日 23:18:20
 */
public interface OpqFriendService {

    /**
     * 查询好友列表
     * @param opqFriend 好友实体
     * @return 好友列表
     */
    List<OpqFriend> selectOpqFriendList(OpqFriend opqFriend);
}
