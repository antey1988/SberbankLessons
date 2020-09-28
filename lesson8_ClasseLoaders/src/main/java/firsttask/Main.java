package firsttask;

public class Main {
    public static void main(String[] args) throws IllegalAccessException, InstantiationException, ClassNotFoundException {
//        E:\User\Oleg\IdeaProjects\SberbankMaven\lesson8_ClasseLoaders\src\main\resources\RootDirectory\FirstPlugin
//        E:\User\Oleg\IdeaProjects\SberbankMaven\lesson8_ClasseLoaders\src\main\resources\RootDirectory\SecondPlugin
        PluginManager pluginManager =
//                new PluginManager("E:\\User\\Oleg\\IdeaProjects\\SberbankMaven\\lesson8_ClasseLoaders\\src\\main\\resources\\RootDirectory");
                new PluginManager("/home/oleg/IdeaProjects/SberbankMaven/lesson8_ClasseLoaders/src/main/resources/RootDirectory");

        Plugin p1 = new Plugin1_Imp();
        p1.doUsefull();
        ((Plugin1_Imp)p1).methodClass1();
        System.out.println("Class " + p1.getClass().getClassLoader().toString());

        Plugin p2 = new Plugin2_Imp();
        p2.doUsefull();
        ((Plugin2_Imp)p2).methodClass2();
        System.out.println("Class " + p2.getClass().getClassLoader().toString());

        Plugin p3 = pluginManager.load("FirstPlugin", "Plugin1_Imp");
        p3.doUsefull();
        System.out.println("Class " + p3.getClass().getClassLoader().toString());

        Plugin p5 = pluginManager.load("SecondPlugin", "Plugin2_Imp");
        p5.doUsefull();
        System.out.println("Class " + p5.getClass().getClassLoader().toString());
    }
}
