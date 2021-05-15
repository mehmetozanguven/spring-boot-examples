package com.springexamples.springaop.controller;

import com.springexamples.springaop.annotations.SimpleAnnotation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class HelloController {
    private static final Logger LOGGER = LoggerFactory.getLogger(HelloController.class);

    @GetMapping("/hello")
    @SimpleAnnotation
    public String helloController(){
        final String methodName = "helloController";
        callAnotherMethod();
        LOGGER.info("HelloController for method : {}", methodName);
        return "Hello";
    }

    private void callAnotherMethod(){

    }
}
