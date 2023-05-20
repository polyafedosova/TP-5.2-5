package ru.vsu.dogapp.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.Date;

@Data
public class DogDto {

    @JsonIgnore
    private Integer id;
    @NotEmpty
    @Size(min = 2, max = 30)
    @Pattern(regexp = "^[a-zA-Zа-яА-Я\\s]+$",
            message = "Name should be between 2 and 30 characters and contain only letters.")
    private String name;
    private LocalDate birthday;
    @NotNull
    private Boolean sex;
    @NotBlank
    @Size(min = 2, max = 60)
    @Pattern(regexp = "^[а-яА-Я\\-\\s]+$",
            message = "Breed should be between 2 and 60 characters and contain only russian letters.")
    private String breed;
}
