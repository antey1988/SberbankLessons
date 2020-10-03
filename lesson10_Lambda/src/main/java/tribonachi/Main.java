package tribonachi;

import java.util.function.IntUnaryOperator;
import java.util.stream.IntStream;

public class Main {
    private static final int n = 4;

    public static void main(String[] args) {
        IntStream intStream = IntStream.range(0, n).map(new Tribonachi());
        intStream.forEach(System.out::println);
    }
}
