package com.zjzaki.core.pojo.msg;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author zjzaki
 * @create 2023年04月17日 12:22:54
 */
public class AtUin {

    @JSONField(name = "Uin")
    private Long uin;

    @JSONField(name = "Nick")
    private String nick;

    public AtUin() {
    }

    public AtUin(Long uin) {
        this.uin = uin;
    }

    public AtUin(Long uin, String nick) {
        this.uin = uin;
        this.nick = nick;
    }

    public Long getUin() {
        return uin;
    }

    public void setUin(Long uin) {
        this.uin = uin;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}
