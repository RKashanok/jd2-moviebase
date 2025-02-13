package com.jd2.moviebase.service;

import com.jd2.moviebase.dto.AccountDto;
import com.jd2.moviebase.dto.LoginResponse;
import com.jd2.moviebase.dto.LoginUserDto;
import com.jd2.moviebase.dto.RegisterUserDto;
import com.jd2.moviebase.model.User;
import com.jd2.moviebase.model.UserDetailModel;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final AccountService accountService;

    public LoginResponse authentication(LoginUserDto input) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getEmail(),
                        input.getPassword()
                ));
        UserDetailModel authenticatedUser = (UserDetailModel) userService.loadUserByUsername(input.getEmail());
        String jwtToken = jwtService.generateToken(authenticatedUser);
        return LoginResponse.builder().token(jwtToken).expiresIn(jwtService.getExpirationTime()).build();
    }

    @Transactional
    public User signup(RegisterUserDto registerUserDto) {
        User user = User.builder()
                .email(registerUserDto.getEmail())
                .password(registerUserDto.getPassword())
                .role("ROLE_USER")
                .build();
        User registeredUser = userService.create(user);

        AccountDto.AccountDtoBuilder accountBuilder = AccountDto.builder()
                .userId(registeredUser.getId());
        Optional.ofNullable(registerUserDto.getFirstName()).ifPresent(accountBuilder::firstName);
        Optional.ofNullable(registerUserDto.getLastName()).ifPresent(accountBuilder::lastName);
        Optional.ofNullable(registerUserDto.getPreferredName()).ifPresent(accountBuilder::preferredName);
        Optional.ofNullable(registerUserDto.getDateOfBirth()).ifPresent(accountBuilder::dateOfBirth);
        AccountDto accountDto = accountBuilder.build();
        accountService.create(accountDto);

        return registeredUser;
    }

}
