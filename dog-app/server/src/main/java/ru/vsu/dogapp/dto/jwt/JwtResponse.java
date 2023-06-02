package ru.vsu.dogapp.dto.jwt;


import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public class JwtResponse {

    @ApiModelProperty(value = "Type of token")
    private final String type = "Bearer";
    @ApiModelProperty(value = "Token")
    private String accessToken;
}
