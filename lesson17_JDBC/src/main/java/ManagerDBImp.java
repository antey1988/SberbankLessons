import interfaces.ManagerDB;

import java.sql.*;

public class ManagerDBImp implements ManagerDB {
    private Connection connection;
    private Statement statement;
    @Override
    public void openConnection(String JDBC_Driver, String DateBase_URL) {
        try {
            Class.forName(JDBC_Driver);
            connection = DriverManager.getConnection(DateBase_URL);
            statement = connection.createStatement();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void closeConnection() {
        try {
            statement.close();
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public ResultSet selectAllFromTable(String table) {
        ResultSet resultSet = null;
        String SQL = "SELECT * FROM " + table;
        try {
            resultSet = statement.executeQuery(SQL);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return resultSet;
    }


}
