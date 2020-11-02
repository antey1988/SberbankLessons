import interfaces.DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class DAOImp implements DAO {
    @Override
    public Map<Integer, List<Integer>> getMapObject(ResultSet resultSet) {
        Map<Integer, List<Integer>> values = new ConcurrentHashMap<>();
        List<Integer> list = new ArrayList<>();
        try {
            while(resultSet.next()) {
                list.add(resultSet.getInt(2));
                values.put(resultSet.getInt(1), new ArrayList<>(list));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return values;
    }
}
