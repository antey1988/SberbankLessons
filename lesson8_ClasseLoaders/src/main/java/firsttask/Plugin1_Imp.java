package firsttask;

public class Plugin1_Imp implements Plugin{
    @Override
    public void doUsefull() {
        System.out.println("Class " + toString() + ", method interface");
    }

    public void methodClass1() {
        System.out.println("Class " + toString() + ", method class");
    }
 }
