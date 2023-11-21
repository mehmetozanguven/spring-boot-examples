package com.mehmetozanguven.capturelongrunningmethod.capture;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LongRunningExecution {

    boolean request() default false;

    boolean response() default false;

    long maxExecutionTime() default -1L;
}
