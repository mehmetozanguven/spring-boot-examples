package com.mehmetozanguven.criteria_api_with_spring_boot.testcontainer;

import org.springframework.test.context.ContextConfiguration;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@ContextConfiguration(initializers = TestcontainersInitializer.class)
public @interface EnableTestcontainers {
}
