package firsttask;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class PluginClassLoader extends ClassLoader {

    /*@Override
    protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        synchronized (getClassLoadingLock(name)) {
            // First, check if the class has already been loaded
            Class<?> c = findLoadedClass(name);
            if (c != null) {
                return c;
            }
            c = findClass(name);
            if (resolve) {
                resolveClass(c);
            }
            return c;
        }
    }*/

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] bytesclass = loadClassFromFile(name);
        Class<?> c = defineClass(null, bytesclass, 0, bytesclass.length);
        return c;
    }

    private byte[] loadClassFromFile(String name) {
        String FileName =  name.replace('.', File.separatorChar) +  ".class";
        File file = new File(FileName);
        byte[] classBytes = new byte[(int)file.length()];
        FileInputStream fis;
        try {
            fis = new FileInputStream(file);
            fis.read(classBytes, 0 , classBytes.length);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return classBytes;
    }
}
