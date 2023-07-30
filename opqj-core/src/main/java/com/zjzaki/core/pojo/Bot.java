package com.zjzaki.core.pojo;

/**
 * Bot对象实体
 * @author zjzaki
 * @create 2023年04月07日 10:05:17
 */
public class Bot {

    /**
     * 主键id
     */
    private Integer id;

    /**
     * OPQBot ip地址
     */
    private String ip;
    /**
     * OPQBot 端口
     */
    private Integer port;
    /**
     * OPQBot QQ号
     */
    private Long botId;

    /**
     * 主人qq
     */
    private Long masterQq;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public Long getBotId() {
        return botId;
    }

    public void setBotId(Long botId) {
        this.botId = botId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getMasterQq() {
        return masterQq;
    }

    public void setMasterQq(Long masterQq) {
        this.masterQq = masterQq;
    }

    @Override
    public String toString() {
        return "Bot{" +
                "id=" + id +
                ", ip='" + ip + '\'' +
                ", port=" + port +
                ", botId=" + botId +
                ", masterQq=" + masterQq +
                '}';
    }
}
