package ru.vsu.dogapp.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Date;

@Data
public class DogDto {

    @NotEmpty
    @Size(min = 5, max = 30)
    @Pattern(regexp = "^[a-zA-Zа-яА-Я\\s]+$",
            message = "Name should be between 5 and 30 characters and contain only letters.")
    private String name;
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private Date birthday;
    @NotNull
    private Boolean sex;
    @NotBlank
    @Size(min = 5, max = 60)
    @Pattern(regexp = "^[а-яА-Я\\-\\s]+$",
            message = "Breed should be between 5 and 60 characters and contain only russian letters.")
    private String breed;
}
