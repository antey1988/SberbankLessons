import org.junit.Test;

import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        ScalableThreadPool scalableThreadPool = new ScalableThreadPool("First", 5,2);
        addTask(scalableThreadPool, 9, "one");
        scalableThreadPool.start();
        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        addTask(scalableThreadPool, 15, "two");
        scalableThreadPool.stop();

        /*System.out.println(scalableThreadPool.countInQueue());



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

        System.out.println(scalableThreadPool.countInQueue());*/

        if (scalableThreadPool.countInQueue() == 0)
            scalableThreadPool.stop();
    }

    private static void addTask(ScalableThreadPool scalableThreadPool, int count, String group) {
        int i = 0;
        while (i < count) {
            int j = i;
            scalableThreadPool.execute(()->{
                try {
                    TimeUnit.SECONDS.sleep(3);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                System.out.println(Thread.currentThread().getName()+ ": group " + group + "  tasl #" + j);
            });
            i++;
        }
    }
}
