import interfaces.Cachable;

import java.util.List;

public interface Calculator {
    int factorial(int n);

    @Cachable(H2BDSource.class)
    List<Integer> fibonachi(int n);
}
