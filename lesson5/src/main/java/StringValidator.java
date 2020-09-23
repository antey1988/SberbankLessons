

import termexceptions.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class StringValidator {

    private int countDigits;
    private List<String> breakComand;
    private String validSymbol;

    public void set(int countDigits, List<String> breakComand, String validSymbol) {
        this.countDigits = countDigits;
        this.breakComand = breakComand;
        this.validSymbol = validSymbol;
    }

    public String checkString() throws ServisException, UserException {
            int count = 0;
            StringBuilder validString = new StringBuilder();
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//            try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            try {
                while (count < countDigits) {
                    String string = br.readLine();
                    if (breakComand.contains(string)) {
                        if (string.equals("exit") || string.equals("no")) throw new UserException("Отменено пользователем");
                        else throw new ServisException("Сервисное обслуживание");
                    }
                    if (!string.matches(validSymbol))
                        System.out.println("Введен не верный символ. Допускается водить только цифры 0-9");
                    else {
                        validString.append(string);
                        count++;
                    }
                }
            }

            catch (IOException e) {
                e.printStackTrace();
            }

            return validString.toString();
        }
}
