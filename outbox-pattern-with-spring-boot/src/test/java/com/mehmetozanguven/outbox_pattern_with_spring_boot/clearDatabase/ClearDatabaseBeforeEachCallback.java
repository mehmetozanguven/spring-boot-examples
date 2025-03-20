package com.mehmetozanguven.outbox_pattern_with_spring_boot.clearDatabase;

import lombok.extern.slf4j.Slf4j;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@Slf4j
public class ClearDatabaseBeforeEachCallback implements BeforeEachCallback {

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        log.info("Clear database before each request :'{}'", context.getDisplayName());
        clearFlyway(context);
        String methodName = "";
        if (context.getTestMethod().isPresent()) {
            methodName = context.getTestMethod().get().getName();
        }

        log.info("\nDatabase has been cleared and migrated before all tests Method name :: {}\n", methodName);
    }

    private void clearFlyway(ExtensionContext context) {
        Flyway flyway = SpringExtension.getApplicationContext(context)
                .getBean(Flyway.class);
        flyway.clean();
        flyway.migrate();
    }
}
