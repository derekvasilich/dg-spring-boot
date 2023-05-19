package com.example.response;

import com.example.models.User;

public class SignupResponse extends MessageResponse {
    private User user;

    public SignupResponse(String message, User user) {
        super(message);
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
