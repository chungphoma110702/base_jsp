package com.example.base_jsp.controller;

import com.example.base_jsp.models.dto.UserDto;
import com.example.base_jsp.models.request.LoginRequest;
import com.example.base_jsp.models.response.AuthenticationResponse;
import com.example.base_jsp.service.AuthenticationService;
import com.example.base_jsp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final UserService userService;

    @PostMapping("/login")
    public AuthenticationResponse login(@RequestBody LoginRequest request){
        return authenticationService.login(request);
    }

    @PostMapping("/create")
    public String createUser(@RequestBody UserDto request){
        return userService.createUser(request);
    }

    @GetMapping("/users/{id}")
    public UserDto getUserById(@PathVariable int id){
        return userService.getUser(id);
    }
}
