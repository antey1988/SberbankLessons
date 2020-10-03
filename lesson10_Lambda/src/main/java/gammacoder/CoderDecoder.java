package gammacoder;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CoderDecoder {
    private static final Map<Character, Integer> alphaviteToNumber;
    private static final Map<Integer, Character> NumberToAlphavite;
    private static final Character[] alphavite = {'а','б','в','г','д','е','ё','ж','з','и','й','к','л','м','н','о','п','р','с','т','у','ф',
            'х','ц','ч','ш', 'щ', 'ъ','ы','ь','э','ю','я',' ','0','1','2','3','4','5','6','7','8','9'};
    final static int lenAlphavite = alphavite.length;
    static {
//соответствие букв и цифр порядовым номерам
        alphaviteToNumber = Stream.iterate(1, i-> i + 1).limit(lenAlphavite)
                .collect(Collectors.toMap(i->alphavite[i-1],  Function.identity()));
//соответствие порядковых номеров буквам и цифрам
        NumberToAlphavite = alphaviteToNumber.entrySet().stream().collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey));
    }

    public static String coder(String string) {
        string = string.toLowerCase();
        int len = string.length();
        //генерация кодирующей последовательности
        Stream<Integer> gamma = Stream.generate(()->lenAlphavite).limit(len).map(t->1 + (int) (t*Math.random()));
        List<Integer> listGamma = gamma.collect(Collectors.toList());
        //получение сисловой последовательности кодируемой строки
        Stream<Integer> openText =  string.chars().mapToObj(i -> (char) i).map(alphaviteToNumber::get);
        List<Integer> listOpenText = openText.collect(Collectors.toList());
        //суммирование по модулю кодиремой строки и кодирующего "слова"
        Stream<Integer> sum = Stream.iterate(0, i-> i + 1).limit(len)
                .map(i->(listGamma.get(i) + listOpenText.get(i))%lenAlphavite)
                .map(i->i == 0 ? lenAlphavite : i);
//                List<Integer> listSum = sum.collect(Collectors.toList());
        //возращаем зашифрованный текст
        Character[] characters = sum.map(NumberToAlphavite::get).toArray(Character[]::new);
        return Arrays.toString(characters).replace(", ","").replaceAll("\\[|\\]","");
//return null;
    }
    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            System.out.println(coder("открытый текст"));
        }

    }

//    private Map<Character, Integer>
}
