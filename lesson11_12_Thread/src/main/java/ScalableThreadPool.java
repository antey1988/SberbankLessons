import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ScalableThreadPool implements ThreadPool {
        private final String namePool;
        private volatile boolean isRun = true;
        private volatile int currentCount = 0;
        private final Object lock = new Object();
        private final int minCountThread;
        private final int maxCountThread;
        private final BlockingQueue<Runnable> queue;

    public ScalableThreadPool(String namePool, int minCountThread, int maxCountThread) {
        this.namePool = namePool;
        this.minCountThread = minCountThread;
        this.maxCountThread = maxCountThread;
        this.isRun = true;
        this.queue = new LinkedBlockingQueue<>();
    }

    public int countInQueue() {
            return queue.size();
        }

    public void stop() {
        isRun = false;
    }
//запуск основных потоков, выполняющих задачи из очереди
    private void startPrimaryThread(int count) {
        for (int i = 1; i < count+1; i++) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (isRun) {
                        Runnable runnable = queue.poll();
                        if (runnable != null)
                            runnable.run();
                    }
                }
            }, namePool + " pool - Primary thread № " + i);
            thread.start();
        }
    }
//старт вспомогательного потока, который который запускает дополнительные потоки при наличии задач в очереди
    private void startAdditionalThread(int count) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (isRun) {
                    if (currentCount < count) {
                        Runnable runnable = queue.poll();
                        if (runnable != null) {
                            synchronized (lock) {
                                currentCount++;
                            }
                            Thread thread = new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    runnable.run();
                                    synchronized (lock) {
                                        --currentCount;
                                    }
                                }
                            }, namePool + " pool - Additional thread № " + currentCount);
                            thread.start();
                        }
                    }
                }
            }}).start();
    }

    @Override
    public void start() {
        startPrimaryThread(minCountThread);
        startAdditionalThread(maxCountThread-minCountThread);
    }



    @Override
    public void execute(Runnable runnable) {
        queue.offer(runnable);
    }
}
