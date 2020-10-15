import org.junit.Test;
import task.Task;

import java.util.concurrent.*;

public class TaskTest {


    @Test
    public void testWork() {
        Task<Integer> task = new Task<>(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                throw new Exception();
            }
        });

        Executor executor = Executors.newFixedThreadPool(3);
        for (int i = 0; i < 5; i++) {
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        TimeUnit.SECONDS.sleep(1);
                        System.out.println(Thread.currentThread().getName() + " : " + task.get());
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            });
        }
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }


}
