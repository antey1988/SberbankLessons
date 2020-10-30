import java.util.List;

public interface Calculator {
    @Cachable(H2BDSource.class)
    int factorial(int n);

    @Cachable(H2BDSource.class)
    List<Integer> fibonachi(int n);
}
