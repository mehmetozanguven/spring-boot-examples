package com.mehmetozanguven.springbootjwtexample.service.register;

import com.mehmetozanguven.springbootjwtexample.request.RegisterRequest;
import com.mehmetozanguven.springbootjwtexample.response.RegisterResponse;

public interface RegisterService {
    RegisterResponse doRegister(RegisterRequest registerRequest);

}
