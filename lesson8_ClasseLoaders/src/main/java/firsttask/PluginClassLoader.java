package firsttask;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class PluginClassLoader extends ClassLoader {
    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] bytesclass = loadClassFromFile(name);
        return defineClass(null, bytesclass, 0, bytesclass.length);
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
