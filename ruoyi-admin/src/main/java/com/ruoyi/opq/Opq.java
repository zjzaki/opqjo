package com.ruoyi.opq;

import com.alibaba.fastjson.JSONObject;
import com.ruoyi.system.domain.OpqGroupMember;
import com.ruoyi.system.domain.OpqGroupMsg;
import com.ruoyi.system.domain.OpqGroupPlugin;
import com.ruoyi.system.domain.OpqPlugin;
import com.ruoyi.system.service.OpqGroupMemberService;
import com.ruoyi.system.service.OpqGroupMsgService;
import com.ruoyi.system.service.OpqGroupService;
import com.ruoyi.system.service.OpqPluginService;
import com.zjzaki.core.annotation.IgnoreBot;
import com.zjzaki.core.pojo.Msg;
import com.zjzaki.core.pojo.msg.AtUin;
import com.zjzaki.core.pojo.msg.Image;
import com.zjzaki.core.pojo.msg.MsgBody;
import com.zjzaki.core.pojo.msg.MsgHead;
import com.zjzaki.core.utils.JsonTransform;
import com.zjzaki.core.utils.PropertiesUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.List;
import java.util.UUID;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * @author zjzaki
 * @create 2023年04月16日 21:28:13
 */
@Configuration("dataSave")
public class Opq {

    private static final Logger log = LoggerFactory.getLogger(Opq.class);

    @Resource(name = "opqGroupMsgServiceImpl")
    private OpqGroupMsgService opqGroupMsgService;

    @Resource(name = "opqGroupServiceImpl")
    private OpqGroupService opqGroupService;

    @Resource(name = "opqGroupMemberServiceImpl")
    private OpqGroupMemberService opqGroupMemberService;

    @Resource(name = "opqPluginServiceImpl")
    private OpqPluginService opqPluginService;

    private static Opq opq;

    private Long botId;

    @PostConstruct
    public void init() {
        opq = this;
        opq.opqGroupMsgService = this.opqGroupMsgService;
        opq.opqGroupService = this.opqGroupService;
        opq.opqGroupMemberService = this.opqGroupMemberService;
        opq.opqPluginService = this.opqPluginService;
        botId = PropertiesUtil.getBot().getBotId();
        addPlugin();
    }

