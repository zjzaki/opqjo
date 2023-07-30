package com.zjzaki;

import com.ruoyi.opq.PluginLoader;
import com.ruoyi.util.ReadPluginUtil;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.jar.JarFile;

/**
 * @author zjzaki
 * @create 2023年05月15日 15:10:12
 */
public class MyTest {

    @Test
    public void testReadPlugins() throws IOException {
        File file = new File("./plugins");
        File[] files = file.listFiles();
        for (File file1 : files) {
            System.out.println(file1.getName());
        }
    }
}
