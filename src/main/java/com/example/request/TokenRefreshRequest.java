package com.example.request;

import javax.validation.constraints.NotBlank;

public class TokenRefreshRequest {
    @NotBlank
    private String refreshToken;

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refresh) {
        this.refreshToken = refresh;
    }
}
