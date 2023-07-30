package com.ruoyi.opq;

import com.ruoyi.socket.OpqWebSocket;
import com.ruoyi.system.domain.OpqPlugin;
import org.apache.tomcat.util.scan.JarFactory;
import sun.misc.ClassLoaderUtil;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * 插件加载类
 *
 * @author zjzaki
 * @create 2023年05月15日 22:05:20
 */
public class PluginLoader {

    /**
     * jar包URL
     */
    private List<URL> pluginsFileUrlList;

    /**
     * jar包URL加载器
     */
    private URLClassLoader jarUrlClassLoader;

    /**
     * jar包列表
     */
    private List<JarFile> jarFilesList;

    /**
     * 插件加载器
     */
    private static PluginLoader pluginLoader;

    /**
     * 插件列表
     */
    private List<OpqPlugin> opqPluginList;

    /**
     * 提供对外的静态方法获取该对象
     *
     * @return 插件加载类实体
     */
    public static PluginLoader getInstance() {
        if (pluginLoader == null) {
            synchronized (OpqWebSocket.class) {
                if (pluginLoader == null) {
                    pluginLoader = new PluginLoader();
                }
            }
        }
        return pluginLoader;
    }

    /**
     * 读取plugins文件夹中的jar包
     */
    public boolean loadPluginJar() {
        pluginsFileUrlList = new ArrayList<>();
        jarFilesList = new ArrayList<>();
        opqPluginList = new ArrayList<>();
        //读取plugins文件夹
        File filePluginsJar = new File("plugins");
        //得到文件夹中所有的文件列表
        File[] files = filePluginsJar.listFiles();
        //遍历文件
        if (files == null) {
            return false;
        }
        for (File pluginFile : files) {
            if (pluginFile.isFile() && pluginFile.getName().startsWith("opqj-plugin-") && pluginFile.getName().endsWith(".jar")) {

                //jar文件URL
                URL pluginFileUrl = null;
                try {
                    pluginFileUrl = pluginFile.toURI().toURL();
                    pluginsFileUrlList.add(pluginFileUrl);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                JarFile jarFile = null;
                try {
                    jarFile = new JarFile(pluginFile);
                    jarFilesList.add(jarFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        //将list转换为数组
        URL[] urls = new URL[pluginsFileUrlList.size()];
        for (int i = 0; i < pluginsFileUrlList.size(); i++) {
            urls[i] = pluginsFileUrlList.get(i);
        }
        jarUrlClassLoader = new URLClassLoader(urls, Thread.currentThread().getContextClassLoader());

        //遍历jar文件读取插件详细
        for (JarFile jarFile : jarFilesList) {
            OpqPlugin opqPlugin = new OpqPlugin();
            //读取jar文件中的class类的信息
            Enumeration<JarEntry> entries = jarFile.entries();
            List<String> classNames = getClassNames(entries);
            classNames.forEach(clazzName -> {
                if (clazzName.endsWith("Main")) {
                    try {
                        //得到class对象
                        Class<?> aClass = jarUrlClassLoader.loadClass(clazzName);
                        //实例化这个对象
                        Object instance = aClass.newInstance();
                        //得到字段
                        Field pluginNameFiled = aClass.getDeclaredField("pluginName");
                        pluginNameFiled.setAccessible(true);
                        String pluginName = (String) pluginNameFiled.get(instance);
                        opqPlugin.setName(pluginName);
                        Field pluginDescFiled = aClass.getDeclaredField("pluginDesc");
                        pluginDescFiled.setAccessible(true);
                        String pluginDesc = (String) pluginDescFiled.get(instance);
                        opqPlugin.setDesc(pluginDesc);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            opqPluginList.add(opqPlugin);
        }

        return true;
    }

    /**
     * 得到jar包中所有的class文件
     *
     * @param entries
     * @return class类文件名列表
     */
    public List<String> getClassNames(Enumeration<JarEntry> entries) {
        List<String> classNames = new ArrayList<>();
        while (entries.hasMoreElements()) {
            JarEntry jarEntry = entries.nextElement();
            String name = jarEntry.getName();
            if (name.endsWith(".class")) {
                String replace = name.replace(".class", "").replace("/", ".");
                classNames.add(replace);
            }
        }
        return classNames;
    }

    public List<OpqPlugin> getOpqPluginList() {
        return opqPluginList;
    }

    public URLClassLoader getJarUrlClassLoader() {
        return jarUrlClassLoader;
    }

    public List<JarFile> getJarFilesList() {
        return jarFilesList;
    }

    /**
     * 关闭jar文件，重载jarUrlList
     *
     * @param index
     */
    public void closeJarFile(int index) {
        //将list转换为数组
        URL[] urls = new URL[pluginsFileUrlList.size()];
        for (int i = 0; i < pluginsFileUrlList.size(); i++) {
            if (index != i) {
                urls[i] = pluginsFileUrlList.get(i);
            }
        }
        try {
            jarUrlClassLoader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        JarFile jarFile = jarFilesList.get(index);
        try {
            jarFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        jarFile = null;
        System.gc();
        ClassLoaderUtil.releaseLoader(jarUrlClassLoader);
        try {
            jarUrlClassLoader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        jarUrlClassLoader = new URLClassLoader(urls, Thread.currentThread().getContextClassLoader());

    }
}
