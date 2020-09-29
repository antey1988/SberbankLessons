import classesImp.LoaderImp;
import classesImp.ServiceImp;
import interfaces.Loader;
import interfaces.Service;

public class Main {
    public static void main(String[] args) {
        Loader loader1 = new LoaderImp();
        loader1.doHardWork("fdfs", 2);

        Service service1 = new ServiceImp();
        service1.doHardWork("dfdf", 4);

        CacheProxy cacheProxy = new CacheProxy();
        Loader loader2 = cacheProxy.cache(loader1);
        loader2.doHardWork("текст", 6);

        Service service2 = cacheProxy.cache(service1);
        service2.doHardWork("текст", 8);

//        loader2.doHardWork("text", 9);
    }
}
