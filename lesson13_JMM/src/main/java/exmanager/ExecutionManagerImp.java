package exmanager;

import java.util.Arrays;
import java.util.concurrent.CountDownLatch;

public class ExecutionManagerImp implements ExecutionManager{
    private final ScalableThreadPool threadPoolManager;//пул менеджера, запускает задачу создания Context при каждом вызове метода execute()
    private static int numberContext = 0; //номер последнего вызова метода execute()


    public ExecutionManagerImp(ScalableThreadPool threadPoolManager) {
        this.threadPoolManager = threadPoolManager;
        this.threadPoolManager.startPool();
    }

    public ExecutionManagerImp(){
        this(new ScalableThreadPool("Starting Contexts", 1));
    }

    //возвращаемое значение объект типа Context,
    //при каждом вызове данного метода создается новый обект,
    //через который можно узнать состояние выполнения группы задач
    @Override
    public Context execute(Runnable callback, Runnable... tasks) {
        //пул потоков выполняещий задачи конкретного контекста
        ScalableThreadPool threadPoolContext = new ScalableThreadPool("" + ++numberContext + " call Context", 5,5);

        Context context = new ContextImp(tasks.length+1, threadPoolContext);
        //защелка, отслежиает заверщение всех задач из массива tasks
        CountDownLatch cdl = new CountDownLatch(tasks.length);
        threadPoolManager.execute(()->{//в пул менеджера добавляется задача, суть которой "закинуть все задачи callback and tasks в пул Контекста,
                                        //добавив в них дополнительные проверки
            Arrays.stream(tasks).forEach(task-> threadPoolContext.execute(()->{
                try {
                    task.run(); //запускаем задачу в пуле Контекста
                    threadPoolContext.incrementCompletedTaskCount(); //в случае успешного завершения увеличиваем счетчик "Успешный задач"
                } catch (Exception e) {
                    threadPoolContext.incrementFailedTaskCount(); //в случае ошибки - счетчик "Безуспешных задач"
                } finally {
                    cdl.countDown(); //уменьшаем значение "защелки"
                }
            }));
            threadPoolContext.execute(()->{
                try {
                    cdl.await(); //ожидаение пока "защелка" не обнулится,
                    callback.run(); //после запускаем задачу callback
                    threadPoolContext.incrementCompletedTaskCount(); //в случае успешного завершения увеличиваем счетчик "Успешный задач"
                } catch (InterruptedException e) {
                    threadPoolContext.incrementInterruptedTaskCount(); //в случае запроса на прерывание по команде shutdown(),
                                                                        //увеличиваем счетчик прерванных задач
                    threadPoolContext.addInterruptedTask(callback);
                    Thread.currentThread().interrupt();
                } catch (Exception e) {
                    threadPoolContext.incrementFailedTaskCount(); //в случае ошибки - счетчик "Безуспешных задач"
                }
            });
        });

        return context;
    }
    //остановка пула Менеджера
    @Override
    public void shutdown() {
        threadPoolManager.shutdown();
    }
}
