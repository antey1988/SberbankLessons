

import termexceptions.NegBalanceException;

public class TerminalServer {
    private int balance = 10000;
    private final String pin = "1467";

    public int getBalance() {
        return balance;
    }

    public int subBalance(int money) throws NegBalanceException {
        int newbalance = balance - money;
        if (newbalance < 0) throw new NegBalanceException(String.format("На Вашем счете %d руб. Введите меньшую сумму", balance));
        return balance = newbalance;
    }

    public int addBalance(int money) {
        return balance += money;
    }

    public boolean checkPin(String cardNumber, String pin) {
        return this.pin.equals(pin);
    }
}
