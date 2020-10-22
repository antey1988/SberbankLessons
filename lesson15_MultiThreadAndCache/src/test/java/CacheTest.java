import classesImp.ServiceImp;
import interfaces.Service;
import org.junit.Assert;
import org.junit.Before;
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
    final int allCountReqest = 1000;
    final int deferentCountReqest = 6;
    Service service, service1, service2;
    String rootDir;
    CacheProxy cacheProxy;
    PerformanceProxy performanceProxy;
    ExecutorService executorService;
    CountDownLatch cdl;

    @Before
    public void start(){
        service = new ServiceImp();
        rootDir = "/home/oleg/IdeaProjects/SberbankMaven/lesson15_MultiThreadAndCache/src/main/resources";
        //  = "E:\\User\\Oleg\\IdeaProjects\\_SberbankLessons\\lesson9_Cache\\src\\main\\resources";
        cacheProxy = new CacheProxy(rootDir,true);
        performanceProxy = new PerformanceProxy();
        service1 = cacheProxy.cache(service);
        service2 = performanceProxy.metric(service1);

        executorService = Executors.newFixedThreadPool(4);
        cdl = new CountDownLatch(allCountReqest);
    }

    @Test
    public void testCacheInMemory() {
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

    @Test
    public void testCacheInFile() {
        List<Runnable> runnableList;
        AtomicInteger i = new AtomicInteger();
        runnableList = Stream.generate(() ->
                (Runnable) () -> {
                    service2.printStringInteger("test"+ i.getAndIncrement(), 10+(int) (Math.random() * deferentCountReqest), new Date());
                    cdl.countDown();
                }).limit(deferentCountReqest)
                .collect(Collectors.toList());

        runnableList.addAll(Stream.generate(() ->
                (Runnable) () -> {
                    service2.printStringInteger("test" + (int) (Math.random() * deferentCountReqest), 10+(int) (Math.random() * deferentCountReqest), new Date());
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

        int[] expected = cacheProxy.getCountReqestAndCache("printStringInteger");
        System.out.printf("Всего %d запросов, результаты %d взят из кэша, %d вычеслены и добавлены в кэш\n",
                expected[0], expected[0] - expected[1], expected[1]);
    }
}
