package param_met;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class CollectionUtils {

    public static <T> void addAll(List<? extends T> source, List<? super T> destination) {
        destination.addAll(source);
    }

    public static <T> List<T> newArrayList() {
        return new ArrayList<>();
    }

    public static <T> int indexOf(List<T> source, Integer o) {
        return source.indexOf(o);
    }

    public static <T> List<T> limit(List<T> source, int size) {
        return source.subList(0, size);
    }
public static <T> void add(List<T> source, Object o) {
//    public static <T> void add(List<T> source, T o) {
        source.add((T)o);
    }

    public static <T> void removeAll(List<? super T> removeFrom, List<? extends T> c2) {
        removeFrom.removeAll(c2);
    }

    public static <T> boolean containsAll(List<? super T> c1, List<? extends T> c2) {
        return c1.containsAll(c2);
    }

    public static <T> boolean containsAny(List<? super T> c1, List<? extends T> c2) {
        List<? super T> list = new ArrayList<>(c1);
        return list.removeAll(c2);
    }

    public static <T> List<T> range(List<T> list, Object min, Object max) {
        return range(list, min, max, null);
    }

    public static <T> List<T> range(List<T> list, Object min, Object max, Comparator<? super T> comparator) {
        List<T> newlist = new ArrayList<>(list);
        newlist.sort(comparator);
//        return newlist.subList(newlist.indexOf(min), newlist.lastIndexOf(max)+1);
        if ((comparator == null) || (comparator.compare((T)min, (T)max) < 0))
            return newlist.subList(newlist.indexOf(min), newlist.lastIndexOf(max)+1);
        else
            return newlist.subList(newlist.indexOf(max), newlist.lastIndexOf(min)+1);
    }

}
