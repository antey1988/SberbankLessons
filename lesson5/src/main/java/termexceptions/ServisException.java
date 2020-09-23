package termexceptions;

/**
 * бросается при вводе служебного слова STOP, используется для остановки работы банкомата
 * для предотвращения не вводить служебное сло STOP во время запроса номера карты
 */
public class ServisException extends Exception {
    public ServisException(String message) {
        super(message);
    }
}
