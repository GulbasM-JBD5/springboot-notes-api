package com.example.thirdyear.controller;

import com.example.thirdyear.dto.UserRequest;
import com.example.thirdyear.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.example.thirdyear.dto.LoginRequest;

@RestController
@RequestMapping("/auth")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void register(@Valid @RequestBody UserRequest request) {
        userService.register(request);
    }
    @PostMapping("/login")
    public boolean login( @Valid @RequestBody LoginRequest request) {
        return userService.login(request);
    }
}
