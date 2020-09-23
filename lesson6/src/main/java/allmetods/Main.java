package allmetods;

public class Main {
    public static void main(String[] args) {
        Aclass aclass = new Cclass();
        MethodsClass.printAllMetods(aclass, true);
        System.out.println("-------------------------");
        MethodsClass.printAllMetods(aclass, false);
    }
}
