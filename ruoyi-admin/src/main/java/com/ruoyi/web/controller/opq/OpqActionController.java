package com.ruoyi.web.controller.opq;

import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.system.service.OpqActionService;
import com.ruoyi.system.service.impl.OpqActionServiceImpl;
import com.zjzaki.core.pojo.msg.AtUin;
import com.zjzaki.core.pojo.sendMsg.CgiRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;

/**
 * @author zjzaki
 * @create 2023年04月19日 10:31:28
 */
@RestController
@RequestMapping("/opq/action")
public class OpqActionController extends BaseController {

    @Resource(name = "opqActionServiceImpl")
    private OpqActionService opqActionService;

    private static final Logger log = LoggerFactory.getLogger(OpqActionController.class);

    /**
     * 发送成功代码
     */
    private static final Integer SEND_SUCCESS = 0;

    private static final String CGI_BASE_RESPONSE = "CgiBaseResponse";

    private static final String RESPONSE_DATA = "ResponseData";

    //=====================================================发送相关=======================================================

    /**
     * 发送好友文本消息
     *
     * @param cgiRequest 发消息实体
     * @return 结果
     */
    @PreAuthorize("@ss.hasPermi('opq:action:friend:text')")
    @PostMapping("sendFriendText")
    public AjaxResult sendFriendText(@RequestBody CgiRequest cgiRequest) {
        return sendResult(opqActionService.sendFriendText(cgiRequest));
    }

    /**
     * 发送群组文本消息
     *
     * @param cgiRequest 发消息实体
     * @return 结果
     */
    @PreAuthorize("@ss.hasPermi('opq:action:group:text')")
    @PostMapping("sendGroupText")
    public AjaxResult sendGroupText(@RequestBody CgiRequest cgiRequest) {
        System.out.println(JSONObject.toJSONString(cgiRequest));
        return sendResult(opqActionService.sendGroupText(cgiRequest));
    }

    /**
     * 发送群组图片
     *
     * @param cgiRequest 发消息实体
     * @return 结果
     */
    @PreAuthorize("@ss.hasPermi('opq:action:group:pic')")
    @PostMapping("sendGroupPic")
    public AjaxResult sendGroupPic(@RequestBody CgiRequest cgiRequest) {
        System.out.println(JSONObject.toJSONString(cgiRequest));
        return sendResult(opqActionService.sendGroupPic(cgiRequest));
    }

    //=====================================================群组操作相关=======================================================

    /**
     * 禁言群组成员
     * @param cgiRequest 发消息实体
     * @return 结果
     */
    @PostMapping("banTimeGroupMember")
    public AjaxResult banTimeGroupMember(@RequestBody CgiRequest cgiRequest) {
        //{"CgiBaseResponse":{"ErrMsg":"","Ret":0},"ResponseData":{"Status":"success"}}
        return sendActionResult(opqActionService.banTimeGroupMember(cgiRequest));
    }

    /**
     * 得到操作的结果
     *
     * @param jsonObject
     * @return
     */
    private AjaxResult sendActionResult(JSONObject jsonObject) {
        if (SEND_SUCCESS.equals(jsonObject.getJSONObject(CGI_BASE_RESPONSE).getInteger("Ret"))) {
            return success("操作成功");
        } else {
            return error("发生错误：" + jsonObject.getJSONObject(CGI_BASE_RESPONSE).getString("ErrMsg"));
        }
    }

    /**
     * 得到发送结果
     *
     * @param jsonObject Json实体
     * @return 结果
     */
    private AjaxResult sendResult(JSONObject jsonObject) {
        if (SEND_SUCCESS.equals(jsonObject.getJSONObject(CGI_BASE_RESPONSE).getInteger("Ret"))) {
            return success("发送成功");
        } else {
            return error("发生错误：" + jsonObject.getJSONObject(CGI_BASE_RESPONSE).getString("ErrMsg"));
        }
    }
}
