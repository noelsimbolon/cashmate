package org.kys.bnmo.helpers.plugins;

import org.kys.bnmo.plugins.adapters.PageAdapter;
import org.kys.bnmo.views.Page;

import java.io.Console;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.Manifest;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class PluginLoader {
    private String path = "D:/Tubes2Plugins";
    static final List<Class> plugins = new ArrayList<>();
    static boolean isLoaded = false;

    public List<Class> loadClasses() {
        if (isLoaded) return new ArrayList<>(plugins);

        try {
            // Create a new ClassLoader instance
            File pluginsFolder = new File("D:/Tubes2Plugins");

            if (!pluginsFolder.exists()) {
                pluginsFolder.mkdirs();
            }

            File[] jarFiles = pluginsFolder.listFiles((dir, name) -> name.endsWith(".jar"));

            for (File jarFile : jarFiles) {

                Manifest manifest = new JarFile(jarFile.getAbsolutePath()).getManifest();
                String mainClassName = manifest.getMainAttributes().getValue("Main-Class");
                URLClassLoader classLoader = new URLClassLoader(
                        new URL[]{new URL("file:" + jarFile.getAbsolutePath())});

                Class plugin = classLoader.loadClass(mainClassName);

                System.out.println(plugin.getName());
                plugins.add(plugin);
            }

            isLoaded = true;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ArrayList<>(plugins);
    }

//    private Class findMainClass(String jarPath) throws IOException {
//        try (JarFile jarFile = new JarFile(jarPath)) {
//            Enumeration<JarEntry> entries = jarFile.entries();
//            while (entries.hasMoreElements()) {
//                JarEntry entry = entries.nextElement();
//                String entryName = entry.getName();
//                if (entryName.endsWith(".class")) {
//                    String className = entryName.substring(0, entryName.length() - 6).replace('/', '.');
//                    try {
//                        Class<?> clazz = Class.forName(className);
//                        Method mainMethod = clazz.getDeclaredMethod("main", String[].class);
//                        if (mainMethod != null) {
//                            return className;
//                        }
//                    } catch (ClassNotFoundException | NoSuchMethodException | SecurityException ex) {
//                        // Ignore and continue
//                    }
//                }
//            }
//        }
//        return null;
//    }

    public static void main(String[] args) {

        try {
            // Create a new ClassLoader instance
            File pluginsFolder = new File("D:/Tubes2Plugins");

            if (!pluginsFolder.exists()) {
                pluginsFolder.mkdirs();
            }

            File[] jarFiles = pluginsFolder.listFiles((dir, name) -> name.endsWith(".jar"));

            for (File file : jarFiles) {
                JarFile jarFile = new JarFile(file.getAbsolutePath());
                ZipFile zipFile = new ZipFile(file.getAbsolutePath());

                Manifest manifest = jarFile.getManifest();
                String mainClassName = manifest.getMainAttributes().getValue("Main-Class");

                Enumeration<? extends ZipEntry> entries = zipFile.entries();

                while (entries.hasMoreElements()) {
                    ZipEntry entry = entries.nextElement();
                    String entryName = entry.getName();

                    System.out.println(entryName);

                    if (entryName.endsWith(mainClassName + ".class")) {
                        // Convert the entry name to a class name
                        String className = entryName.substring(0, entryName.length() - 6).replace('/', '.');

                        URLClassLoader classLoader = new URLClassLoader(
                                new URL[]{new URL("file:" + file.getAbsolutePath())});

                        Class plugin = classLoader.loadClass(mainClassName);
                        plugins.add(plugin);
                        System.out.println("Loaded class: " + plugin.getName());
                        break;
                    }
                }
            }

            isLoaded = true;

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
