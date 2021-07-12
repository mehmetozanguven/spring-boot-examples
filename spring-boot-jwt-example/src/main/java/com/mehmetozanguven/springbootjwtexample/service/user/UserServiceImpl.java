package com.mehmetozanguven.springbootjwtexample.service.user;

import com.mehmetozanguven.springbootjwtexample.dto.UserDTO;
import com.mehmetozanguven.springbootjwtexample.repository.UserRepository;
import com.mehmetozanguven.springbootjwtexample.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(@Autowired UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<UserDTO> findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
