package com.ruoyi.start;

import com.ruoyi.opq.PluginLoader;
import com.ruoyi.socket.OpqWebSocket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;

/**
 * @author zjzaki
 * @create 2023年04月16日 21:55:08
 */
@Component
@EnableScheduling
public class OpqStart implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(OpqStart.class);

    @Override
    public void run(String... args) throws Exception {
        log.info("OpqSocket连接");
        //通过单例模式的到opqWebSocket实例
        OpqWebSocket opqWebSocket = OpqWebSocket.getInstance();
        //连接websocket
        opqWebSocket.connect();
        //加载插件
//        PluginLoader.getInstance().loadPluginJar();
    }

    @PreDestroy
    public void shutdown(){
        //在spring项目关闭是断开Opq的socket连接
        OpqWebSocket.getInstance().close();
    }

    @Scheduled(cron = "0/3 * * * * ?")
    public void reconnect(){
        if (!OpqWebSocket.getInstance().isOpen()){
            OpqWebSocket.getInstance().connect();
            log.info("断线重连");
        }
    }
}
