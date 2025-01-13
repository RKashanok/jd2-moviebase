package com.jd2.moviebase.controller;

import com.jd2.moviebase.dto.AccountDto;
import com.jd2.moviebase.model.User;
import com.jd2.moviebase.service.AccountService;
import com.jd2.moviebase.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;
    private AccountService accountService;

    @Autowired
    public UserController(UserService userService, AccountService accountService) {
        this.userService = userService;
        this.accountService = accountService;
    }

    @PostMapping
    public User create(@RequestBody User user) {
        return userService.create(user);
    }

    @GetMapping("/{id}")
    public User findById(@PathVariable("id") Long id) {
        return userService.findById(id);
    }

    @GetMapping("/{id}/accounts")
    public AccountDto findAccountsByUserId(@PathVariable("id") Long id) {
        return accountService.findByUserId(id);
    }

    @GetMapping
    public List<User> findAll() {
        return userService.findAll();
    }

    @PutMapping
    public User update(@RequestBody User user) {
        return userService.update(user);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable("id") Long id) {
        userService.deleteById(id);
    }
}
