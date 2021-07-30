package com.mehmetozanguven.springbootjwtexample.controller;

import com.mehmetozanguven.springbootjwtexample.request.DummyPostRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ProtectedController {

    @GetMapping("/protected")
    public ResponseEntity<?> getProtectedResource() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        return ResponseEntity.ok("Hello Logged-In User:" + currentPrincipalName + ", you can access this resource, because JWT was valid");
    }

    @PostMapping("/doSimplePostRequest")
    public ResponseEntity<?> doSimplePostRequest(@RequestBody DummyPostRequest dummyPostRequest) {
        return ResponseEntity.ok("Post request is done");
    }
}
