import interfaces.Source;

public class H2BDSource implements Source {
    /*static final String DATABASE_URL = "jdbc:h2:" +
            "/home/oleg/IdeaProjects/SberbankMaven/lesson17_JDBC/src/main/resources/cache";
    static final String JDBC_DRIVER = "org.h2.Driver";*/

    @Override
    public String get_Database_URL() {
        return "jdbc:h2:" +
                "/home/oleg/IdeaProjects/SberbankMaven/lesson17_JDBC/src/main/resources/cache";
    }

    @Override
    public String get_JDBC_Driver() {
        return "org.h2.Driver";
    }
}
