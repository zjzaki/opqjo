package com.zjzaki.core.plugin;

import com.zjzaki.core.pojo.Msg;

/**
 * @author zjzaki
 * @create 2023年04月19日 20:45:53
 */
public abstract class BasePlugin {

    /**
     * 插件名
     */
    private String pluginName;

    /**
     * 插件描述
     */
    private String pluginDesc;

    /**
     * 接收好友消息
     *
     * @param friendMsg 好友消息
     */
    protected void receiveFriendMsg(Msg friendMsg) {

    }

    /**
     * 群组消息
     *
     * @param groupMsg 群组消息
     */
    protected void receiveGroupMsg(Msg groupMsg) {

    }

    /**
     * 接收私聊消息
     *
     * @param privateMsg 私聊消息
     */
    protected void receivePrivateMsg(Msg privateMsg) {

    }
}
