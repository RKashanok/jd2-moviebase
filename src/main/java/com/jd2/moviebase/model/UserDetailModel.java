package com.jd2.moviebase.model;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class UserDetailModel implements UserDetails {
    private String username;
    private String password;
    private List<GrantedAuthority> authorities;
    @Getter
    private Long accountId;

    public UserDetailModel(User user, Long accountId) {
        this.username = user.getEmail();
        this.password = user.getPassword();
        this.authorities = Stream.of(user.getRole().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        this.accountId = accountId;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

}
