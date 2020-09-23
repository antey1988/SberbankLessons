

public class ConverDegrees {
    //  Calvin
    public static void CelsiusToCalvin(double temp) {
        System.out.println(temp + 273.15);
    }

    //Faringate
    public static void CelsiusToFaringate(double temp) {
        System.out.println(temp * 1.8 + 32);
    }

    public static void main(String[] args) {
        CelsiusToCalvin(0.85);
        CelsiusToFaringate(0);
        CelsiusToFaringate(-20);
    }
}
