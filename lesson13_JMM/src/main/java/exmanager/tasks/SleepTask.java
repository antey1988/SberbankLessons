package exmanager.tasks;

import java.util.concurrent.TimeUnit;

public class SleepTask implements Runnable {
    private final int time;
    private final TimeUnit timeUnit;
    private final String name;

    public SleepTask(int time, TimeUnit timeUnit, String name) {
        this.time = time;
        this.timeUnit = timeUnit;
        this.name = name;
    }

    @Override
    public void run() {
        try {
            timeUnit.sleep(time);

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println(name + ", starting in thread: "
                + Thread.currentThread().getName() + ", ended later " + time + " milliseconds");
    }

    @Override
    public String toString() {
        return name;
    }
}
