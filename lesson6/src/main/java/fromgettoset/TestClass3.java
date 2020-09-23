package fromgettoset;

public class TestClass3 {
    private C c;
    private D d;
    private int number1;
    private double number2;

    public TestClass3(C c, D d, int number1, double number2) {
        this.c = c;
        this.d = d;
        this.number1 = number1;
        this.number2 = number2;
    }

    public C getC() {
        return c;
    }

    public void setC(C c) {
        this.c = c;
    }

    public D getD() {
        return d;
    }

    public void setD(D d) {
        this.d = d;
    }

    public int getNumber1() {
        return number1;
    }

    public void setNumber1(int number) {
        this.number1 = number;
    }

    public double getNumber2() {
        return number2;
    }

    public void setNumber2(double number2) {
        this.number2 = number2;
    }

    @Override
    public String toString() {
        return "TestClass3{" +
                "c=" + c +
                ", d=" + d +
                ", number1=" + number1 +
                ", number2=" + number2 +
                '}';
    }
}