    public void addPlugin() {
        PluginLoader instance = PluginLoader.getInstance();
        instance.loadPluginJar();
        // 得到加载的插件列表
        List<OpqPlugin> opqPluginList = instance.getOpqPluginList();
        //得到所有jar包
        List<JarFile> jarFilesList = instance.getJarFilesList();
        // 遍历插件列表
        for (int i = 0; i < jarFilesList.size(); i++) {
            OpqPlugin opqPlugin = opqPluginList.get(i);

            //查询数据库，判断是否存在该插件
            OpqPlugin plugin = opq.opqPluginService.selectOpqPluginByName(opqPlugin.getName());
            if (plugin == null) {
                //生成UUID
                String uuid = UUID.randomUUID().toString().replace("-", "");
                //设置uuid
                opqPlugin.setId(uuid);
                //设置启用
                opqPlugin.setEnable("1");
                //调用业务层方法，插入数据库
                opq.opqPluginService.addPlugin(opqPlugin);
            } else if ("0".equals(plugin.getEnable())) {

                JarFile jarFile = jarFilesList.get(i);
                try {
                    jarFile.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                jarFilesList.remove(i);
                opqPluginList.remove(i);
            }
        }
    }

    /**
     * 插入群组消息
     *
     * @param msgJson JSON格式的消息
     */
    public synchronized void insertGroupMsg(JSONObject msgJson) {
        //解析数据
        Msg msg = JsonTransform.json2Msg(msgJson);
        //插件调用
        onGroupMsg(msg);
        //测试插件调用专用
        new PluginTest().receiveGroupMsg(msg);
        //当msgBody为null时不执行
        if (msg.getMsgBody() == null) {
            return;
        }
        //调用业务层方法，放入redis中
        opq.opqGroupMsgService.insertRedisCacheMsgCount(msg);
        //表名
        String tableName = "tb_group_msg_" + msg.getMsgHead().getGroupInfo().getGroupCode();
        //判断表是否存在
        boolean b = opq.opqGroupMsgService.existTable(tableName);

        if (!b) {
            log.info("表不存在，创建表--->" + tableName);
            //不存在，创建表
            opq.opqGroupMsgService.createNewGroupMsgTable(tableName);
        }
        //格式化信息
        OpqGroupMsg opqGroupMsg = new OpqGroupMsg();
        //得到消息头
        MsgHead msgHead = msg.getMsgHead();
        //得到消息体
        MsgBody msgBody = msg.getMsgBody();

        opqGroupMsg.setGroupUin(msgHead.getFromUin());
        opqGroupMsg.setBotUin(msgHead.getToUin());
        opqGroupMsg.setSenderUin(msgHead.getSenderUin());
        opqGroupMsg.setSenderNick(msgHead.getSenderNick());
        opqGroupMsg.setFlag(1);
        opqGroupMsg.setMsgType(msgHead.getMsgType());
        opqGroupMsg.setC2cCmd(msgHead.getC2cCmd());
        opqGroupMsg.setMsgSeq(msgHead.getMsgSeq());
        opqGroupMsg.setMsgTime(msgHead.getMsgTime());
        opqGroupMsg.setMsgRandom(msgHead.getMsgRandom());
        opqGroupMsg.setMsgUid(msgHead.getMsgUid());
        opqGroupMsg.setGroupCard(msgHead.getGroupInfo().getGroupCard());
        opqGroupMsg.setGroupCode(msgHead.getGroupInfo().getGroupCode());
        opqGroupMsg.setGroupInfoSeq(msgHead.getGroupInfo().getGroupInfoSeq());
        opqGroupMsg.setGroupLevel(msgHead.getGroupInfo().getGroupLevel());
        opqGroupMsg.setGroupRank(msgHead.getGroupInfo().getGroupRank());
        opqGroupMsg.setGroupType(msgHead.getGroupInfo().getGroupType());
        opqGroupMsg.setGroupName(msgHead.getGroupInfo().getGroupName());
        opqGroupMsg.setSubMsgType(msgBody.getSubMsgType());
        opqGroupMsg.setContext(msgBody.getContent());
        List<AtUin> atUinLists = msgBody.getAtUinLists();
        if (atUinLists != null) {
            opqGroupMsg.setAtUinLists(atUinLists.toString());
        }
        List<Image> images = msgBody.getImages();
        if (images != null) {
            opqGroupMsg.setImages(images.toString());
        }
        opqGroupMsg.setVideos(msgBody.getVideo());
        opqGroupMsg.setVoice(msgBody.getVoice());
        //未完待续
        OpqGroupMember opqGroupMember = new OpqGroupMember();
        opqGroupMember.setMemberUin(msgHead.getSenderUin());
        opqGroupMember.setGroupUid(msgHead.getGroupInfo().getGroupCode());
        opqGroupMember.setLastSpeakTime(msgHead.getMsgTime());
        opq.opqGroupMemberService.updateOpqGroupMemberLastSpeakTime(opqGroupMember);
        //插入数据库
        int i = opq.opqGroupMsgService.insertGroupMsg(opqGroupMsg);
        log.info("插入消息--->" + tableName);
    }

    /**
     * 收到群聊消息
     *
     * @param msg
     */
    public void onGroupMsg(Msg msg) {
        PluginLoader pluginLoader = PluginLoader.getInstance();
        //得到类加载器
        URLClassLoader jarUrlClassLoader = pluginLoader.getJarUrlClassLoader();
        //得到插件列表
        List<JarFile> jarFilesList = pluginLoader.getJarFilesList();
        if (jarFilesList == null) {
            return;
        }
        //遍历jar包
        for (JarFile jarFile : jarFilesList) {
            // 开始获取jar中的.class文件
            Enumeration<JarEntry> entries = jarFile.entries();
            List<String> classNames = pluginLoader.getClassNames(entries);

            classNames.forEach(clazzName -> {
                if (clazzName.endsWith("Main")) {
                    try {
                        //得到class对象
                        Class<?> aClass = jarUrlClassLoader.loadClass(clazzName);
                        //创建对象
                        Object o = aClass.newInstance();
                        //得到方法
                        Method method = aClass.getDeclaredMethod("receiveGroupMsg", Msg.class);
                        method.setAccessible(true);
                        //得到插件的名字
                        Field pluginNameField = aClass.getDeclaredField("pluginName");
                        pluginNameField.setAccessible(true);
                        // 得到插件的名字
                        String pluginName = (String) pluginNameField.get(o);
                        // 查询插件实体
                        OpqPlugin plugin = opq.opqPluginService.selectOpqPluginByName(pluginName);
                        if (plugin == null) {
                            return;
                        }
                        //查询开启该插件的群聊
                        List<OpqGroupPlugin> opqGroupPlugins = opq.opqPluginService.selectGroupPluginListByPluginId(plugin.getId());
                        //如果开启的群聊为空，则跳过
                        if (opqGroupPlugins == null) {
                            return;
                        }
                        //遍历群聊列表
                        for (OpqGroupPlugin opqGroupPlugin : opqGroupPlugins) {
                            //判断该群聊是否开启当前插件
                            if (opqGroupPlugin.getGroupCode().equals(msg.getMsgHead().getFromUin())) {
                                //判断是否有忽略机器人自己消息的注解
                                // if (method.isAnnotationPresent(IgnoreBot.class)) {
                                //读取机器人配置文件
                                botId = PropertiesUtil.getBot().getBotId();
                                // 排除机器人的qq
                                if (!(msg.getMsgHead().getFromUin().equals(msg.getMsgHead().getSenderUin())
                                        || msg.getMsgHead().getSenderUin().equals(botId))) {
                                    method.invoke(o, msg);
                                }
                                //}else{
                                //  method.invoke(o, msg);
                                //}
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    /**
     * 刷新群组和群成员列表
     */
    public void refreshGroupAndMember() {
        opq.opqGroupService.refreshOpqGroupList();
    }

    /**
     * 刷新群成员信息
     *
     * @param groupUin 群号
     */
    public void refreshGroupMember(Long groupUin) {
        opq.opqGroupService.refreshOpqGroupMember(groupUin);
    }
}
