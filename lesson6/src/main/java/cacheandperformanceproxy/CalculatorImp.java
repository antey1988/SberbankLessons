package cacheandperformanceproxy;

public class CalculatorImp implements Calculator {
    @Override
    public int calc1(int number) {
        try {
            Thread.sleep(800);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return number*number;
    }

    @Override
    public int calc2(int number) {
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return number + number;
    }
}
