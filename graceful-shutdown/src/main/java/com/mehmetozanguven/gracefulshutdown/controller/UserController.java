package com.mehmetozanguven.gracefulshutdown.controller;

import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

@Slf4j
@RestController
public class UserController {
    ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(4);

    @GetMapping("/hello")
    public String hello() throws InterruptedException {
        log.info("Waiting for 7 seconds");
        executor.execute(() -> {
            try {
                Thread.sleep(7000);
                log.info("Completed 7seconds in thread");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        log.info("Returning response");
        return "Hello world";
    }

    @PreDestroy
    public void destroy() {
        log.info("This will be called after graceful shutdown");

        // while there is an active thread
        while (executor.getActiveCount() > 0) {
            try {
                // wait another 2seconds and check again
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        log.info("Definitely destroy all active threads");
    }
}
