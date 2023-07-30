package com.ruoyi.system.mapper;

import com.ruoyi.system.domain.OpqFriend;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author zjzaki
 * @create 2023年04月16日 15:02:51
 */
@Repository("opqFriendMapper")
public interface OpqFriendMapper {

    /**
     * 批量添加好友
     * @param opqFriendList 好友列表
     * @return 影响的行数
     */
    int insertFriendList(List<OpqFriend> opqFriendList);

    /**
     * 查询好友列表
     * @param opqFriend 好友实体
     * @return 好友列表实体
     */
    List<OpqFriend> selectOpqFriendList(OpqFriend opqFriend);

    /**
     * 修改所有好友删除状态 为删除
     * @return 影响的行数
     */
    int updateOpqAllFriendDeleteFlag();
}
