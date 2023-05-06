package org.kys.bnmo.helpers.plugins;

import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

public class PluginLoader {
    private String path = "D:/Tubes2Plugins";

    public static void main(String[] args) {

        try {
            // Create a new ClassLoader instance
            URLClassLoader classLoader = new URLClassLoader(
                    new URL[]{ new URL("file:D:/Tubes2Plugins/plugin.jar") });

            // Load the desired class
            Class pluginClass = classLoader.loadClass("org.kys.bnmo.plugins.test.Hello");

            // Instantiate the plugin class
            Object plugin = pluginClass.newInstance();

            // Invoke a method on the plugin object
            Method pluginMethod = pluginClass.getMethod("hello");
            pluginMethod.invoke(plugin);

        } catch (Exception e) {
            // Handle URL syntax errors
            e.printStackTrace();
        }
    }

}
