package termexceptions;

/**
 * бросается при отказе пользователя от дальнейших действий
 * для предотвращения не вводить служебные слова EXIT, NO
 */
public class UserException extends Exception {
    public UserException(String message) {
        super(message);
    }

    public UserException(String message, Throwable cause) {
        super(message, cause);
    }
}
