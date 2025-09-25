package ge.leverx.springbootapplicationexample.logging;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
@Documented
public @interface CustomLogging {
    Level level() default Level.INFO;
    boolean logArgs() default true;
    boolean logResult() default true;
    boolean logExecutionTime() default true;
    String value() default "";

    enum Level { TRACE, DEBUG, INFO, WARN, ERROR }
}
