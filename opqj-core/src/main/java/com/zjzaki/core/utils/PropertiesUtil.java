package com.zjzaki.core.utils;


import com.zjzaki.core.pojo.Bot;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author zjzaki
 * @create 2023年04月07日 09:59:27
 */
public class PropertiesUtil {

    /**
     * 读取bot.properties配置文件
     *
     * @return
     */
    public static Bot getBot() {
        Bot bot = new Bot();
        InputStream is = PropertiesUtil.class.getClassLoader().getResourceAsStream("bot.properties");
        Properties properties = new Properties();
        try {
            properties.load(is);
            bot.setIp(properties.getProperty("ip"));
            bot.setPort(Integer.valueOf(properties.getProperty("port")));
            bot.setBotId(Long.valueOf(properties.getProperty("botId")));

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return bot;
    }
}
