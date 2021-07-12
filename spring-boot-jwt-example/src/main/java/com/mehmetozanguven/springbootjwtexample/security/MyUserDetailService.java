package com.mehmetozanguven.springbootjwtexample.security;

import com.mehmetozanguven.springbootjwtexample.dto.UserDTO;
import com.mehmetozanguven.springbootjwtexample.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class MyUserDetailService implements UserDetailsService {
    private final UserRepository userRepository;

    public MyUserDetailService(@Autowired UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserDTO> userInDb = userRepository.findByUsername(username);
        UserDTO userDTO = userInDb.orElseThrow(() -> new UsernameNotFoundException("Not Found in DB"));
        return new SecureUser(userDTO);
    }
}
