package interfaces;

import java.sql.Connection;
import java.sql.ResultSet;

public interface ManagerDB {
    void openConnection(String JDBC_Driver, String DateBase_URL);
    void closeConnection();
    ResultSet selectAllFromTable(String table);
}
