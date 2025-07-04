package com.example.base_jsp.models.response;


import lombok.Data;

@Data
public class AuthenticationResponse {

    private String token;

    private String tokenType = "Bearer";

    public AuthenticationResponse(String token) {
        this.token = token;
    }
}
