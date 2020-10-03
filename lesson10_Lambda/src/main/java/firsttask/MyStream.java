package firsttask;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

public class MyStream {
    private List<Integer> values;

    public static MyStream of(Integer ... values) {
        MyStream myStream = new MyStream();
        myStream.values = Arrays.asList(values);
        return myStream;
    }

    public void forEach(Consumer<Integer> consumer) {
        for(Integer integer : values)
            consumer.accept(integer);
    }

    public MyStream filter(Predicate<Integer> predicate) {
        List<Integer> newValues = new ArrayList<>();
        for(Integer integer : values)
            if (predicate.test(integer)) newValues.add(integer);
        values = newValues;
        return this;

    }

    public MyStream map(UnaryOperator<Integer> unaryOperator) {
        List<Integer> newValues = new ArrayList<>();
        for(Integer integer : values)
            newValues.add(unaryOperator.apply(integer));
        values = newValues;
        return this;
    }

    public MyStream distinct() {
        List<Integer> newValues = new ArrayList<>();
        for(Integer integer : values) {
            if (!newValues.contains(integer))
                newValues.add(integer);
        }
        values = newValues;
        return this;
    }

    public static void main(String[] args) {
        MyStream stream = MyStream.of(1, 2, 2, 3, 4, 5, 6, 7, 8, 8, 9).filter(n->n%2 == 0).map(i->i*i).distinct();
        stream.forEach(System.out::println);
    }
}
