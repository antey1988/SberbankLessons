package exmanager;

import java.util.Arrays;
import java.util.concurrent.CountDownLatch;

public class ExecutionManagerImp implements ExecutionManager{
    private final ScalableThreadPool threadPoolManager;
    private static int numberContext = 1;

    public ExecutionManagerImp(ScalableThreadPool threadPoolManager) {
        this.threadPoolManager = threadPoolManager;
        this.threadPoolManager.start();
    }

    public ExecutionManagerImp(){
        this(new ScalableThreadPool("Starting Contexts", 1,1));
    }

    @Override
    public Context execute(Runnable callback, Runnable... tasks) {
        ScalableThreadPool threadPoolContext = new ScalableThreadPool("" + numberContext++ + " call Context", 5,8);
        Context context = new ContextImp(tasks.length+1, threadPoolContext);

        CountDownLatch cdl = new CountDownLatch(tasks.length);
        threadPoolManager.execute(()->{
            Arrays.stream(tasks).forEach(task-> threadPoolContext.execute(()->{
                try {
                    task.run();
                    threadPoolContext.getCurrentCompletedTaskCount().incrementAndGet();
                } catch (Exception e) {
                    threadPoolContext.getCurrentFailedTaskCount().incrementAndGet();
                } finally {
                    cdl.countDown();
                }
            }));
            threadPoolContext.execute(()->{
                try {
                    cdl.await();
                    callback.run();
                    threadPoolContext.getCurrentCompletedTaskCount().incrementAndGet();
                } catch (InterruptedException e) {
                    threadPoolContext.getCurrentInterruptedTaskCount().incrementAndGet();
                    Thread.currentThread().interrupt();
                } catch (Exception e) {
                    threadPoolContext.getCurrentFailedTaskCount().incrementAndGet();
                }
            });
        });

        return context;
    }

    @Override
    public void shutdown() {
        threadPoolManager.shutdown();
    }
}
