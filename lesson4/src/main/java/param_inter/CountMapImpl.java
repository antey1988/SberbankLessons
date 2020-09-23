package param_inter;

import java.util.HashMap;
import java.util.Map;

public class CountMapImpl<T> implements CountMap<T> {
    //мапа, ключ - добовляемые обекты, значение - количество добавлений
    private Map<T, Integer> map = new HashMap<>();

    @Override
    public void add(Object o) {
    //извлекаем значение из мапы и инкрементируем его
        int value = map.getOrDefault(o, 0);
        map.put((T)o, ++value);
    }

    @Override
    public int getCount(Object o) {
    //возвращаем значение по ключу
        return map.getOrDefault(o, 0);
    }

    @Override
    public int remove(Object o) {
        return map.remove(o);
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public void addAll(CountMap<T> source) {
        source.toMap().forEach((k, v) -> {
//           получаем значение из заполняемой мапы, увеличиваем его на значение из заполняющей
            int value = map.getOrDefault(k, 0);
            map.put(k, value + v);
        });
    }

    @Override
    public Map<T, Integer> toMap() {
        return map;
    }

    @Override
    public void toMap(Map<T, Integer> destination) {
        map.forEach(destination::put);
//        destination = map;
    }
}
