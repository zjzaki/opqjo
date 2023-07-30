package com.ruoyi.system.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ruoyi.system.domain.OpqGroup;
import com.ruoyi.system.domain.OpqGroupMember;
import com.ruoyi.system.mapper.OpqGroupMapper;
import com.ruoyi.system.mapper.OpqGroupMemberMapper;
import com.ruoyi.system.service.OpqGroupService;
import com.zjzaki.core.action.Action;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zjzaki
 * @create 2023年04月16日 15:47:00
 */
@Service("opqGroupServiceImpl")
public class OpqGroupServiceImpl implements OpqGroupService {

    @Resource(name = "opqGroupMapper")
    private OpqGroupMapper opqGroupMapper;

    @Resource(name = "opqGroupMemberMapper")
    private OpqGroupMemberMapper opqGroupMemberMapper;

    /**
     * 刷新群组成员信息
     *
     * @param groupUin 群号
     * @return 结果
     */
    @Override
    public int refreshOpqGroupMember(Long groupUin) {
        //将所有已存在的群的状态修改为删除
        opqGroupMapper.updateAllGroupDeleteFlag();
        int code = 0;
        //群组列表
        ArrayList<OpqGroup> opqGroups = new ArrayList<>();
        //请求数据
        String groupListStr = Action.getGroupList();
        //转换为JSON格式
        JSONObject groupJsonObject = JSONObject.parseObject(groupListStr);
        /*
           得到基础请求信息
         */
        JSONObject cgiBaseResponse = groupJsonObject.getJSONObject("CgiBaseResponse");
        //错误信息
        String errMsg = cgiBaseResponse.getString("ErrMsg");
        //状态码
        Integer ret = cgiBaseResponse.getInteger("Ret");
         /*
            得到请求数据
         */
        JSONObject responseData = groupJsonObject.getJSONObject("ResponseData");
        //得到群组列表
        JSONArray groupLists = responseData.getJSONArray("GroupLists");
        //遍历数据
        for (int i = 0; i < groupLists.size(); i++) {
            JSONObject groupJson = groupLists.getJSONObject(i);
            OpqGroup opqGroup = new OpqGroup();
            opqGroup.setGroupCode(groupJson.getLong("GroupCode"));
            opqGroup.setCreateTime(groupJson.getLong("CreateTime"));
            opqGroup.setGroupCnt(groupJson.getInteger("GroupCnt"));
            opqGroup.setMemberCnt(groupJson.getInteger("MemberCnt"));
            opqGroup.setGroupName(groupJson.getString("GroupName"));
            //添加到列表
            opqGroups.add(opqGroup);
        }
        //插入群组信息
        code += opqGroupMapper.insertOpqGroupList(opqGroups);

        // 通过群号将群成员状态修改为删除
        code += opqGroupMemberMapper.updateOpqGroupMemberDeleteFlagByGroupUin(groupUin);
        //插入群成员信息，如果已经存在就修改信息
        code += insertOpqGroupMemberList(groupUin);
        return code;
    }

    /**
     * 刷新群组信息
     *
     * @return 影响的行数
     */
    @Override
    public int refreshOpqGroupList() {
        //将所有已存在的群的状态修改为删除
        opqGroupMapper.updateAllGroupDeleteFlag();
        //将所有已存在的群组成员的状态修改为删除
        opqGroupMemberMapper.updateAllOpqGroupMemberDeleteFlag();
        //状态码
        int code = 0;
        //群组列表
        ArrayList<OpqGroup> opqGroups = new ArrayList<>();
        //请求数据
        String groupListStr = Action.getGroupList();
        //转换为JSON格式
        JSONObject groupJsonObject = JSONObject.parseObject(groupListStr);
        /*
           得到基础请求信息
         */
        JSONObject cgiBaseResponse = groupJsonObject.getJSONObject("CgiBaseResponse");
        //错误信息
        String errMsg = cgiBaseResponse.getString("ErrMsg");
        //状态码
        Integer ret = cgiBaseResponse.getInteger("Ret");
         /*
            得到请求数据
         */
        JSONObject responseData = groupJsonObject.getJSONObject("ResponseData");
        //得到群组列表
        JSONArray groupLists = responseData.getJSONArray("GroupLists");
        //遍历数据
        for (int i = 0; i < groupLists.size(); i++) {
            JSONObject groupJson = groupLists.getJSONObject(i);
            OpqGroup opqGroup = new OpqGroup();
            opqGroup.setGroupCode(groupJson.getLong("GroupCode"));
            opqGroup.setCreateTime(groupJson.getLong("CreateTime"));
            opqGroup.setGroupCnt(groupJson.getInteger("GroupCnt"));
            opqGroup.setMemberCnt(groupJson.getInteger("MemberCnt"));
            opqGroup.setGroupName(groupJson.getString("GroupName"));
            //添加到列表
            opqGroups.add(opqGroup);
        }
        //插入群组信息
        code += opqGroupMapper.insertOpqGroupList(opqGroups);
        //插入群组成员信息
        for (int i = 0; i < groupLists.size(); i++) {
            //得到群号
            Long groupCode = groupLists.getJSONObject(i).getLong("GroupCode");
            //插入信息
            code += insertOpqGroupMemberList(groupCode);
        }
        return code;
    }

