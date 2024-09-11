package com.jd2.moviebase.controller;

import com.jd2.moviebase.model.User;
import com.jd2.moviebase.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        System.out.println("Start User Controller");
        this.userService = userService;
    }

//    @Autowired
//    public UserController(UserService userService) {
//        System.out.println("Start User Controller");
//        this.userService = userService;
//    }

//    @RequestMapping(value = "/users", method = RequestMethod.GET)
//    public @ResponseBody String getAllUsers() {
//        return "All users!!!";
//    }

    @GetMapping(value = "/{id}")
    public User findById(@PathVariable("id") Integer id) {
        return userService.findById(id);
    }
}
