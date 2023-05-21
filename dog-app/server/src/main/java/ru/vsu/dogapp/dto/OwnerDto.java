package ru.vsu.dogapp.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import ru.vsu.dogapp.entity.type.Role;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
public class OwnerDto {

    @JsonIgnore
    private Integer id;
    @NotBlank
    @Size(min = 5, max = 30)
    @Pattern(regexp = "^[a-zA-Zа-яА-Я0-9*_@.-]+$",
            message = "Username should be between 5 and 30 characters and contain only letters and digits.")
    private String username;

    @NotBlank
    @Size(min = 8, max = 40)
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@*_]).{8,40}$",
            message = "Password should be between 8 and 40 characters and contain at least one lowercase and uppercase " +
                    "english letters, digit and one of the symbols '@*_'.")
    private String password;


    @NotBlank
    @Size(min = 3, max = 30)
    @Pattern(regexp = "^[A-Za-zА-Яа-я0-9\\s]+$",
            message = "Name should be between 3 and 30 characters and contain only letters and digits.")
    private String name;
    private Set<Role> roles;
}
