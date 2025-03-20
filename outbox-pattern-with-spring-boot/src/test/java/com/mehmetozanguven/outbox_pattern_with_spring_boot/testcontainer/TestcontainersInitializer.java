package com.mehmetozanguven.outbox_pattern_with_spring_boot.testcontainer;

import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.kafka.KafkaContainer;
import org.testcontainers.lifecycle.Startables;
import org.testcontainers.utility.DockerImageName;

public class TestcontainersInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    private static final String POSTGRES_DOCKER_NAME = "postgres:16-alpine";
    private static final String KAFKA_DOCKER_NAME = "apache/kafka-native:4.0.0";
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(DockerImageName.parse(POSTGRES_DOCKER_NAME));
    static KafkaContainer kafkaContainer = new KafkaContainer(DockerImageName.parse(KAFKA_DOCKER_NAME));
    static {
        Startables.deepStart(postgres, kafkaContainer).join();
    }

    @Override
    public void initialize(ConfigurableApplicationContext ctx) {
        TestPropertyValues.of(
                "spring.datasource.url=" + postgres.getJdbcUrl(),
                "spring.datasource.username=" + postgres.getUsername(),
                "spring.datasource.password=" + postgres.getPassword(),
                "spring.test.database.replace=none",
                "spring.kafka.bootstrap-servers=" + kafkaContainer.getBootstrapServers(),
                "spring.kafka.consumer.group-id=test"
        ).applyTo(ctx.getEnvironment());
    }
}
