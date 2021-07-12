package com.mehmetozanguven.springbootjwtexample.service.register;

import com.mehmetozanguven.springbootjwtexample.dto.UserDTO;
import com.mehmetozanguven.springbootjwtexample.dto.UserRoleDTO;
import com.mehmetozanguven.springbootjwtexample.repository.RegisterRepository;
import com.mehmetozanguven.springbootjwtexample.request.RegisterRequest;
import com.mehmetozanguven.springbootjwtexample.response.RegisterResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class RegisterServiceImpl implements RegisterService {
    private final PasswordEncoder passwordEncoder;
    private final RegisterRepository registerRepository;

    public RegisterServiceImpl(@Autowired PasswordEncoder passwordEncoder, @Autowired RegisterRepository registerRepository) {
        this.passwordEncoder = passwordEncoder;
        this.registerRepository = registerRepository;
    }

    @Transactional
    @Override
    public RegisterResponse doRegister(RegisterRequest registerRequest) {
        Optional<UserDTO> isUserInDb = registerRepository.findByUsername(registerRequest.getUsername());
        if (isUserInDb.isPresent()) {
            throw new RuntimeException("User has already in db");
        }

        UserDTO newUser = mapNewRegisterToNewUser(registerRequest);
        registerRepository.save(newUser);
        RegisterResponse registerResponse = new RegisterResponse();
        registerResponse.setRegistered(true);
        registerResponse.setMesssage("User was saved");
        return registerResponse;
    }

    private UserDTO mapNewRegisterToNewUser(RegisterRequest registerRequest) {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(registerRequest.getUsername());
        userDTO.setPassword(passwordEncoder.encode(registerRequest.getPassword()));

        UserRoleDTO basicRole = new UserRoleDTO();
        basicRole.setRole("ROL_BASIC");
        basicRole.setUserDTO(userDTO);

        userDTO.getUserRoles().add(basicRole);
        return userDTO;
    }
}
