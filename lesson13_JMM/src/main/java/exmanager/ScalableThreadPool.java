package exmanager;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class ScalableThreadPool {
    private final String namePool;
    private final AtomicInteger currentCountAdditionaThread = new AtomicInteger();//текущее количество запущенных дополнительных потоков
    private final int minCountThread;
    private final int maxCountThread;
    private final BlockingQueue<Runnable> queueTask;//очередь задач
    private final CopyOnWriteArraySet<Thread> activeThread;//реестр запущенных потоков

    private final AtomicInteger completedTaskCount = new AtomicInteger();
    private final AtomicInteger failedTaskCount = new AtomicInteger();
    private final AtomicInteger interruptedTaskCount = new AtomicInteger();

    private final Object lock = new Object();


    public ScalableThreadPool(String name) {
        this(name, 0, Integer.MAX_VALUE);
    }

    public ScalableThreadPool(String name, int minCountThread) {
        this(name, minCountThread, minCountThread);
    }

    public AtomicInteger getCurrentCompletedTaskCount() {
        return completedTaskCount;
    }
    public void incrementCompletedTaskCount() {
        synchronized (lock) {
            completedTaskCount.incrementAndGet();
        }
    }
    public AtomicInteger getCurrentFailedTaskCount() {
        return failedTaskCount;
    }
    public void incrementFailedTaskCount() {
        synchronized (lock) {
            failedTaskCount.incrementAndGet();
        }
    }
    public AtomicInteger getCurrentInterruptedTaskCount() {
        return interruptedTaskCount;
    }
    public void incrementInterruptedTaskCount() {
        synchronized (lock) {
            interruptedTaskCount.incrementAndGet();
        }
    }

    public ScalableThreadPool(String namePool, int minCountThread, int maxCountThread) {
        this.namePool = namePool;
        this.minCountThread = minCountThread;
        this.maxCountThread = maxCountThread;
        this.queueTask = new LinkedBlockingQueue<>();
        this.activeThread = new CopyOnWriteArraySet<>();
    }

    public void shutdown() {
        activeThread.forEach(Thread::interrupt);
        synchronized (lock) {
            interruptedTaskCount.addAndGet(queueTask.size());
        }
    }
    //запуск основных потоков, работающие постоянно
    private void startPrimaryThread(int count) {
        for (int i = 1; i < count+1; i++) {
            Thread thread = new Thread(() -> {
                activeThread.add(Thread.currentThread());
                while (!Thread.currentThread().isInterrupted()) {
                    Runnable runnable = queueTask.poll();
                    if (runnable != null)
                        runnable.run();
                }
                activeThread.remove(Thread.currentThread());
            }, "Pool (" + namePool + ") - Thread (Primary thread № " + i + ")");
            thread.start();
        }
    }
    //старт сервисного потока, запускающего дополнительные потоки
    private void startAdditionalThread(int maxCountAdditionaThread) {
            Thread thread = new Thread(() -> {
                activeThread.add(Thread.currentThread());
                while (!Thread.currentThread().isInterrupted()) {
                    if (currentCountAdditionaThread.intValue() < maxCountAdditionaThread) {
                        Runnable runnable = queueTask.poll();
                        if (runnable != null) {
                            currentCountAdditionaThread.getAndIncrement();
                            Thread thread1 = new Thread(() -> {
                                activeThread.add(Thread.currentThread());
                                runnable.run();
                                currentCountAdditionaThread.decrementAndGet();
                                activeThread.remove(Thread.currentThread());
                            }, "Pool (" + namePool + ") - Thread (Additional thread №" + currentCountAdditionaThread + ")");
                            thread1.start();
                        }
                    }
                }
                activeThread.remove(Thread.currentThread());
            }, namePool + " pool - Service thread");
            thread.start();
    }

    public void start() {
        if (minCountThread >= 0) {
            startPrimaryThread(minCountThread);
            int countAdditionalThread = maxCountThread - minCountThread;
            if (countAdditionalThread > 0)
                startAdditionalThread(countAdditionalThread);
        }
    }

    public void execute(Runnable runnable) {
        queueTask.offer(runnable);
    }
}
