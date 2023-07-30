package com.ruoyi.util;

import com.zjzaki.core.pojo.Msg;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

/**
 * https://blog.csdn.net/weixin_45492007/article/details/118068537
 *
 * @create 2023年05月15日 15:06:32
 */
public class ReadPluginUtil {

    /**
     * 读取所有的插件
     */
    public static void readPlugins() {
        File filePlugins = new File("./plugins");

        File[] files = filePlugins.listFiles();
        for (File pluginFile : files) {
            System.out.println(pluginFile);

            if (pluginFile.getName().startsWith("opqj-plugin-") && pluginFile.getName().endsWith(".jar")) {
                if (!pluginFile.exists()) {
                    System.out.println("文件不存在！");
                    return;
                }
                if (!pluginFile.isFile()) {
                    System.out.println("读取的为文件夹而非文件！");
                    return;
                }
                if (!pluginFile.canRead()) {
                    System.out.println("当前文件不可读！");
                    return;
                }
                URL url1 = null;
                try {
                    url1 = pluginFile.toURI().toURL();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                URLClassLoader jarUrlClassLoader = new URLClassLoader(new URL[]{url1},
                        Thread.currentThread().getContextClassLoader());
                JarFile jarFile = null;
                try {
                    jarFile = new JarFile(pluginFile);
                    // 获取jar中实际的MAINFEST.MF文件
                    Manifest manifest = jarFile.getManifest();
                    // 打印jar包中的信息
                    printManifestFile(manifest);
                    // 开始获取jar中的.class文件
                    Enumeration<JarEntry> entries = jarFile.entries();
                    List<String> classNames = getClassNames(entries);
                    classNames.forEach(x -> {
                        System.out.println("zjzkai---> " + x);
                        if (x.endsWith("Main")) {
                            System.out.println(1);
                            loadAndInstanceClass(x, jarUrlClassLoader);
                        }
                    });

                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (jarFile != null) {
                        try {
                            jarFile.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    private static void loadAndInstanceClass(String clazzName, ClassLoader classLoader) {
        try {
            // 需要使用其他的classLoader加载
            Class<?> forName = classLoader.loadClass(clazzName);
            System.out.println(forName);
            Object newInstance = forName.newInstance();
            System.out.println(newInstance);
            Method method = forName.getDeclaredMethod("receiveGroupMsg", Msg.class);
            method.setAccessible(true);
            method.invoke(newInstance,new Msg());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static List<String> getClassNames(Enumeration<JarEntry> entries) {
        List<String> classNames = new ArrayList<String>();
        while (entries.hasMoreElements()) {
            JarEntry nextElement = entries.nextElement();
            String name = nextElement.getName();
            // 这个获取的就是一个实体类class java.util.jar.JarFile$JarFileEntry
            // Class<? extends JarEntry> class1 = nextElement.getClass();
            //System.out.println("entry name=" + name);
            // 这样就获取所有的jar中的class文件

            // 加载某个class文件，并实现动态运行某个class
            if (name.endsWith(".class")) {
                String replace = name.replace(".class", "").replace("/", ".");
                classNames.add(replace);
            }
        }
        return classNames;
    }

    private static void printManifestFile(Manifest manifest) {
        Attributes mainAttributes = manifest.getMainAttributes();
        Set<Map.Entry<Object, Object>> entrySet = mainAttributes.entrySet();
        Iterator<Map.Entry<Object, Object>> iterator = entrySet.iterator();
        // 打印并显示当前的MAINFEST.MF文件中的信息
        while (iterator.hasNext()) {
            Map.Entry<Object, Object> next = iterator.next();
            Object key = next.getKey();
            Object value = next.getValue();
            // 这里可以获取到Class-Path,或者某个执行的Main-Class
            System.out.println(key + ": " + value);
        }
    }
}
