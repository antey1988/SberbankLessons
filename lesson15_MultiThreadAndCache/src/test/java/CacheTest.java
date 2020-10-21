import classesImp.ServiceImp;
import interfaces.Service;
import org.junit.Assert;
import org.junit.Test;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CacheTest {
    @Test
    public void testRun() {
        final int allCountReqest = 30;
        final int deferentCountReqest = 5;
        Service service = new ServiceImp();
        String rootDir = "/home/oleg/IdeaProjects/SberbankMaven/lesson15_MultiThreadAndCache/src/main/resources";
              //  = "E:\\User\\Oleg\\IdeaProjects\\_SberbankLessons\\lesson9_Cache\\src\\main\\resources";
        CacheProxy cacheProxy = new CacheProxy(rootDir,true);
        PerformanceProxy performanceProxy = new PerformanceProxy();
//        Service service1 = performanceProxy.metric(service);
//        Service service2 = cacheProxy.cache(service1);
        Service service1 = cacheProxy.cache(service);
        Service service2 = performanceProxy.metric(service1);

        ExecutorService executorService = Executors.newFixedThreadPool(2);
        CountDownLatch cdl = new CountDownLatch(allCountReqest);


        List<Runnable> runnableList;
        AtomicInteger i = new AtomicInteger();
        runnableList = Stream.generate(() ->
                (Runnable) () -> {
                    service2.run("test"+ i.getAndIncrement(), 10, new Date());
                    cdl.countDown();
                }).limit(deferentCountReqest)
                .collect(Collectors.toList());

        runnableList.addAll(Stream.generate(() ->
                (Runnable) () -> {
                    service2.run("test" + (int) (Math.random() * deferentCountReqest), 12, new Date());
                    cdl.countDown();
                }).limit(allCountReqest - deferentCountReqest)
                .collect(Collectors.toList()));
        Collections.shuffle(runnableList);
        runnableList.forEach(executorService::execute);



        try {
            cdl.await();
            executorService.shutdownNow();

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        int[] actual = new int[] {allCountReqest, deferentCountReqest};
        int[] expected = cacheProxy.getCountReqestAndCache("run");
        Assert.assertArrayEquals(expected, actual);
    }
}
