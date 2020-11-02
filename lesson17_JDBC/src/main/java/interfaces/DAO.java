package interfaces;

import java.sql.ResultSet;
import java.util.List;
import java.util.Map;

public interface DAO {
    Map<Integer, List<Integer>> getMapObject(ResultSet resultSet);
}
