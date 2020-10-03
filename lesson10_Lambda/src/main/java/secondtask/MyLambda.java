package secondtask;

import java.util.function.BinaryOperator;

@FunctionalInterface
public interface MyLambda<T> {
    T getSum(T t1, T t2);
}
