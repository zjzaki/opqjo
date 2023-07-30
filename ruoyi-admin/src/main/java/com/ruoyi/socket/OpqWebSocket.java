package com.ruoyi.socket;

import com.alibaba.fastjson.JSONObject;
import com.ruoyi.opq.Opq;
import com.zjzaki.core.pojo.Bot;
import com.zjzaki.core.pojo.Msg;
import com.zjzaki.core.utils.JsonTransform;
import com.zjzaki.core.utils.PropertiesUtil;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author zjzaki
 * @create 2023年04月16日 21:54:12
 */
public class OpqWebSocket extends WebSocketClient {

    /**
     * 日志
     */
    private static final Logger log = LoggerFactory.getLogger(OpqWebSocket.class);

    /**
     * 数据保存对象实体
     */
    private Opq opq = new Opq();

    /**
     * websocket实体
     */
    private static volatile OpqWebSocket opqWebSocket;

    /**
     * 群聊新消息
     */
    private static final String ON_EVENT_GROUP_NEW_MSG = "ON_EVENT_GROUP_NEW_MSG";

    /**
     * 进群事件
     */
    private static final String ON_EVENT_GROUP_JOIN = "ON_EVENT_GROUP_JOIN";

    /**
     * 退群事件
     */
    private static final String ON_EVENT_GROUP_EXIT = "ON_EVENT_GROUP_EXIT";

    /**
     * 邀请事件
     */
    private static final String ON_EVENT_GROUP_INVITE = "ON_EVENT_GROUP_INVITE";

    /**
     * 群组系统事件 1 申请进群 2 被邀请进群 13退出群聊 15取消管理员 3设置管理员 //13退出群聊 针对管理员群主的推送事件
     */
    private static final String ON_EVENT_GROUP_SYSTEM_MSG_NOTIFY = "ON_EVENT_GROUP_SYSTEM_MSG_NOTIFY";

    /**
     * 提供对外的静态方法获取该对象
     *
     * @return
     */
    public static OpqWebSocket getInstance() {
        if (opqWebSocket == null) {
            synchronized (OpqWebSocket.class) {
                if (opqWebSocket == null) {
                    //得到bot配置
                    Bot bot = PropertiesUtil.getBot();
                    //连接地址
                    String wsUrl = "ws://" + bot.getIp() + ":" + bot.getPort() + "/ws";
                    URI uri = null;
                    try {
                        uri = new URI(wsUrl);
                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                    }
                    opqWebSocket = new OpqWebSocket(uri, new Draft_6455());
                }
            }
        }
        return opqWebSocket;
    }

    private OpqWebSocket(URI serverUri, Draft protocolDraft) {
        super(serverUri, protocolDraft);
    }

    @Override
    public void onOpen(ServerHandshake serverHandshake) {
        log.info("OpqWebSocket---连接成功");
    }

    @Override
    public void onMessage(String s) {
        log.info("收到消息---" + s);
        JSONObject jsonObject = JSONObject.parseObject(s);

        JSONObject currentPacket = jsonObject.getJSONObject("CurrentPacket");

        switch (currentPacket.getString("EventName")) {
            case ON_EVENT_GROUP_NEW_MSG: {
                JSONObject eventData = currentPacket.getJSONObject("EventData");
                //插入数据库中
                opq.insertGroupMsg(eventData);
                break;
            }
            case ON_EVENT_GROUP_JOIN:
            case ON_EVENT_GROUP_EXIT: {
                JSONObject eventData = currentPacket.getJSONObject("EventData");
                Msg msg = JsonTransform.json2Msg(eventData);
                System.out.println(msg);
                //得到群号 ---获取不到，等到框架更新
                Long fromUin = msg.getMsgHead().getFromUin();
                opq.refreshGroupMember(fromUin);
                //dataSave.refreshGroupAndMember();
                log.info("刷新群组: " + fromUin + "成员信息");
                break;
            }
            default:
        }
    }

    @Override
    public void onClose(int i, String s, boolean b) {
        log.info("连接断开");
        opqWebSocket = null;
    }

    @Override
    public void onError(Exception e) {
        log.error("发生错误---" + e.getMessage());
        e.printStackTrace();
    }
}
