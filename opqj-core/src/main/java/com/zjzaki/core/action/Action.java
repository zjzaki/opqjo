package com.zjzaki.core.action;

import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.zjzaki.core.pojo.Bot;
import com.zjzaki.core.pojo.FileCommand;
import com.zjzaki.core.pojo.FileType;
import com.zjzaki.core.pojo.SendMsg;
import com.zjzaki.core.pojo.msg.AtUin;
import com.zjzaki.core.pojo.msg.Image;
import com.zjzaki.core.pojo.sendMsg.CgiRequest;
import com.zjzaki.core.pojo.sendMsg.Voice;
import com.zjzaki.core.utils.PropertiesUtil;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


/**
 * 发送消息
 *
 * @author zjzaki
 * @create 2023年04月07日 08:40:04
 */
public class Action {

    private static RedisCache redisCache;

    private static String result = "";
    /**
     * 可关闭的HttpClient
     */
    private static CloseableHttpClient httpClient;
    /**
     * 可关闭的httpResponse
     */
    private static CloseableHttpResponse response = null;
    /**
     * bot的qq号
     */
    private static Long botId;
    /**
     * OPQBot本体IP地址
     */
    private static String ip;
    /**
     * OPQBot的端口
     */
    private static int port;

    /**
     * 每分钟每群的最大发言条数
     */
    private static int maxSpeechesPerMinute;
    /**
     * 发送消息格式的JSON对象
     */
    private static JSONObject msgJsonObject = new JSONObject();

    static {
        //创建httpClient对象
        httpClient = HttpClients.createDefault();
        //获得配置文件中的信息
        Bot bot = PropertiesUtil.getBot();
        //设置数据
        ip = bot.getIp();
        port = bot.getPort();
        botId = bot.getBotId();
        maxSpeechesPerMinute = bot.getMaxSpeechesPerMinute();
        redisCache = SpringUtils.getBean("redisCache");
    }

