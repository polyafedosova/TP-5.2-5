package ru.vsu.dogapp.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import ru.vsu.dogapp.entity.type.Role;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
@ApiModel(description = "Entity of an owner.")
public class OwnerDto {

    @ApiModelProperty(hidden = true)
    private Integer id;

    @NotBlank(message = "Username must not be blank.")
    @Size(min = 4, max = 30)
    @Pattern(regexp = "^[a-zA-Zа-яА-Я0-9*_@.-]+$",
            message = "Username should be between 4 and 30 characters and contain only letters and digits.")
    @ApiModelProperty(value = "Username (login) of an owner.", required = true, example = "Artem17")
    private String username;

    @NotBlank(message = "Password must not be blank.")
    @Size(min = 8, max = 40)
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@*_]).{8,40}$",
            message = "Password should be between 8 and 40 characters and contain lowercase and uppercase " +
                    "english letters, digit and one of the symbols '@*_'.")
    @ApiModelProperty(value = "Password of an owner.", required = true, example = "Abcd123*@_")
    private String password;

    @NotBlank(message = "Name of an owner must not be blank.")
    @Size(min = 3, max = 30)
    @Pattern(regexp = "^[A-Za-zА-Яа-я0-9\\s]+$",
            message = "Name should be between 3 and 30 characters and contain only letters and digits.")
    @ApiModelProperty(value = "Name of an owner.", required = true, example = "Артем")
    private String name;

    @ApiModelProperty(hidden = true, value = "Roles of an user (admin or user).")
    private Set<Role> roles;
}
