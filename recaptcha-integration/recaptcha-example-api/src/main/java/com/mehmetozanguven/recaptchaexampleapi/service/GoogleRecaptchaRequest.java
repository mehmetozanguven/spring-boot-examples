package com.mehmetozanguven.recaptchaexampleapi.service;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GoogleRecaptchaRequest {
    private String secret;
    private String response;
    private String remoteip;

    public GoogleRecaptchaRequest() {
        this.secret = "secret key";
    }
}
