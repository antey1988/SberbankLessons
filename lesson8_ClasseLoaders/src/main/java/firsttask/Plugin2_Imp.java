package firsttask;

public class Plugin2_Imp implements Plugin{
    @Override
    public void doUsefull() {
        System.out.println("Class " + toString() + ", method interface");
    }

    public void methodClass2() {
        System.out.println("Class " + toString() + ", method class");
    }
}
