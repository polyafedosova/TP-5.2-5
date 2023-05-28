package ru.vsu.dogapp.dto;

import lombok.Data;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Data
public class DogDto {

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

    public void setBirthday(String birthday) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        this.birthday = LocalDate.parse(birthday, formatter);
    }

    public DogDto(Integer id, String name, String birthday, Boolean sex, String breed) {
        this.id = id;
        this.name = name;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        this.birthday = LocalDate.parse(birthday, formatter);
        this.sex = sex;
        this.breed = breed;
    }
}
