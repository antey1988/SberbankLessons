import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CalculatorImp implements Calculator {
    @Override
    public int factorial(int n) {
        return (n == 0 || n ==1) ? 1 : n*factorial(n-1);
    }

    @Override
    public List<Integer> fibonachi(int n) {
        try {
            Thread.currentThread().sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return Stream.iterate(new int[] {0,1},t -> new int[] {t[1], t[1] + t[0]}).limit(n).map(t->t[0]).collect(Collectors.toList());
    }

    public static void main(String[] args) {
        Calculator calc = new CalculatorImp();
        Calculator cacheCalc = CacheProxy.cache(calc);
//        System.out.println(cacheCalc.factorial(5));
        System.out.println(cacheCalc.fibonachi(8));
    }
}
