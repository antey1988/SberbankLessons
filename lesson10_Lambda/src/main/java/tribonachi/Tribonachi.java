package tribonachi;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Tribonachi {

    public static List<Integer> getNumber(int number) {
        Stream<int[]> stream = Stream.iterate(new int[] {0,0,1}, n->new int[] {n[1], n[2], n[2] + n[1] + n[0]}).limit(number);
//        stream.forEach(n -> System.out.println("{" + n[0] + ", " + n[1] +  ", " + n[2] + "}"));
        return stream.map(n -> n[0]).collect(Collectors.toList());
    }

    public static void main(String[] args) {
        getNumber(1).forEach(System.out::println);
        System.out.println();
        getNumber(2).forEach(System.out::println);
        System.out.println();
        getNumber(3).forEach(System.out::println);
        System.out.println();
        getNumber(13).forEach(System.out::println);
    }
}
