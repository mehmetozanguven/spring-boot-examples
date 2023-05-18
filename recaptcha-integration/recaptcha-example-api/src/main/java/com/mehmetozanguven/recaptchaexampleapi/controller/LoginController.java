package com.mehmetozanguven.recaptchaexampleapi.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @PostMapping(value = "/api/login")
    public String doLogin() {
        return "Login method has been called";
    }
}
