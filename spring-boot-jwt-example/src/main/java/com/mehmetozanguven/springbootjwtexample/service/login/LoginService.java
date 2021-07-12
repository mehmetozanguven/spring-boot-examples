package com.mehmetozanguven.springbootjwtexample.service.login;

import com.mehmetozanguven.springbootjwtexample.request.LoginRequest;
import com.mehmetozanguven.springbootjwtexample.response.LoginResponse;

public interface LoginService {

    LoginResponse doLogin(LoginRequest loginRequest);
}
