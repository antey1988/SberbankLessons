package exmanager;

public interface ExecutionManager {
    //Метод execute принимает массив тасков, это задания которые ExecutionManager должен выполнять параллельно
    //После завершения всех тасков должен выполниться callback (ровно 1 раз).
    Context execute(Runnable callback, Runnable... tasks);
    void shutdown();
}
