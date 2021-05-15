package com.springexamples.springaop.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    private static final Logger LOGGER = LoggerFactory.getLogger(HomeController.class);
    @GetMapping("/")
    public String homeController(){
        final String methodName = "home";
        LOGGER.info("Log for method : {}", methodName);
        throw new RuntimeException("Exception inside home controller");
    }
}
