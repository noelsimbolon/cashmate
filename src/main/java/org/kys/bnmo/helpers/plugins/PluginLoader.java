package org.kys.bnmo.helpers.plugins;

import org.kys.bnmo.plugins.adapters.PageAdapter;
import org.kys.bnmo.views.Page;

import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PluginLoader {
    private String path = "D:/Tubes2Plugins";
    static final List<Class> plugins = new ArrayList<>();
    static boolean isLoaded = false;


    public List<Class> loadClasses() {
        if (isLoaded) return new ArrayList<>(plugins);

        try {
            // Create a new ClassLoader instance
            URLClassLoader classLoader = new URLClassLoader(
                    new URL[]{ new URL("file:D:/Tubes2Plugins/plugin.jar") });

            // Load the desired class
            Class pluginClass = classLoader.loadClass("org.kys.bnmo.plugins.test.PieChartPlugin");
            plugins.add(pluginClass);

            isLoaded = true;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ArrayList<>(plugins);
    }
    public static void main(String[] args) {

        try {
            // Create a new ClassLoader instance
            URLClassLoader classLoader = new URLClassLoader(
                    new URL[]{ new URL("file:D:/Tubes2Plugins/plugin.jar") });

            // Load the desired class
            Class pluginClass = classLoader.loadClass("org.kys.bnmo.plugins.test.PieChartPlugin");
            plugins.add(pluginClass);

//            System.out.println(pluginClass.getName());
////             Instantiate the plugin class
//            Object plugin = pluginClass.getDeclaredConstructor(PageAdapter.class).newInstance(
//                    new PageAdapter()
//            );

            // Invoke a method on the plugin object
//            Method[] metods = pluginClass.getMethods();


//            Method pluginMethod = pluginClass.getMethod("onLoad");
//            pluginMethod.invoke(plugin);

        } catch (Exception e) {
            // Handle URL syntax errors
            e.printStackTrace();
        }
    }

}
