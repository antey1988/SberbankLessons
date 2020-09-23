
import java.lang.reflect.Array;
import java.util.Arrays;

public class BobbleSort {
    public static void sort(int [] array) {
        for (int i = array.length - 1; i > 0; i--) {
            boolean flag = true;
            for (int j = 0; j < i; j++) {
                if (array[j] > array[j+1]) {
                    int x = array[j];
                    array[j] = array[j+1];
                    array[j+1] = x;
                    flag = false;
                }
            }
//            BobbleSort.printArray(array);
            if (flag) break;
        }
    }

    public static void main(String[] args) {
        int[] ar = {4, 3, 1, 2, 2};
//        int[] ar = {1, 2, 2, 3, 4};
        BobbleSort.printArray(ar);
//        System.out.println("--------------");
        BobbleSort.sort(ar);
//        System.out.println("--------------");
        BobbleSort.printArray(ar);

    }

    public static void printArray(int[] ar) {
        for (int j : ar) {
            System.out.print(j + " ");
        }
        System.out.println();
    }
}
