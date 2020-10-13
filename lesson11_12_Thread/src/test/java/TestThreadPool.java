import org.junit.Test;

import java.util.concurrent.TimeUnit;

public class TestThreadPool {
    @Test
    public void testScaleThreadPool(){
        ScalableThreadPool scalableThreadPool = new ScalableThreadPool("First", 5,8);
        addTask(scalableThreadPool, 6, "one");
        scalableThreadPool.start();
        try {
            TimeUnit.SECONDS.sleep(5);
            System.out.println("\nnew group");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        addTask(scalableThreadPool, 7, "two");
        try {
            TimeUnit.SECONDS.sleep(5);
            System.out.println("\nstop pool");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        if (scalableThreadPool.countInQueue() == 0)
            scalableThreadPool.stop();
    }
    @Test
    public void testFixedThreadPool(){
        FixedThreadPool fixedThreadPool = new FixedThreadPool("First", 3);
        addTask(fixedThreadPool, 6, "one");
        fixedThreadPool.start();
        try {
            TimeUnit.SECONDS.sleep(5);
            System.out.println("\nnew group tasks");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        addTask(fixedThreadPool, 7, "two");
        try {
            TimeUnit.SECONDS.sleep(5);
            System.out.println("\nstop pool");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        if (fixedThreadPool.countInQueue() == 0)
            fixedThreadPool.stop();
        fixedThreadPool.start();
    }

    private static void addTask(ThreadPool threadPool, int count, String group) {
        int i = 0;
        while (i < count) {
            int j = i;
            threadPool.execute(()->{
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                System.out.println(Thread.currentThread().getName()+ ": group " + group + "  tasl #" + j);
            });
            i++;
        }
    }
}
