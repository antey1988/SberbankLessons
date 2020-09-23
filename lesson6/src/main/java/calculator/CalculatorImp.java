package calculator;

public class CalculatorImp implements Calculator {
    @Override
    public int calc(int number) {
        if (number == 0 || number == 1)
            return 1;
        return number*calc(number - 1);
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            System.out.println(new CalculatorImp().calc(i));
        }
    }
}
