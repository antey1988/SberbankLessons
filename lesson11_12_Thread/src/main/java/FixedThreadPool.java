
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;

public class FixedThreadPool implements ThreadPool {
    private final String namePool;
    private volatile boolean isRun = true;
    private final int countThread;
    private final Queue<Runnable> queue;

    public FixedThreadPool(String namePool, int countThread) {
        this.namePool = namePool;
        this.countThread = countThread;
        this.isRun = true;
        this.queue = new LinkedList<>();
    }

    public int countInQueue() {
        int i;
        synchronized (queue) {
            i = queue.size();
        }
        return i;
    }

    @Override
    public void start() {
        for (int i = 0; i < countThread; i++) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (isRun) {
                        Runnable runnable;
                        synchronized (queue) {
                            runnable = queue.poll();
                        }
                        if (runnable != null)
                            runnable.run();
                    }
                }
            }, namePool + " pool" + " -  Thread " + i);
            thread.start();
        }
    }

    @Override
    public void execute(Runnable runnable) {
        synchronized (queue) {
            queue.offer(runnable);
        }
    }

    public void stop() {
        isRun = false;
    }

}
