package workwithstrings;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    private static String input_text = "1 222 33 4 \n" +
                                    "1 2 333 55 \n" +
                                    "1 1 33 2 2 \n" +
                                    "4 6 8 9 10 10";

    private static List<String> all_strings = new ArrayList<>();
    private static List<String> all_words = new ArrayList<>();
    private static Set<String> def_words = new TreeSet<>();

    public static void main(String[] args) {
//        Stream.of(input_text.split("\n")).map(String::toLowerCase).forEach(s -> strings.add(s));
//        Stream.of(input_text.split("\\s")).map(String::toLowerCase).forEach(s -> all_words.add(s));
//        Stream.of(input_text.split("\\s")).map(String::toLowerCase).forEach(s -> def_words.add(s));

        all_strings = new ArrayList<>(Arrays.asList(input_text.toLowerCase().split("\\n")));
        all_words = new ArrayList<>(Arrays.asList(input_text.toLowerCase().split("\\s")));
        def_words = new TreeSet<>(all_words);


        System.out.println("task #1");
        System.out.println(def_words.size());
        System.out.println("-------------------------");

        System.out.println("task #2");
//        def_words.stream().sorted(Comparator.comparingInt(String::length)).forEach(System.out::println);

        List<String> def_words_len = new ArrayList<>(def_words);
        def_words_len.sort(Comparator.comparingInt(String::length));
        def_words_len.forEach(System.out::println);
        System.out.println("-------------------------");

        def_words.forEach(System.out::println);
        System.out.println("-------------------------");

        System.out.println("task #3");
        def_words.forEach(s -> System.out.println("word " + s + " : " + Collections.frequency(all_words, s)));
        System.out.println("-------------------------");

        System.out.println("task #4 and 5");
        /*список с "обратным итератором"
        * заполняем строками входного текса*/
        List<String> revlist = new ReverseArrayList<>(all_strings);
        for (String s : revlist) System.out.println(s);
//        revlist.forEach(System.out::println);
        System.out.println("-------------------------");

        System.out.println("task #6");
        printString();

    }

    public static void printString() {
        System.out.println("Введите номера строк через пробел");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try {
            String str = br.readLine();
            for(String s : str.split("\\s")) {
                int n = Integer.parseInt(s);
                try {
                    System.out.println(all_strings.get(n-1));
                } catch (IndexOutOfBoundsException e) {
                    System.out.println("Строкa с номером " + n + " отсутствует в тексте");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
