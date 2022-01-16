package com.mehmetozanguven.springcachewithoutannotation.service;

import com.mehmetozanguven.springcachewithoutannotation.model.User;

import java.util.List;

public interface UserService {
    User findById(long id);
    User findByFirstName(String firstName);
    void changeFirstName(long id, String firstName);
    List<User> getAllUsers();
}
