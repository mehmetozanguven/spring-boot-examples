package com.mehmetozanguven.recaptchaexampleapi.service;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class GoogleRecaptchaResponse {
    private boolean success;
    private String challenge_ts;
    private String hostname;
    @JsonProperty("error-codes")
    private String[] errorCodes;
    private Double score;
    private String action;
}
