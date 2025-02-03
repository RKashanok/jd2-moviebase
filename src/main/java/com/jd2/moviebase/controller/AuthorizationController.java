package com.jd2.moviebase.controller;

import com.jd2.moviebase.dto.LoginResponse;
import com.jd2.moviebase.dto.LoginUserDto;
import com.jd2.moviebase.dto.RegisterUserDto;
import com.jd2.moviebase.model.User;
import com.jd2.moviebase.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthorizationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public LoginResponse authenticate(@RequestBody LoginUserDto loginUserDto) {
        return authenticationService.authentication(loginUserDto);
    }

    @PostMapping("/signup")
    public User register(@RequestBody RegisterUserDto registerUserDto) {
        return authenticationService.signup(registerUserDto);
    }
}
