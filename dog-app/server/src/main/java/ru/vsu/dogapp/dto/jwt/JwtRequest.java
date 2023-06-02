package ru.vsu.dogapp.dto.jwt;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Setter
@Getter
@ApiModel(description = "Entity of an user (username and password) for login and getting token.")
public class JwtRequest {

    @NotBlank(message = "Username must not be blank.")
    @ApiModelProperty(value = "Username", required = true, example = "Artem17")
    private String username;
    @NotBlank(message = "Password must not be blank.")
    @ApiModelProperty(value = "Password", required = true, example = "Abcd123*@_")
    private String password;
}
