package com.ruoyi.system.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ruoyi.system.domain.OpqFriend;
import com.ruoyi.system.domain.OpqTag;
import com.ruoyi.system.mapper.OpqFriendMapper;
import com.ruoyi.system.mapper.OpqTagMapper;
import com.ruoyi.system.service.OpqTagService;
import com.zjzaki.core.action.Action;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zjzaki
 * @create 2023年04月16日 13:55:08
 */
@Service("opqTagServiceImpl")
public class OpqTagImpl implements OpqTagService {

    @Resource(name = "opqTagMapper")
    private OpqTagMapper opqTagMapper;

    @Resource(name = "opqFriendMapper")
    private OpqFriendMapper opqFriendMapper;

    /**
     * 添加分组
     *
     * @param opqTag 分组信息
     * @return 影响的行数
     */
    @Override
    public int insertTag(OpqTag opqTag) {
        return opqTagMapper.insertTag(opqTag);
    }

    /**
     * 批量添加分组
     *
     * @param opqTagList 分组list
     * @return 影响的行数
     */
    @Override
    public int insertTagList(List<OpqTag> opqTagList) {
        return opqTagMapper.insertTagList(opqTagList);
    }

    /**
     * 刷新分组和好友列表
     *
     * @return 影响的行数
     */
    @Override
    public int refreshTagAndFriendList() {
        //修改好友删除状态
        opqFriendMapper.updateOpqAllFriendDeleteFlag();
        //修改分组删除状态
        opqTagMapper.updateOpqTagDeleteFlag();
        int code = 0;
        //分组列表
        ArrayList<OpqTag> opqTags = new ArrayList<>();
        //好友列表
        ArrayList<OpqFriend> opqFriends = new ArrayList<>();
        //请求opq得到好友和分组信息
        String friendList = Action.getFriendList();
        //将信息转换为JSON格式
        JSONObject friendJsonObject = JSONObject.parseObject(friendList);
        /*
           得到基础请求信息
         */
        JSONObject cgiBaseResponse = friendJsonObject.getJSONObject("CgiBaseResponse");
        //错误信息
        String errMsg = cgiBaseResponse.getString("ErrMsg");
        //状态码
        Integer ret = cgiBaseResponse.getInteger("Ret");
        /*
            得到请求数据
         */
        JSONObject responseData = friendJsonObject.getJSONObject("ResponseData");
        //得到好友列表
        JSONArray friendLists = responseData.getJSONArray("FriendLists");
        //得到分组列表
        JSONArray tagLists = responseData.getJSONArray("TagLists");
        //得到lastBuffer
        String lastBuffer = responseData.getString("LastBuffer");
        //遍历分组列表
        for (int i = 0; i < tagLists.size(); i++) {
            JSONObject tagJsonObject = tagLists.getJSONObject(i);
            OpqTag opqTag = new OpqTag(tagJsonObject.getInteger("TagId"), tagJsonObject.getString("TagName"));
            opqTags.add(opqTag);
        }
        //遍历好友列表
        for (int i = 0; i < friendLists.size(); i++) {
            JSONObject friendJson = friendLists.getJSONObject(i);
            OpqFriend opqFriend = new OpqFriend();
            opqFriend.setUin(friendJson.getLong("Uin"));
            opqFriend.setUid(friendJson.getString("Uid"));
            opqFriend.setHead(friendJson.getString("Head"));
            opqFriend.setNick(friendJson.getString("Nick"));
            opqFriend.setSex(friendJson.getString("Sex"));
            opqFriend.setSignature(friendJson.getString("Signature"));
            opqFriend.setTagId(friendJson.getInteger("TagId"));
            //添加到列表中
            opqFriends.add(opqFriend);
        }
        //添加到数据库中
        code += opqTagMapper.insertTagList(opqTags);
        code += opqFriendMapper.insertFriendList(opqFriends);

        return code;
    }

    /**
     * 通过tagId查询好友分组
     *
     * @param tagId 分组Id
     * @return 好友分组
     */
    @Override
    public OpqTag selectOpqTagByTagId(Long tagId) {
        return null;
    }

    /**
     * 查询好友分组列表
     *
     * @param opqTag 好友分组
     * @return 好友分组集合
     */
    @Override
    public List<OpqTag> selectOpqTagList(OpqTag opqTag) {
        return opqTagMapper.selectTagList(opqTag);
    }

    /**
     * 新增好友分组
     *
     * @param opqTag 好友分组
     * @return 结果
     */
    @Override
    public int insertOpqTag(OpqTag opqTag) {
        return 0;
    }

    /**
     * 修改好友分组
     *
     * @param opqTag 好友分组
     * @return 结果
     */
    @Override
    public int updateOpqTag(OpqTag opqTag) {
        return 0;
    }

    /**
     * 删除好友分组
     *
     * @param tagId 好友分组主键
     * @return 结果
     */
    @Override
    public int deleteOpqTagByTagId(Long tagId) {
        return 0;
    }

    /**
     * 批量删除好友分组
     *
     * @param tagIds 需要删除的数据主键集合
     * @return 结果
     */
    @Override
    public int deleteOpqTagByTagIds(Long[] tagIds) {
        return 0;
    }
}
