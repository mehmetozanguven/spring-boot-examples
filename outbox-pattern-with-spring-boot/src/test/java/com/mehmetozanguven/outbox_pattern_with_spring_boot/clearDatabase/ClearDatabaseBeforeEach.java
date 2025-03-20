package com.mehmetozanguven.outbox_pattern_with_spring_boot.clearDatabase;


import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.TestPropertySource;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@ExtendWith(ClearDatabaseBeforeEachCallback.class)
@TestPropertySource(properties = "spring.flyway.clean-disabled=false")
public @interface ClearDatabaseBeforeEach {
}
