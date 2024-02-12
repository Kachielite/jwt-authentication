package com.derrick.jwtauthenticationapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class AuthenticationResponse {

    private String responseCode;
    private String responseMessage;
    private String token;
}
