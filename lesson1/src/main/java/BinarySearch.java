

public class BinarySearch {
//    return index or -1, if element not find
    public static int findIndex(int [] array, int elem) {
        if ((elem < array[0]) || (elem > array[array.length - 1])) return -1;
        int down = 0;
        int up = array.length-1;
        int mid;
        while (down <= up) {
                mid = (down + up) / 2;
                int value = array[mid];
                if (value == elem) return mid;
                else if (value > elem) up = mid - 1;
                else down = mid + 1;
        }
        return -1;
    }

    public static void main(String[] args) {
        int[] ar = new int[10];
        for (int i = 0; i < ar.length; i++) {
            ar[i] = i;
        }
        System.out.println(BinarySearch.findIndex(ar, 6));
        System.out.println(BinarySearch.findIndex(ar, -2));
        System.out.println(BinarySearch.findIndex(ar, 11));
    }
}
