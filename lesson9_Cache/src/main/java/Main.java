import classesImp.LoaderImp;
import classesImp.ServiceImp;
import interfaces.Loader;
import interfaces.Service;

import java.io.*;
import java.util.Date;

public class Main {
    public static void main(String[] args) {
        Loader loader1 = new LoaderImp();
        Service service1 = new ServiceImp();
        String rootDir = "/home/oleg/IdeaProjects/SberbankMaven/lesson9_Cache/src/main/resources";
        CacheProxy cacheProxy = new CacheProxy(rootDir,true);

        Service service2 = cacheProxy.cache(service1);

        System.out.println(service2.printStringInteger("текст", 8));
        System.out.println(service2.printStringInteger("текст", 9));
        System.out.println(service2.printStringInteger("текст", 9));
        System.out.println(service2.printStringInteger("текст", 7));
//        System.out.println(int.class.getSimpleName() + " " + Integer.class.getSimpleName());
        System.out.println(service2.run("","test", 15, new Date()));
        System.out.println(service2.run("","test1", 10, new Date()));
        System.out.println(service2.run("","test", 15, new Date()));

//        loader2.doHardWork("text", 9);
    }
}