    /**
     * 添加群成员信息
     *
     * @param groupUid 群号
     * @return 影响的行数
     */
    @Override
    public int insertOpqGroupMemberList(Long groupUid) {
        //群组列表
        ArrayList<OpqGroupMember> opqGroupMemberArrayList = new ArrayList<>();
        //lastBuffer
        String lastBuffer = "";
        int total = 0;
        //do...while循环请求数据
        do {
            //请求数据
            String groupMemberListStr = Action.getGroupMemberList(groupUid, lastBuffer);
            //转换为JSON格式
            JSONObject groupMemberJsonObject = JSONObject.parseObject(groupMemberListStr);
        /*
           得到基础请求信息
         */
            JSONObject cgiBaseResponse = groupMemberJsonObject.getJSONObject("CgiBaseResponse");
            //错误信息
            String errMsg = cgiBaseResponse.getString("ErrMsg");
            //状态码
            Integer ret = cgiBaseResponse.getInteger("Ret");
            //解析数据
            JSONObject responseData = groupMemberJsonObject.getJSONObject("ResponseData");
            //得到LastBuffer
            lastBuffer = responseData.getString("LastBuffer");

            //得到成员列表
            JSONArray memberListsJsonArray = responseData.getJSONArray("MemberLists");
            if (memberListsJsonArray != null) {
                //遍历信息
                for (int i = 0; i < memberListsJsonArray.size(); i++) {
                    JSONObject memberJsonObject = memberListsJsonArray.getJSONObject(i);
                    OpqGroupMember opqGroupMember = new OpqGroupMember(groupUid);
                    opqGroupMember.setCreditLevel(memberJsonObject.getInteger("CreditLevel"));
                    opqGroupMember.setJoinTime(memberJsonObject.getLong("JoinTime"));
                    opqGroupMember.setLastSpeakTime(memberJsonObject.getLong("LastSpeakTime"));
                    opqGroupMember.setLevel(memberJsonObject.getInteger("Level"));
                    opqGroupMember.setMemberFlag(memberJsonObject.getInteger("MemberFlag"));
                    opqGroupMember.setNick(memberJsonObject.getString("Nick"));
                    opqGroupMember.setMemberUid(memberJsonObject.getString("Uid"));
                    opqGroupMember.setMemberUin(memberJsonObject.getLong("Uin"));
                    //添加到列表
                    opqGroupMemberArrayList.add(opqGroupMember);
                }
            }
            total += 1;
            if (total > 5) {
                 break;
            }
        } while (!"".equals(lastBuffer));
        //返回结果
        return opqGroupMemberMapper.insertOpqGroupMemberList(opqGroupMemberArrayList);
    }

    /**
     * 查询群组
     *
     * @param groupCode 群号
     * @return 群组实体
     */
    @Override
    public OpqGroup selectOpqGroupByGroupCode(Long groupCode) {
        return opqGroupMapper.selectOpqGroupByGroupCode(groupCode);
    }

    /**
     * 查询群组列表
     *
     * @param opqGroup 群组
     * @return 群组列表
     */
    @Override
    public List<OpqGroup> selectOpqGroupList(OpqGroup opqGroup) {
        return opqGroupMapper.selectOpqGroupList(opqGroup);
    }

    /**
     * 插入群组
     *
     * @param opqGroup 群组实体
     * @return 结果
     */
    @Override
    public int insertOpqGroup(OpqGroup opqGroup) {
        return 0;
    }

    /**
     * 修改群组
     *
     * @param opqGroup 群组实体
     * @return 结果
     */
    @Override
    public int updateOpqGroup(OpqGroup opqGroup) {
        return 0;
    }

    /**
     * 删除群组（退群）
     *
     * @param groupCode 群号
     * @return 结果
     */
    @Override
    public int deleteOpqGroupByGroupCode(Long groupCode) {
        return 0;
    }

    /**
     * 批量删除群组
     *
     * @param groupCode 群号
     * @return 结果
     */
    @Override
    public int deleteOpqGroupByGroupCodes(Long[] groupCode) {
        return 0;
    }
}
