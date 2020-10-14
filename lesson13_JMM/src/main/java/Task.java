import java.util.concurrent.Callable;

public class Task<T>{
    private volatile T result;  //переменная, в которой хранится результат первого вызова метода get в случае его успешного запршения
    private volatile RuntimeException runtimeException; //переменная, в которой хранится исключение, возникщее при первом выхове метода get
    private final Callable<T> callable;

    public Task(Callable<? extends T> callable) {
        this.callable = (Callable<T>) callable;
    }

    public T get() {
        if ((result == null) && (runtimeException == null)) { //проверяем не вызывался ли данный метод ранее
            synchronized (this) {
                if ((result == null) && (runtimeException == null)) { //проверяем не вызывался ли данный метод ранее, после снятия блокировки
                    try {
                        result = callable.call();
                    } catch (Exception e) {
                        runtimeException = new ExInGetRuntimeException(e);
                        throw runtimeException;
                    }
                } else if (runtimeException != null) //метод вызывался другим потоком, который первый заблокировал объект
                    throw runtimeException;          // и завершился брошенным исключением
            }
        }   else if (runtimeException != null) //метод ранее вызывался и завершился брошенным исключением
            throw  runtimeException;
        return  result; //результат успешного завершения метода
    }
}
