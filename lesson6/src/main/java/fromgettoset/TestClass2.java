package fromgettoset;

public class TestClass2 {
    private B b;
    private D d;
    private String text;
    private double number;

    public TestClass2(B b, D d, String text, double number) {
        this.b = b;
        this.d = d;
        this.text = text;
        this.number = number;
    }

    public B getA() {
        return b;
    }

    public void setA(B b) {
        this.b = b;
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

    public double getNumber() {
        return number;
    }

    public void setNumber(double number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "TestClass2{" +
                "b=" + b +
                ", d=" + d +
                ", text='" + text + '\'' +
                ", number=" + number +
                '}';
    }
}
