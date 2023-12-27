import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.TYPE})
@Zusicherung(author = Author.Marlene)
public @interface Zusicherung {
    Author author() default Author.All;

    String vorbedingung() default "";

    String nachbedingung() default "";

    String invariante() default "";

    String historyConstraints() default "";

    String PROBLEM() default "none";
}

