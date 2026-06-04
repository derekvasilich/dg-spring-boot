package com.dg.response;

import com.dg.models.User;

public class SignupResponse extends MessageResponse<String> {
    private User user;

    public SignupResponse(String message, User user) {
        super(message);
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
