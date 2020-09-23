package fromgettoset;

public class TestClass1 {
    private A a;
    private D d;
    private String text;
    private int number;

    public TestClass1(A a, D d, String text, int number) {
        this.a = a;
        this.d = d;
        this.text = text;
        this.number = number;
    }

    public A getA() {
        return a;
    }

    public void setA(A a) {
        this.a = a;
    }

    public D getD() {
        return d;
    }

    public void setD(D d) {
        this.d = d;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "TestClass1{" +
                "a=" + a +
                ", d=" + d +
                ", text='" + text + '\'' +
                ", number=" + number +
                '}';
    }
}
