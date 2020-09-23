package termexceptions;

/**
 * бросается при попытки запросить доступ к заблокированному терминалу
 * для предотвращения не запрашивать доступ до окончания времени блокировки
 */
public class AccountIsLockedException extends Exception {
    public AccountIsLockedException(String message) {
        super(message);
    }
}
