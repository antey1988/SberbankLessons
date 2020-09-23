
/**
 * класс банкомат
 */

import termexceptions.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ATMImp implements ATM {
    public StringValidator stringValidator = new StringValidator();
//вывводит информацию в консоль для пользователя
    @Override
    public void request(String string) {
        System.out.println(string);
    }
//считывает данные введенные пользователем согласно маски
    @Override
    public String answer(int digits, List<String> textStop, String maska) throws ServisException, UserException {
        stringValidator.set(digits, textStop, maska);
        return stringValidator.checkString();
    }
//формирует запрос операции по банковскому счету, либо отказ от дальнейших действий
    private String requestActions() throws ServisException, UserException {
        request("Выберите действие:\n" +
                "1 - запрос баланса\n" +
                "2 - пополнить баланс\n" +
                "3 - снять наличные\n");
        try {
            return answer(1, Collections.singletonList("exit"), "[1-3]");
        } catch (UserException e) {
            throw  new UserException(e.getMessage() +"\n" +
                    "Отменено при выборе запроса");
        }
    }
//формирует запрос на сумму денег для снятия или пополнения, либо отказ от дальнейших действий
//выполняет проверку введенных данных на кратность 100
    private int requestAmount () throws UserException {
        int sum = 0;
        try {
            do {
                request("Введите сумму, кратную 100");
                String string = answer(1, Collections.singletonList("exit"), "[0-9]+");
                sum = Integer.parseInt(string);
            } while (sum % 100 != 0);
        } catch (UserException e) {
            throw new UserException(e.getMessage() +"\n" +
                    "Отменено при вводе суммы");
        } catch (ServisException e) {
            e.printStackTrace();
        }
        return sum;
    }

    public static void main(String[] args) throws IOException {
        ATMImp atm = new ATMImp();
        String cardNumber;
        while (true) {
            atm.request("Введите номер карты. Это 6-шестизначное число");
            try {
                cardNumber = atm.answer(1, Arrays.asList("stop", "exit"), "[0-9]{6,6}");
                Terminal terminal = new TerminalImp(atm, cardNumber);
//                Terminal terminal = new TerminalImp(atm, "123");
                while (true) {
                    try {
                        atm.request("Продолжить работу?  yes / no ");
                        String req = atm.answer(1, Collections.singletonList("no"), "yes");
                        if (req.equals("yes")) {
                            terminal.giveControl();
                            String action = atm.requestActions();
                            if (action.equals("1")) {
                                atm.request(String.format("Ваш баланс составляет %d руб.", terminal.checkBalance()));
                            }
                            else if (action.equals("2")) {
                                int amount = atm.requestAmount();
                                atm.request(String.format("Ваш баланс пополнен на %d руб. и\n" +
                                        "составляет %d руб.", amount, terminal.putMoney(amount)));
                            }
                            else {
                                while (true) {
                                    int amount = atm.requestAmount();
                                    try {
                                        atm.request(String.format("Снята сумма %d руб. \n" +
                                                "Ваш баланс составляет %d руб.", amount, terminal.getMoney(amount)));
                                        break;
                                    } catch (NegBalanceException e) {
                                        atm.request(e.getMessage());
                                    }
                                }
                            }

                        }
                    } catch (UserException e) {
                        System.out.println(e.getMessage());
                        break;
                    } catch (AccountIsLockedException e) {
                        System.out.println(e.getMessage());
                    } catch (ServisException e) {
                        e.printStackTrace();
                    }
                }

            } catch (UserException e) {
                System.out.println(e.getMessage());
            } catch (ServisException e) {
                System.out.println(e.getMessage());
                break;
            }

        }
    }
}
