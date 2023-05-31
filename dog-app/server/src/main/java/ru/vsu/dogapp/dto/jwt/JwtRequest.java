package ru.vsu.dogapp.dto.jwt;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Setter
@Getter
@ApiModel(description = "Entity of an user (username and password) for login and getting token.")
public class JwtRequest {

    @NotBlank(message = "Username must not be blank.")
    private String username;
    @NotBlank(message = "Password must not be blank.")
    private String password;
}
