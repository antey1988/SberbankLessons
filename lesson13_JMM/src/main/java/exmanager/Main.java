package exmanager;

import exmanager.tasks.*;

import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

public class Main {
    static final int COUNTTASKS = 30;
    public static void main(String[] args) {
        ExecutionManager executionManager = new ExecutionManagerImp();
        Runnable [] firstGroupTask = Stream.iterate(1, t-> t+1).limit(COUNTTASKS)
                .map(t-> {
                    Runnable runnable;
                    if ((int)(COUNTTASKS * Math.random() + 1)  > COUNTTASKS/2)
                        //задачи проснуться через определенное время
                        runnable = new SleepTask(1000 + (int)(Math.random()*1000), "First Group Tasks (task sleep #" + t + ")");
                    else
                        //задачи, бросающие ошибки
                        runnable = new ExceptionTask("First Group Tasks (task exception #" + t + ")");
                    return runnable;
                })
                .toArray(Runnable[]::new);
        //задача callback
        Runnable firstCallback = new SleepTask(500, "First Callback");

        Context context = executionManager.execute(firstCallback,firstGroupTask);

        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("Все ли задачи отработали?: " + (context.isFinished() ? "да" : "нет"));
        System.out.println("Количество успешно завершенных задача: " + context.getCompletedTaskCount());
        System.out.println("Количество прерванных задача: " + context.getInterruptedTaskCount());
        System.out.println("Количество задача, завершенных с ошибкой: " + context.getFailedTaskCount());

        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        context.interrupt();

        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("Все ли задачи отработали?: " + (context.isFinished() ? "да" : "нет"));
        System.out.println("Количество успешно завершенных задача: " + context.getCompletedTaskCount());
        System.out.println("Количество прерванных задача: " + context.getInterruptedTaskCount());
        System.out.println("Количество задача, завершенных с ошибкой: " + context.getFailedTaskCount());

        executionManager.shutdown();
    }
}
