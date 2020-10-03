package secondtask;

public class Main {
    public static void main(String[] args) {
        MyLambda<String> myLambda1 = (a, b) -> a + b;
        MyLambda<Integer> myLambda2 = (a, b) -> a + b;
        System.out.println(myLambda1.getSum("abc", "def"));
        System.out.println(myLambda2.getSum(1, 1));

    }
}
