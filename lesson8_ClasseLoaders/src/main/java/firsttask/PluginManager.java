package firsttask;

import java.io.File;
import java.net.*;

public class PluginManager {
    private final String pluginRootDirectory;

    public PluginManager(String pluginRootDirectory) {
        this.pluginRootDirectory = pluginRootDirectory;
    }

    public Plugin load(String pluginName, String pluginClassName) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        String _pluginRootDirectory = pluginRootDirectory.replace(File.separatorChar, '.');
        String _pluginName = pluginName.replace(File.separatorChar, '.');
        String fullPluginClassName = _pluginRootDirectory + '.' + _pluginName + '.' + pluginClassName;
        PluginClassLoader pluginClassLoader = new PluginClassLoader();
        return (Plugin) pluginClassLoader.loadClass(fullPluginClassName).newInstance();
    }

    /*public Plugin load(String pluginName, String pluginClassName) {
        URLClassLoader urlClassLoader;
    }*/

}
