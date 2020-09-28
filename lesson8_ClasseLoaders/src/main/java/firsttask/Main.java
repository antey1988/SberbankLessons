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
        System.out.println("Class " + p1.getClass().getClassLoader().toString());

        Plugin p4 = new Plugin2_Imp();
        p1.doUsefull();
        System.out.println("Class " + p4.getClass().getClassLoader().toString());

        Plugin p2 = pluginManager.load("FirstPlugin", "Plugin1_Imp");
        p2.doUsefull();
        System.out.println("Class " + p2.getClass().getClassLoader().toString());

        Plugin p3 = pluginManager.load("SecondPlugin", "Plugin2_Imp");
        p3.doUsefull();
        System.out.println("Class " + p3.getClass().getClassLoader().toString());
    }
}
