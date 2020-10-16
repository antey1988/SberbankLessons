import exmanager.Context;
import exmanager.ExecutionManager;
import exmanager.ExecutionManagerImp;
import exmanager.tasks.ExceptionTask;
import exmanager.tasks.SleepTask;
import org.junit.Test;
import org.junit.Assert;

import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

public class ExecutionManagerTest {
    static final int COUNTTASKS_1 = 10;
    static final int COUNTTASKS_2 = 15;
    @Test
    public void testExecute(){
        ExecutionManager executionManager = new ExecutionManagerImp();
        //задачи tasks
        Runnable [] firstGroupTask = generateTasks("First Group", COUNTTASKS_1);
        //задача callback
        Runnable firstCallback = new SleepTask(500, "First Callback");
        //задачи tasks
        Runnable [] secondGroupTask = generateTasks("Second Group", COUNTTASKS_2);
        //задача callback
        Runnable secondCallback = new SleepTask(1000, "Second Callback");


        Context context1 = executionManager.execute(firstCallback,firstGroupTask);
        Context context2 = executionManager.execute(secondCallback,secondGroupTask);

        sleepMain(2);
//        sleepMain(2);
        context1.interrupt();
        sleepMain(2);
        context2.interrupt();
        checkContext(context1);
        checkContext(context2);
        executionManager.shutdown();
        Assert.assertNotEquals(COUNTTASKS_1,
                context1.getCompletedTaskCount() + context1.getFailedTaskCount() + context1.getInterruptedTaskCount());
        Assert.assertEquals(COUNTTASKS_1+1,
                context1.getCompletedTaskCount() + context1.getFailedTaskCount() + context1.getInterruptedTaskCount());
    }

    private Runnable[] generateTasks(final String groupName, final int count) {
        return Stream.iterate(1, t-> t+1).limit(count)
                .map(t-> {
                    Runnable runnable;
                    if ((int)(count * Math.random() + 1)  > count/2)
                        //задачи проснуться через определенное время
                        runnable = new SleepTask(1000 + (int)(Math.random()*1000), "" + groupName + " Tasks (task sleep #" + t + ")");
                    else
                        //задачи, бросающие ошибки
                        runnable = new ExceptionTask("" + groupName + " Tasks (task exception #" + t + ")");
                    return runnable;
                })
                .toArray(Runnable[]::new);
    }

    private void checkContext(Context context) {
        System.out.println("Все ли задачи отработали?: " + (context.isFinished() ? "да" : "нет"));
        System.out.println("Количество успешно завершенных задача: " + context.getCompletedTaskCount());
        System.out.println("Количество прерванных задача: " + context.getInterruptedTaskCount());
        System.out.println("Количество задача, завершенных с ошибкой: " + context.getFailedTaskCount());
    }

    private void sleepMain(int seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}