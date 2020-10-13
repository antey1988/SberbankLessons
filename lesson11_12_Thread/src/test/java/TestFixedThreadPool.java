import org.junit.Test;

import java.util.concurrent.TimeUnit;

public class TestFixedThreadPool {
    @Test
    public void testWork(){
        FixedThreadPool fixedThreadPool = new FixedThreadPool("First", 3);
        int i = 0;
        while (i < 15) {
            int j = i;
            fixedThreadPool.execute(()->{
                try {
                    TimeUnit.SECONDS.sleep(3);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                System.out.println(Thread.currentThread().getName()+ ": tasl # " + j);
            });
            i++;
        }

        System.out.println(fixedThreadPool.countInQueue());

        fixedThreadPool.start();

        try {
            TimeUnit.MICROSECONDS.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println(fixedThreadPool.countInQueue());

        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println(fixedThreadPool.countInQueue());

        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println(fixedThreadPool.countInQueue());

        fixedThreadPool.stop();
    }
}
