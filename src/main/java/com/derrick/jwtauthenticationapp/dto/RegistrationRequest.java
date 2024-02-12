package com.derrick.jwtauthenticationapp.dto;

import com.derrick.jwtauthenticationapp.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class RegistrationRequest {
    private String firstName;
    private String lastName;
    private String email;
    private Role role;
    private String password;
}
