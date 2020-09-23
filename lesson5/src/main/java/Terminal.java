
import termexceptions.AccountIsLockedException;
import termexceptions.NegBalanceException;
import termexceptions.UserException;

/**
 * интефейс, предоставляющий доступ к банковскому счету
 */
public interface Terminal {
    int checkBalance ();
    int putMoney (int count);
    int getMoney(int count) throws NegBalanceException;
    void giveControl() throws UserException, AccountIsLockedException;
}
