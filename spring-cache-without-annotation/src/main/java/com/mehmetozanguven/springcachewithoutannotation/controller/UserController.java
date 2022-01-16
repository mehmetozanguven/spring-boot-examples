package com.mehmetozanguven.springcachewithoutannotation.controller;

import com.mehmetozanguven.springcachewithoutannotation.model.User;
import com.mehmetozanguven.springcachewithoutannotation.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/findById")
    public ResponseEntity<User> findById(@RequestParam(value = "id") long userId) {
        User user = userService.findById(userId);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/findByUserName")
    public ResponseEntity<User> findByFirstName(@RequestParam(value = "name") String firstName) {
        User user = userService.findByFirstName(firstName);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/updateFirstName")
    public ResponseEntity<String> updateFirstName(@RequestParam(value = "id") long userId, @RequestParam(value = "name") String newFirstName) {
        userService.changeFirstName(userId, newFirstName);
        return ResponseEntity.ok("Success");
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<User>> findAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }
}
