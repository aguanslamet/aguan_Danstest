package com.jwtAuth.test.controller;

import com.jwtAuth.test.dto.JWTResponse;
import com.jwtAuth.test.dto.ResponseStatus;
import com.jwtAuth.test.dto.UserRequest;
import com.jwtAuth.test.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
@RestController
@RequestMapping(value = "/auth")
public class AuthController {
    @Autowired
    AuthService authService;

    @PostMapping("/register")
    public ResponseStatus register(@Valid @RequestBody UserRequest request){
        return authService.register(request);
    }
    @PostMapping("/login")
    public ResponseEntity<JWTResponse> login(@Valid @RequestBody UserRequest request){
        return authService.login(request);
    }
}