    /**
     * 发送post请求，调用本体API接口发送消息
     *
     * @param bot      bot实体
     * @param funcName 方法名
     * @param msgJson  JSON格式消息对象
     * @return 返回的结果
     */
    public static String post(Bot bot, String funcName, JSONObject msgJson) {
        //效验是否超出
        boolean b = checkSpeechComments(msgJson);
        if (b) {
            return "超出发言频率限制";
        }
        try {

            String postUrl = "http://" + bot.getIp() + ":" + bot.getPort() + "/v1/LuaApiCaller?qq=" + bot.getBotId()
                    + "&funcname=" + funcName + "&timeout=10";
            //创建http请求
            HttpPost httpPost = new HttpPost(postUrl);
            httpPost.addHeader("Content-type", "application/json;");
            StringEntity entity = new StringEntity(JSONObject.toJSONString(msgJson), ContentType.APPLICATION_JSON);
            httpPost.setEntity(entity);
            response = httpClient.execute(httpPost);

            result = EntityUtils.toString(response.getEntity(), "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    /**
     * 机器人发送控制
     *
     * @param msgJson 发送消息JSON实体
     * @return 是否超出限制, true超出, false没有超出
     */
    public static boolean checkSpeechComments(JSONObject msgJson) {
        //判断是不是发送消息接口
        if (!"MessageSvc.PbSendMsg".equals(msgJson.getString("CgiCmd"))) {
            return false;
        }
        //获取群号信息
        Long groupCode = msgJson.getJSONObject("CgiRequest").getLong("ToUin");
        //定义键值
        String speechCommentsKey = "speech_comments:" + groupCode;
        //判断键值是否存在
        if (!redisCache.hasKey(speechCommentsKey)) {
            //不存在,创建新的
            redisCache.setCacheObject(speechCommentsKey, 1, 1, TimeUnit.MINUTES);
            return false;
        } else {
            //获取键的有效时间,如果等于-1的话表示过期,将计数置1,设置有效时间一分钟
            if (redisCache.getExpire(speechCommentsKey) == -1) {
                redisCache.setCacheObject(speechCommentsKey, 1, 1, TimeUnit.MINUTES);
                return false;
            }
            //得到存储的值
            Integer count = redisCache.getCacheObject(speechCommentsKey);
            //更新计数
            redisCache.setCacheObject(speechCommentsKey, ++count, (int) redisCache.getExpire(speechCommentsKey) + 1, TimeUnit.SECONDS);
            if (count > maxSpeechesPerMinute) {
                return true;
            } else {
                return false;
            }
        }
    }

    /**
     * 判断传入的bot值
     *
     * @param bot 传入的bot实体
     * @return bot实体
     */
    private static Bot checkBot(Bot bot) {
        Bot tempBot = new Bot();
        if (bot == null) {
            tempBot.setIp(ip);
            tempBot.setPort(port);
            tempBot.setBotId(botId);
        } else {
            tempBot = bot;
            if ("".equals(bot.getIp())) {
                tempBot.setIp(ip);
            }

            if (bot.getPort() == 0) {
                tempBot.setPort(port);
            }

            if (bot.getBotId() == 0) {
                tempBot.setBotId(botId);
            }
        }
        return tempBot;
    }

    private static Bot checkBot() {
        Bot tempBot = new Bot();
        tempBot.setIp(ip);
        tempBot.setPort(port);
        tempBot.setBotId(botId);
        return tempBot;
    }

    /**
     * 构造请求JSON对象
     *
     * @param sendMsg 发送消息实体
     * @return Json对象
     */
    private static JSONObject buildMsgJsonObject(SendMsg sendMsg) {
        return (JSONObject) JSONObject.toJSON(sendMsg);
    }

    //=======================================================好友相关=====================================================

    /**
     * 发送好友文本消息
     *
     * @param cgiRequest 发送实体
     * @return 返回结果
     */
    public static String sendFriendText(CgiRequest cgiRequest) {
        SendMsg sendMsg = new SendMsg();
        sendMsg.setCgiCmd("MessageSvc.PbSendMsg");
        //发送类型 1好友
        cgiRequest.setToType(1);
        sendMsg.setCgiRequest(cgiRequest);
        return post(checkBot(), "MagicCgiCmd", buildMsgJsonObject(sendMsg));
    }

    /**
     * 发送好友文本消息
     *
     * @param toUin   要发送的好友
     * @param content 文本内容
     * @return 返回结果
     */
    public static String sendFriendText(Long toUin, String content) {
        SendMsg sendMsg = new SendMsg();
        sendMsg.setCgiCmd("MessageSvc.PbSendMsg");
        CgiRequest cgiRequest = new CgiRequest();
        //要发送的人
        cgiRequest.setToUin(toUin);
        //发送类型 1好友
        cgiRequest.setToType(1);
        //要发送的消息
        cgiRequest.setContent(content);
        sendMsg.setCgiRequest(cgiRequest);
        return post(checkBot(), "MagicCgiCmd", buildMsgJsonObject(sendMsg));
    }

    public static String sendFriendVoice() {
        return "";
    }

    //=======================================================群聊相关=====================================================

    /**
     * 发送群组文本消息
     *
     * @param cgiRequest 发消息实体
     * @return 结果
     */
    public static String sendGroupText(CgiRequest cgiRequest) {
        SendMsg sendMsg = new SendMsg();
        sendMsg.setCgiCmd("MessageSvc.PbSendMsg");
        cgiRequest.setToType(2);
        sendMsg.setCgiRequest(cgiRequest);
        return post(checkBot(), "MagicCgiCmd", buildMsgJsonObject(sendMsg));
    }

    /**
     * 发送群组文本消息
     *
     * @param group   发送的群号
     * @param content 发送的消息
     * @param atUser  需要at的群成员,可省
     * @return result
     */
    public static String sendGroupText(Long group, String content, Long... atUser) {
        return sendGroupText(null, group, content, atUser);
    }

    /**
     * 发送群组文本消息
     *
     * @param group   发送的群号
     * @param content 发送的消息
     * @param atUser  需要at的群成员,可省
     * @return result
     */
    public static String sendGroupText(Bot bot, Long group, String content, Long... atUser) {

        SendMsg sendMsg = new SendMsg();
        sendMsg.setCgiCmd("MessageSvc.PbSendMsg");
        CgiRequest cgiRequest = new CgiRequest();
        //要发送的群
        cgiRequest.setToUin(group);
        //发送类型 2群组
        cgiRequest.setToType(2);
        //要发送的消息
        cgiRequest.setContent(content);
        /*
          要At的人
         */
        List<AtUin> atUinList = new ArrayList<>();

        for (int i = 0; i < atUser.length; i++) {
            atUinList.add(new AtUin(atUser[i], "0.0"));
        }
        cgiRequest.setAtUinLists(atUinList);
        sendMsg.setCgiRequest(cgiRequest);
        bot = checkBot(bot);

        return post(bot, "MagicCgiCmd", buildMsgJsonObject(sendMsg));
    }

    /**
     * 发送群组图片消息
     *
     * @param cgiRequest 发消息实体
     * @return 结果
     */
    public static String sendGroupPic(CgiRequest cgiRequest) {
        //图片资源地址
        String fileStr = "";
        if (cgiRequest.getBase64Buf() != null && !"".equals(cgiRequest.getBase64Buf())) {
            //上传资源
            fileStr = uploadFile(FileCommand.GROUP_PIC, FileType.BASE_64_BUF, cgiRequest.getBase64Buf());
        } else if (cgiRequest.getFilePath() != null && !"".equals(cgiRequest.getFilePath())) {
            //上传资源
            fileStr = uploadFile(FileCommand.GROUP_PIC, FileType.FILE_PATH, cgiRequest.getFilePath());
        } else if (cgiRequest.getFileUrl() != null && !"".equals(cgiRequest.getFileUrl())) {
            fileStr = uploadFile(FileCommand.GROUP_PIC, FileType.FILE_URL, cgiRequest.getFileUrl());
        }
        //解析数据
        JSONObject jsonObject = JSONObject.parseObject(fileStr);
        JSONObject responseData = jsonObject.getJSONObject("ResponseData");
        Image image = responseData.toJavaObject(Image.class);
        SendMsg sendMsg = new SendMsg();
        sendMsg.setCgiCmd("MessageSvc.PbSendMsg");
        cgiRequest.setToType(2);
        ArrayList<Image> images = new ArrayList<>();
        images.add(image);
        cgiRequest.setImages(images);
        sendMsg.setCgiRequest(cgiRequest);
        return post(checkBot(), "MagicCgiCmd", buildMsgJsonObject(sendMsg));
    }

    /**
     * 发送群组语音消息
     *
     * @param group    群号
     * @param fileType 文件来源类型
     * @param url      地址
     * @return 结果
     */
    public static String sendGroupPic(Long group, FileType fileType, String url) {
        //上传资源
        String fileStr = uploadFile(FileCommand.GROUP_PIC, fileType, url);
        //解析数据
        JSONObject jsonObject = JSONObject.parseObject(fileStr);
        JSONObject responseData = jsonObject.getJSONObject("ResponseData");
        Image image = responseData.toJavaObject(Image.class);
        SendMsg sendMsg = new SendMsg();
        sendMsg.setCgiCmd("MessageSvc.PbSendMsg");
        CgiRequest cgiRequest = new CgiRequest();
        cgiRequest.setToUin(group);
        cgiRequest.setToType(2);
        ArrayList<Image> images = new ArrayList<>();
        images.add(image);
        cgiRequest.setImages(images);

        sendMsg.setCgiRequest(cgiRequest);
        return post(checkBot(), "MagicCgiCmd", buildMsgJsonObject(sendMsg));
    }


    /**
     * 发送图文混合消息
     *
     * @param group    群号
     * @param fileType 文件来源类型
     * @param url      地址
     * @param content  文本消息内容
     * @param atUser   要at的群成员
     * @return 返回的结果
     */
    public static String sendGroupPic(Long group, FileType fileType, String url, String content, Long... atUser) {
        //上传资源
        String fileStr = uploadFile(FileCommand.GROUP_PIC, fileType, url);
        //解析数据
        JSONObject jsonObject = JSONObject.parseObject(fileStr);
        JSONObject responseData = jsonObject.getJSONObject("ResponseData");
        Image image = responseData.toJavaObject(Image.class);
        SendMsg sendMsg = new SendMsg();
        sendMsg.setCgiCmd("MessageSvc.PbSendMsg");
        CgiRequest cgiRequest = new CgiRequest();
        cgiRequest.setToUin(group);
        cgiRequest.setToType(2);
        //图片消息
        ArrayList<Image> images = new ArrayList<>();
        images.add(image);
        cgiRequest.setImages(images);
        //要发送的文字消息内容
        cgiRequest.setContent(content);
        /*
          要At的人
         */
        List<AtUin> atUinList = new ArrayList<>();

        for (int i = 0; i < atUser.length; i++) {
            atUinList.add(new AtUin(atUser[i], "0.0"));
        }
        cgiRequest.setAtUinLists(atUinList);

        sendMsg.setCgiRequest(cgiRequest);
        return post(checkBot(), "MagicCgiCmd", buildMsgJsonObject(sendMsg));
    }

    /**
     * 发送群聊语音消息
     *
     * @param group    群聊
     * @param fileType 文件来源类型
     * @param url      文件路径
     * @return 结果
     */
    public static String sendGroupVoice(Long group, FileType fileType, String url) {
        //上传资源
        String fileStr = uploadFile(FileCommand.GROUP_VOICE, fileType, url);
        //解析数据
        JSONObject jsonObject = JSONObject.parseObject(fileStr);
        JSONObject responseData = jsonObject.getJSONObject("ResponseData");
        Voice voice = responseData.toJavaObject(Voice.class);
        SendMsg sendMsg = new SendMsg();
        sendMsg.setCgiCmd("MessageSvc.PbSendMsg");
        CgiRequest cgiRequest = new CgiRequest();
        cgiRequest.setToUin(group);
        cgiRequest.setToType(2);
        cgiRequest.setVoice(voice);
        sendMsg.setCgiRequest(cgiRequest);
        return post(checkBot(), "MagicCgiCmd", buildMsgJsonObject(sendMsg));
    }

    //======================================================私聊相关======================================================

    /**
     * 发送私聊文本消息
     *
     * @param toUin     要发送的人
     * @param groupCode 发送的群号
     * @param content   消息
     * @return 返回的结果
     */
    public static String sendPrivateText(Long toUin, Long groupCode, String content) {
        SendMsg sendMsg = new SendMsg();
        sendMsg.setCgiCmd("MessageSvc.PbSendMsg");
        CgiRequest cgiRequest = new CgiRequest();
        cgiRequest.setToUin(toUin);
        cgiRequest.setGroupCode(groupCode);
        cgiRequest.setToType(3);
        cgiRequest.setContent(content);
        sendMsg.setCgiRequest(cgiRequest);
        return post(checkBot(), "MagicCgiCmd", buildMsgJsonObject(sendMsg));
    }

    //======================================================群组操作相关==================================================

    /**
     * 禁言群组成员
     *
     * @param uin     群号
     * @param uid     用户的uid
     * @param banTime 禁言时间 单位：s
     * @return 返回的结果
     */
    public static String banTimeGroupMember(Long uin, String uid, Integer banTime) {
        SendMsg sendMsg = new SendMsg();
        sendMsg.setCgiCmd("SsoGroup.Op");
        CgiRequest cgiRequest = new CgiRequest();
        cgiRequest.setOpCode(4691);
        cgiRequest.setUin(uin);
        cgiRequest.setUid(uid);
        cgiRequest.setBanTime(banTime);
        sendMsg.setCgiRequest(cgiRequest);

        return post(checkBot(), "MagicCgiCmd", buildMsgJsonObject(sendMsg));
    }

    /**
     * 禁言群组成员
     *
     * @param cgiRequest 发消息实体
     * @return 返回的结果
     */
    public static String banTimeGroupMember(CgiRequest cgiRequest) {
        SendMsg sendMsg = new SendMsg();
        sendMsg.setCgiCmd("SsoGroup.Op");
        cgiRequest.setOpCode(4691);
        sendMsg.setCgiRequest(cgiRequest);
        return post(checkBot(), "MagicCgiCmd", buildMsgJsonObject(sendMsg));
    }

    /**
     * 提出群聊
     *
     * @param uin 群号
     * @param uid 成员uid
     * @return 返回的结果
     */
    public static String removeGroupMember(Long uin, String uid) {
        SendMsg sendMsg = new SendMsg();
        sendMsg.setCgiCmd("SsoGroup.Op");
        CgiRequest cgiRequest = new CgiRequest();
        cgiRequest.setOpCode(2208);
        cgiRequest.setUin(uin);
        cgiRequest.setUid(uid);
        sendMsg.setCgiRequest(cgiRequest);
        return post(checkBot(), "MagicCgiCmd", buildMsgJsonObject(sendMsg));
    }

    /**
     * 踢出群聊
     *
     * @param cgiRequest 发消息实体
     * @return 返回的结果
     */
    public static String removeGroupMember(CgiRequest cgiRequest) {
        SendMsg sendMsg = new SendMsg();
        sendMsg.setCgiCmd("SsoGroup.Op");
        cgiRequest.setOpCode(2208);
        sendMsg.setCgiRequest(cgiRequest);
        return post(checkBot(), "MagicCgiCmd", buildMsgJsonObject(sendMsg));
    }

    /**
     * 撤回群成员消息
     *
     * @param uin       群号
     * @param msgSeq    消息序列
     * @param msgRandom 消息随机码
     * @return 返回的结果
     */
    public static String revokeGroupMsg(Long uin, Long msgSeq, Long msgRandom) {
        SendMsg sendMsg = new SendMsg();
        sendMsg.setCgiCmd("GroupRevokeMsg");
        CgiRequest cgiRequest = new CgiRequest();
        cgiRequest.setUin(uin);
        cgiRequest.setMsgSeq(msgSeq);
        cgiRequest.setMsgRandom(msgRandom);
        sendMsg.setCgiRequest(cgiRequest);
        return post(checkBot(), "MagicCgiCmd", buildMsgJsonObject(sendMsg));
    }

    /**
     * 修改群成员昵称
     *
     * @param uin  群号
     * @param uid  群成员uid，字符串
     * @param nick 要修改成的群聊昵称
     * @return
     */
    public static String updateGroupUserNickname(Long uin, String uid, String nick) {

        SendMsg sendMsg = new SendMsg();
        sendMsg.setCgiCmd("SsoGroup.Op");
        CgiRequest cgiRequest = new CgiRequest();
        cgiRequest.setOpCode(2300);
        cgiRequest.setUin(uin);
        cgiRequest.setUid(uid);
        cgiRequest.setNick(nick);
        sendMsg.setCgiRequest(cgiRequest);
        return post(checkBot(), "MagicCgiCmd", buildMsgJsonObject(sendMsg));
    }

    //======================================================获取信息相关==================================================

    /**
     * 获取好友列表
     *
     * @return 好友列表JSON格式
     */
    public static String getFriendList() {
        //清除已有的json数据
        msgJsonObject.clear();
        //填入CgiCmd
        msgJsonObject.put("CgiCmd", "GetFriendLists");
        //构建CgiRequest
        JSONObject cgiRequest = new JSONObject();
        //设置LastUin的值
        cgiRequest.put("LastUin", 0);
        msgJsonObject.put("CgiRequest", cgiRequest);
        Bot bot = checkBot(null);
        return post(bot, "MagicCgiCmd", msgJsonObject);
    }

    /**
     * 获取好友列表
     *
     * @param botId 机器人Id
     * @return 好友列表JSON格式
     */
    public static String getFriendList(String botId) {
        //清除已有的json数据
        msgJsonObject.clear();
        //填入CgiCmd
        msgJsonObject.put("CgiCmd", "GetFriendLists");
        //构建CgiRequest
        JSONObject cgiRequest = new JSONObject();
        //设置LastUin的值
        cgiRequest.put("LastUin", 0);
        msgJsonObject.put("CgiRequest", cgiRequest);
        Bot bot = checkBot(null);
        return post(bot, "MagicCgiCmd", msgJsonObject);
    }

    /**
     * 获取群组列表
     *
     * @return 得到的请求信息
     */
    public static String getGroupList() {
        //清除已有的json数据
        msgJsonObject.clear();
        //填入CgiCmd
        msgJsonObject.put("CgiCmd", "GetGroupLists");
        //构建CgiRequest
        JSONObject cgiRequest = new JSONObject();
        msgJsonObject.put("CgiRequest", cgiRequest);
        Bot bot = checkBot(null);
        return post(bot, "MagicCgiCmd", msgJsonObject);
    }

    /**
     * 通过群号得到群成员
     *
     * @param groupUid 群号
     * @return 得到的请求数据
     */
    public static String getGroupMemberList(Long groupUid) {
        return getGroupMemberList(groupUid, "");
    }

    /**
     * 得到群组的成员
     *
     * @param groupUid   QQ群号
     * @param lastBuffer 字符串，不为空表示还有数据
     * @return 请求的结果
     */
    public static String getGroupMemberList(Long groupUid, String lastBuffer) {
        //清除已有的json数据
        msgJsonObject.clear();
        //填入CgiCmd
        msgJsonObject.put("CgiCmd", "GetGroupMemberLists");
        //构建CgiRequest
        JSONObject cgiRequest = new JSONObject();
        cgiRequest.put("Uin", groupUid);
        cgiRequest.put("LastBuffer", lastBuffer);
        msgJsonObject.put("CgiRequest", cgiRequest);
        Bot bot = checkBot(null);
        return post(bot, "MagicCgiCmd", msgJsonObject);
    }

    /**
     * 上传资源文件 语言/图片/文件
     *
     * @param fileCommand 1好友图片 2群组图片 26好友语音 29群组语音 final类
     * @param fileType    文件来源类型 枚举类
     * @param fileUrl     文件地址
     * @return 结果
     */
    public static String uploadFile(Integer fileCommand, FileType fileType, String fileUrl) {
        SendMsg sendMsg = new SendMsg();
        sendMsg.setCgiCmd("PicUp.DataUp");
        CgiRequest cgiRequest = new CgiRequest();
        cgiRequest.setCommandId(fileCommand);
        switch (fileType) {
            case FILE_URL: {
                cgiRequest.setFileUrl(fileUrl);
                break;
            }
            case FILE_PATH: {
                cgiRequest.setFilePath(fileUrl);
                break;
            }
            case BASE_64_BUF: {
                cgiRequest.setBase64Buf(fileUrl);
                break;
            }
            default:

        }
        sendMsg.setCgiRequest(cgiRequest);

        Bot bot = checkBot();

        //资源上传手动处理
        try {
            String postUrl = "http://" + bot.getIp() + ":" + bot.getPort() + "/v1/upload?qq=" + bot.getBotId();
            //创建http请求
            HttpPost httpPost = new HttpPost(postUrl);
            httpPost.addHeader("Content-type", "application/json;");
            StringEntity entity = new StringEntity(JSONObject.toJSONString(buildMsgJsonObject(sendMsg)), ContentType.APPLICATION_JSON);
            httpPost.setEntity(entity);
            response = httpClient.execute(httpPost);
            result = EntityUtils.toString(response.getEntity(), "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
}