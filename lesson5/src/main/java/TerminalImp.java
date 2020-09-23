
/**
 * класс терминальная сессия, создается при вводе номера карты
 */

import termexceptions.*;

import java.time.LocalTime;
import java.util.Arrays;

public class TerminalImp implements Terminal {
    private final int timeLockMax = 10;
    private final int timeActivMax = 20;
    private final TerminalServer server;
    private final String cardNumber;
    private final ATM ATMImp;
    private boolean lock = false;
    private boolean authorization = false;
    private LocalTime timePin;

    public TerminalImp(ATM atm, String cardNumber) {
        this.server = new TerminalServer();
        this.cardNumber = cardNumber;
        this.ATMImp = atm;
    }

    @Override
    public int checkBalance() {
        return server.getBalance();
    }

    @Override
    public int putMoney(int money) {
        server.addBalance(money);
        return checkBalance();
    }

    @Override
    public int getMoney(int money) throws NegBalanceException {
        server.subBalance(money);
        return checkBalance();
    }

    @Override
    public void giveControl() throws UserException, AccountIsLockedException {
        try {
            boolean pinTrue;
            int countTry = 0;
            while(!checkLock()) {
                ATMImp.request("Введите пин-код");
                String validPin = ATMImp.answer(4, Arrays.asList("exit"), "[0-9]");
                timePin = LocalTime.now();
                pinTrue = server.checkPin(cardNumber, validPin);
                if (!pinTrue) {
                    ATMImp.request("Введен не правильный пин-код.\n" +
                            "Осталось " + (2 - countTry++) + " попытки");

                    if (countTry == 3)
                        lock = true;
                } else {
                    authorization = true;
                    return;
                }
            }
        } catch (UserException e) {
            throw  new UserException(e.getMessage() +"\n" +
                    "Отменено при вводе пин-кода");
        } catch (ServisException e) {
            e.printStackTrace();
        }
    }

    private boolean checkLock () throws AccountIsLockedException {
        if (lock) {
            int timeLock = LocalTime.now().getSecond() - timePin.getSecond();
            if (timeLock <= timeLockMax)
                throw new AccountIsLockedException(
                        String.format("Аккаунт будет разблокирован через %d секунд", timeLockMax - timeLock));
            else
                lock = false;
        }
        return false;
    }

    /*private boolean checkAuthorization () {
        if (authorization) {
            int timeLock = LocalTime.now().getSecond() - timePin.getSecond();
            if (timeLock >= timeActivMax)
                authorization = false;
            else
                lock = false;
        }
        return false;
    }*/


}
