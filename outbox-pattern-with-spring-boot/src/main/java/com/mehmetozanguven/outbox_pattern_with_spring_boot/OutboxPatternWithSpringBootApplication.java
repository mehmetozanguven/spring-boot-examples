package com.mehmetozanguven.outbox_pattern_with_spring_boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class OutboxPatternWithSpringBootApplication {

	public static void main(String[] args) {
		SpringApplication.run(OutboxPatternWithSpringBootApplication.class, args);
	}

}
