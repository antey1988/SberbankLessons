
import termexceptions.ServisException;
import termexceptions.UserException;

import java.util.List;

/**
 * интерфейс, выводящий информацию для пользователя и получающий ответ от пользователя в соответствии с маской
 */
public interface ATM {
     void request(String string);
     String answer(int digits, List<String> textStop, String maska) throws ServisException, UserException;
}
