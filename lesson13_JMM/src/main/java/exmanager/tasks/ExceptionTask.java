package exmanager.tasks;

public class ExceptionTask implements Runnable{
    private final String name;

    public ExceptionTask(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        System.out.println(name + ", starting in thread: "
                + Thread.currentThread().getName() + ", ended with fail");
        throw new RuntimeException();
    }
}
