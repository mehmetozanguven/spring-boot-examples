package com.mehmetozanguven.springbootjwtexample.controller;

import com.mehmetozanguven.springbootjwtexample.request.RegisterRequest;
import com.mehmetozanguven.springbootjwtexample.response.RegisterResponse;
import com.mehmetozanguven.springbootjwtexample.service.register.RegisterService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api")
public class RegisterController {
    private final RegisterService registerService;

    public RegisterController(@Autowired RegisterService registerService) {
        this.registerService = registerService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> doRegister(@RequestBody @Valid RegisterRequest registerRequest) {
        RegisterResponse registerResponse = registerService.doRegister(registerRequest);
        return ResponseEntity.ok(registerResponse);
    }
}
