package com.derrick.jwtauthenticationapp.service;

import com.derrick.jwtauthenticationapp.config.JwtService;
import com.derrick.jwtauthenticationapp.dto.AuthenticationRequest;
import com.derrick.jwtauthenticationapp.dto.AuthenticationResponse;
import com.derrick.jwtauthenticationapp.dto.RegistrationRequest;
import com.derrick.jwtauthenticationapp.entity.User;
import com.derrick.jwtauthenticationapp.repository.UserRepository;
import com.derrick.jwtauthenticationapp.utils.AuthUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    final UserRepository userRepository;
    final JwtService jwtService;
    final PasswordEncoder passwordEncoder;
    final AuthenticationManager authenticationManager;



    public AuthenticationResponse register(RegistrationRequest registrationRequest){

        Optional<User> getUser = userRepository.findByEmail(registrationRequest.getEmail());
        if(getUser.isPresent()){
            return AuthenticationResponse
                    .builder()
                    .responseCode(AuthUtils.USER_EXIST_CODE)
                    .responseMessage(AuthUtils.USER_EXIST_MESSAGE)
                    .token(null)
                    .build();
        }

        User newUser = User
                .builder()
                .firstName(registrationRequest.getFirstName())
                .lastName(registrationRequest.getLastName())
                .email(registrationRequest.getEmail())
                .role(registrationRequest.getRole())
                .password(passwordEncoder.encode(registrationRequest.getPassword()))
                .build();

        userRepository.save(newUser);

        String jwt = jwtService.generateToken(newUser);

        return AuthenticationResponse
                .builder()
                .responseCode(AuthUtils.USER_CREATED_CODE)
                .responseMessage(AuthUtils.USER_CREATED_MESSAGE)
                .token(jwt)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest){

        Optional<User> getUser = userRepository.findByEmail(authenticationRequest.getEmail());
        if(getUser.isEmpty()){
            return AuthenticationResponse
                    .builder()
                    .responseCode(AuthUtils.USER_NOT_FOUND_CODE)
                    .responseMessage(AuthUtils.USER_NOT_FOUND_MESSAGE)
                    .token(null)
                    .build();
        }

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), authenticationRequest.getPassword())
        );

        String jwt = jwtService.generateToken(getUser.get());
        return AuthenticationResponse
                .builder()
                .responseCode(AuthUtils.USER_AUTHENTICATED_CODE)
                .responseMessage(AuthUtils.USER_AUTHENTICATED_MESSAGE)
                .token(jwt)
                .build();


    }


}
