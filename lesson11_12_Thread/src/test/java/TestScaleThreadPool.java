import org.junit.Test;

import java.util.concurrent.TimeUnit;

public class TestScaleThreadPool {
    @Test
    public void testWork(){
        ScalableThreadPool scalableThreadPool = new ScalableThreadPool("First", 2,4);
        int i = 0;
        while (i < 15) {
            int j = i;
            scalableThreadPool.execute(()->{
                try {
                    TimeUnit.SECONDS.sleep(3);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                System.out.println(Thread.currentThread().getName()+ ": tasl #" + j);
            });
            i++;
        }

        System.out.println(scalableThreadPool.countInQueue());

        scalableThreadPool.start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println(scalableThreadPool.countInQueue());

        try {
            TimeUnit.SECONDS.sleep(4);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println(scalableThreadPool.countInQueue());

        try {
            TimeUnit.SECONDS.sleep(4);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println(scalableThreadPool.countInQueue());

        scalableThreadPool.stop();
    }
}
