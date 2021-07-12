package com.mehmetozanguven.springbootjwtexample.service.user;

import com.mehmetozanguven.springbootjwtexample.dto.UserDTO;

import java.util.Optional;

public interface UserService {
    Optional<UserDTO> findUserByUsername(String username);
}
