import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class BDSource extends Source{
    static final String DATABASE_URL = "jdbc:h2:" +
            "/home/oleg/IdeaProjects/SberbankMaven/lesson17_JDBC/src/main/resources/cache";
    static final String JDBC_DRIVER = "org.h2.Driver";

    public static void main(String[] args) {
        try {
            Class.forName(JDBC_DRIVER);
            Connection connection = DriverManager.getConnection(DATABASE_URL);
            connection.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}
