package com.mehmetozanguven.springbootjwtexample.response;

public class RegisterResponse {
    private boolean isRegistered;
    private String messsage;

    public RegisterResponse() {

    }

    public boolean isRegistered() {
        return isRegistered;
    }

    public void setRegistered(boolean registered) {
        isRegistered = registered;
    }

    public String getMesssage() {
        return messsage;
    }

    public void setMesssage(String messsage) {
        this.messsage = messsage;
    }
}
