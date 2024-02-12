package com.derrick.jwtauthenticationapp.controller;

import com.derrick.jwtauthenticationapp.dto.AuthenticationRequest;
import com.derrick.jwtauthenticationapp.dto.AuthenticationResponse;
import com.derrick.jwtauthenticationapp.dto.RegistrationRequest;
import com.derrick.jwtauthenticationapp.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthenticationController {
    final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegistrationRequest registrationRequest){
        AuthenticationResponse authenticationResponse = authenticationService.register(registrationRequest);
        return new ResponseEntity<>(authenticationResponse, HttpStatus.CREATED);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest  authenticationRequest){
        AuthenticationResponse authenticationResponse = authenticationService.authenticate(authenticationRequest);
        return new ResponseEntity<>(authenticationResponse, HttpStatus.OK);
    }
}
