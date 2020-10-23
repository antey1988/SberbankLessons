package exmanager;

import exmanager.tasks.ExceptionTask;
import exmanager.tasks.SleepTask;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

public class Main {
    static final int COUNTTASKS_1 = 9;
    static final int COUNTTASKS_2 = 14;
    static final int COUNTTASKS_3 = 19;

    public static void main(String[] args) {
        ExecutionManager executionManager = new ExecutionManagerImp();
        //задачи tasks
        Runnable [] firstGroupTask = generateTasks("First Group", COUNTTASKS_1);
        //задача callback
        Runnable firstCallback = new SleepTask(500, TimeUnit.MILLISECONDS, "First Callback");
        //задачи tasks
        Runnable [] secondGroupTask = generateTasks("Second Group", COUNTTASKS_2);
        //задача callback
        Runnable secondCallback = new SleepTask(1000, TimeUnit.MILLISECONDS, "Second Callback");
        //задачи tasks
        Runnable [] thirdGroupTask = generateTasks("Third Group", COUNTTASKS_3);
        //задача callback
        Runnable thirdCallback = new SleepTask(1000, TimeUnit.MILLISECONDS, "Third Callback");


        Context context1 = executionManager.execute(firstCallback,firstGroupTask);
        Context context2 = executionManager.execute(secondCallback,secondGroupTask);
        Context context3 = executionManager.execute(thirdCallback,thirdGroupTask);

        sleepMain(1400, TimeUnit.MILLISECONDS);
//        sleepMain(2);
        context1.interrupt();
        sleepMain(1650, TimeUnit.MILLISECONDS);
        context2.interrupt();
        sleepMain(2, TimeUnit.SECONDS);
        context3.interrupt();
        sleepMain(1, TimeUnit.SECONDS);
        checkContext(context1);
        checkContext(context2);
        checkContext(context3);
        executionManager.shutdown();
    }

    private static Runnable[] generateTasks(final String groupName, final int count) {
        return Stream.iterate(1, t-> t+1).limit(count)
                .map(t-> {
                    Runnable runnable;
                    if ((int)(count * Math.random() + 1)  >= count/5)
                        //задачи проснуться через определенное время
                        runnable = new SleepTask(450 + (int)(Math.random()*450), TimeUnit.MILLISECONDS, "" + groupName + " Tasks (task sleep #" + t + ")");
                    else
                        //задачи, бросающие ошибки
                        runnable = new ExceptionTask("" + groupName + " Tasks (task exception #" + t + ")");
                    return runnable;
                })
                .toArray(Runnable[]::new);
    }

    private static void checkContext(Context context) {
        System.out.println("\nВсе ли задачи отработали?: " + (context.isFinished() ? "да" : "нет"));
        System.out.println("Количество успешно завершенных задача: " + context.getCompletedTaskCount());
        System.out.println("Количество прерванных задача: " + context.getInterruptedTaskCount());
        System.out.println("Количество задача, завершенных с ошибкой: " + context.getFailedTaskCount());
    }

    private static void sleepMain(int time, TimeUnit timeUnit) {
        try {
            timeUnit.sleep(time);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
