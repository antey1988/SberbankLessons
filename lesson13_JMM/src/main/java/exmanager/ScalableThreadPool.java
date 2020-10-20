package exmanager;

import java.util.List;
import java.util.Set;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class ScalableThreadPool {
    private final String namePool; //имя пула
    private final AtomicInteger currentCountAdditionaThread = new AtomicInteger();//счетчик запущенных дополнительных потоков
    private final int minCountThread; //минимальное количество потоков в пуле (основые потоки, запускаются сразу при старте пула)
    private final int maxCountThread; //максимальное количество потоков в пуле (дополнительные потоки запускаются при наличии задач и занятых основных)
    private final BlockingQueue<Runnable> queueTask;//очередь задач
    private final List<Runnable> interruptTask;//список прерванных задач
    private final Set<Thread> activeThread;//реестр запущенных потоков, используется для остановки выполняющихся потоков по команде shutdown()
    private volatile boolean isShutdown;//флаг полной остановки пула,  потоки сразу же завершают работу при старте

    private final AtomicInteger completedTaskCount = new AtomicInteger(); //счетчик успешно завершенных задач
    private final AtomicInteger failedTaskCount = new AtomicInteger(); //счетчик задач, завершенных с ошибкой
    private final AtomicInteger interruptedTaskCount = new AtomicInteger(); //счетчик прерванных задач

    public ScalableThreadPool(String name) {
        this(name, 0, Integer.MAX_VALUE);
    }

    public ScalableThreadPool(String name, int minCountThread) {
        this(name, minCountThread, minCountThread);
    }

    public ScalableThreadPool(String namePool, int minCountThread, int maxCountThread) {
        this.namePool = namePool;
        this.minCountThread = minCountThread;
        this.maxCountThread = maxCountThread;
        this.queueTask = new LinkedBlockingQueue<>();
        this.interruptTask = new CopyOnWriteArrayList<>();
        this.activeThread = new CopyOnWriteArraySet<>();
        this.isShutdown = false;
    }

    //возвращает количество успешно завершенных задач
    public int getCurrentCompletedTaskCount() {
        return completedTaskCount.get();
    }
    //инкрементирует счетчик успешно завершенных задач
    public void incrementCompletedTaskCount() {
        completedTaskCount.incrementAndGet();
    }
    //возвращает количество задач, завершенных с ошибкой
    public int getCurrentFailedTaskCount() {
        return failedTaskCount.get();
    }
    //инкрементирует задач, завершенных с ошибкой
    public void incrementFailedTaskCount() {
        failedTaskCount.incrementAndGet();
    }
    //возвращает количество прерванных задач
    public int getCurrentInterruptedTaskCount() {
        return interruptedTaskCount.get();
    }
    //инкрементирует счетчик прерванных задач
    public void incrementInterruptedTaskCount() {
        int count = interruptedTaskCount.incrementAndGet();
//        System.out.println("Количество прерванных задач из потока в пуле потоков (" + namePool + "): "  + count);
    }

    public void addInterruptedTask(Runnable runnable) {
        interruptTask.add(runnable);
    }



    public void shutdown() {
        //выставляем флаг остановки пула
        isShutdown = true;
        //выставляем флаги прерывания у всех активных потоков
        activeThread.forEach(Thread::interrupt);
        //увеличиваем значение счетчика прерванных задач на количество задач, оставшихся в очереди
        int count = interruptedTaskCount.addAndGet(queueTask.size());
//        System.out.println("Количество прерванных задач из очереди в пуле потоков (" + namePool + "): "  + count);
        interruptTask.addAll(queueTask);
    }
    //запуск основных потоков, работающие постоянно
    private void startPrimaryThread(int count) {
        for (int i = 1; i < count+1; i++) {
            if (isShutdown) // прерываем запуск оставшихся основных потоков
                break;
            Thread thread = new Thread(() -> {
                if (registrationThread(Thread.currentThread())) { //если удалось зарегистрироваться в реестре
                    while (!Thread.currentThread().isInterrupted()) {//начинаем обрабатывать задачи пока не придет команда shutdown()
                        Runnable runnable;
                        try {
                            runnable = queueTask.take(); //извлечение задачи из очереди
                            runnable.run();

                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                    activeThread.remove(Thread.currentThread()); //удаляемся из реестра
                }
            }, "Pool (" + namePool + ") - Thread (Primary thread № " + i + ")");
            thread.start();
        }
    }
    //старт сервисного потока, запускающего дополнительные потоки
    private void startAdditionalThread(int maxCountAdditionaThread) {
            Thread thread = new Thread(() -> {  //сервесный поток, который будет запускать дополнительные потоки
                                                // при наличии задач в очереди и занятых основных потоков
                if (registrationThread(Thread.currentThread())) {
                    while (!Thread.currentThread().isInterrupted()) {
                        if (currentCountAdditionaThread.intValue() < maxCountAdditionaThread) { //проверка на "наличие" дополнительных потоков
//                        Runnable runnable = queueTask.poll();//извлечение задачи из очереди
                            Runnable runnable;
                            try {
                                runnable = queueTask.take(); //извлечение задачи из очереди
                                currentCountAdditionaThread.getAndIncrement(); //инкремент счетчика дополнительных потоков
                                Thread thread1 = new Thread(() -> { //дополнительный поток
                                    if (registrationThread(Thread.currentThread())) { //если удалось зарегестрироваться в реестре
                                        runnable.run();                                 //выполняем задачу полностью
                                        currentCountAdditionaThread.decrementAndGet(); //декремент счетчика дополнительных потоков
                                        activeThread.remove(Thread.currentThread());//удаляемся из реестра
                                    } else {                                         //задача прервана по команде shutdown()
                                        incrementInterruptedTaskCount();
                                        interruptTask.add(runnable);
                                    }
                                }, "Pool (" + namePool + ") - Thread (Additional thread)");
                                thread1.start();
                            } catch (InterruptedException e) {
                                Thread.currentThread().interrupt();
                            }
                        }
                    }
                    activeThread.remove(Thread.currentThread());//удаляемся из реестра
                }
            }, "Pool (" + namePool + ") - Thread (Service thread");
            thread.start();
    }
    //запуск пула
    public void startPool() {
        if (minCountThread >= 0) {
            startPrimaryThread(minCountThread);
            int countAdditionalThread = maxCountThread - minCountThread;
            if (countAdditionalThread > 0)
                startAdditionalThread(countAdditionalThread);
        }
    }
    //добавление задачи в очередь
    public void execute(Runnable runnable) {
        queueTask.offer(runnable);
    }

    //регистрация в реесте активных потоков, если не пришла команда об остановке пула
    private boolean registrationThread(Thread thread) {
        synchronized (activeThread) {
            if (!isShutdown) {
                activeThread.add(thread);
                return true;
            }
        }
        return false;
    }

}
