package annotations;

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
