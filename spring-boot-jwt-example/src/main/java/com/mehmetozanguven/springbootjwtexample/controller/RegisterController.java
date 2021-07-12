package com.mehmetozanguven.springbootjwtexample.controller;

import com.mehmetozanguven.springbootjwtexample.request.RegisterRequest;
import com.mehmetozanguven.springbootjwtexample.response.RegisterResponse;
import com.mehmetozanguven.springbootjwtexample.service.register.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
