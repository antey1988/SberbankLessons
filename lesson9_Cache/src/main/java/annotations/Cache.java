package annotations;
/**
 * Аннотация применяется, если необходимо кэшировать результаты метода
 * Возможно кэширование в оперативную память или в файловую систему
 * поля:
 * type - задается место хранения кэша
 * namePrifix - задается имя кэшируемого метода
 * zip - в случае кэширования в файловую систему, возможно дололнительное упаковывание
 * identityBy - массив классов, формирующий маску для вычисления уникального ключа кэша
 *              из значений переданных параметров,
 *              при значении по умолчанию используются все параметры,
 *              при указании классов необходимо соблюдать такую же последовальность как и в сигнатуре метода
 *              при необходимости пропустить какой-либо из параметров укаазать SkipType.class
 * sizeList - задает размер списка, сохраняемого в кэше, занчение по умолчанию -1 - весь список
 */

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Cache {
    TypeCache type() default TypeCache.IN_MEMORY;
    String namePrefix() default "";
    boolean zip() default false;
    Class[] identityBy() default {};
    int sizeList() default  -1;
}
