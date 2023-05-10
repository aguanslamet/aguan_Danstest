package com.jwtAuth.test.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
public class UserRequest {
    private Long id;
    private String username;
    @Email(message = "In Correte Formate Email!")
    private String email;
    @NotEmpty(message = "Password is Required!")
    private String password;
}
