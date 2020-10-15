package exmanager;

import java.util.concurrent.atomic.AtomicInteger;

public class ContextImp implements Context {
    private final int fullCount;
    private final ScalableThreadPool threadPool;

    public ContextImp(int fullCount, ScalableThreadPool threadPool) {
        this.fullCount = fullCount;
        this.threadPool = threadPool;
        this.threadPool.start();
    }

    @Override
    public int getCompletedTaskCount() {
        return threadPool.getCurrentCompletedTaskCount().get();
    }

    @Override
    public int getFailedTaskCount() {
        return threadPool.getCurrentFailedTaskCount().get();
    }

    @Override
    public int getInterruptedTaskCount() {
        return threadPool.getCurrentInterruptedTaskCount().get();
    }

    @Override
    public void interrupt() {
        threadPool.shutdown();
    }

    @Override
    public boolean isFinished() {
        return getCompletedTaskCount() + getInterruptedTaskCount() == fullCount;
    }
}
