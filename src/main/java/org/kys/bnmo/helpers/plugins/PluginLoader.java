package org.kys.bnmo.helpers.plugins;
import org.kys.bnmo.plugins.interfaces.BasePlugin;
import org.kys.bnmo.plugins.interfaces.PluginInterface;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

public class PluginLoader {
    private String path = "D:/Tubes2Plugins";
    static final List<Class> plugins = new ArrayList<>();
    static boolean isLoaded = false;

    private void readJar() {
        try {
            // Create a new ClassLoader instance
            File pluginsFolder = new File("D:/Tubes2Plugins");

            if (!pluginsFolder.exists()) {
                pluginsFolder.mkdirs();
            }

            File[] jarFiles = pluginsFolder.listFiles((dir, name) -> name.endsWith(".jar"));

            for (File file : jarFiles) {

                try {
                    JarFile jarFile = new JarFile(file.getAbsolutePath());

                    Manifest manifest = jarFile.getManifest();
                    String mainClassName = manifest.getMainAttributes().getValue("Main-Class");

                    URLClassLoader classLoader = new URLClassLoader(
                            new URL[]{new URL("file:" + file.getAbsolutePath())});

                    Class plugin = classLoader.loadClass(mainClassName);

                    if (plugin.getSuperclass().equals(BasePlugin.class))
                    {
                        plugins.add(plugin);
                    }

                }

                catch (Exception e)
                {
                    System.out.println(e.getMessage());
                }
            }

            isLoaded = true;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public List<Class> loadClasses() {
        if (isLoaded) return new ArrayList<>(plugins);
        readJar();
        return new ArrayList<>(plugins);
    }

    public static void main(String[] args) {
        new PluginLoader().readJar();
    }

}
