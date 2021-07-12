package com.mehmetozanguven.springbootjwtexample.controller;

import com.mehmetozanguven.springbootjwtexample.request.LoginRequest;
import com.mehmetozanguven.springbootjwtexample.response.LoginResponse;
import com.mehmetozanguven.springbootjwtexample.service.login.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class LoginController {
    private final LoginService loginService;

    public LoginController(@Autowired LoginService loginService) {
        this.loginService = loginService;
    }


    @PostMapping("/login")
    public ResponseEntity<?> doLogin(@RequestBody @Valid LoginRequest loginRequest) {
        LoginResponse loginResponse = loginService.doLogin(loginRequest);
        return ResponseEntity.ok(loginResponse);
    }
}
