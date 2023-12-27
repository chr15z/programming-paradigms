import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@CreatedBy(Author.Marlene)
@Target({ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.TYPE})
@SelfMade
public @interface Zusicherung {
    String vorbedingung() default "";
    String nachbedingung()default "";
    String invariante()default "";
    String historyConstraints()default "";

}
