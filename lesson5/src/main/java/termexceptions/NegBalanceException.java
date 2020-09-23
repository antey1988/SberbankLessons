package termexceptions;

/**
 * бросается при попытки снять сумму, превышающую баланс по счету
 * для предотвращения не запрашивать сумму больше баланса
 */
public class NegBalanceException extends Exception {
    public NegBalanceException(String message) {
        super(message);
    }
}
