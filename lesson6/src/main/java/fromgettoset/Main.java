package fromgettoset;

import java.lang.reflect.InvocationTargetException;

public class Main {
    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException {
        TestClass1 testClass1 = new TestClass1(new A(), new D(), "text1", 10);
        TestClass2 testClass2 = new TestClass2(new B(), new D(), "text2", 20.0);
        TestClass3 testClass3 = new TestClass3(new C(), new D(), 15, 25.0);
        System.out.println(testClass1);
        System.out.println(testClass2);
        System.out.println(testClass3);
        System.out.println("---------------------");
        BeanUtils.assign(testClass1, testClass2);
        BeanUtils.assign(testClass3, testClass2);
        System.out.println(testClass1);
        System.out.println(testClass2);
        System.out.println(testClass3);
    }
}
