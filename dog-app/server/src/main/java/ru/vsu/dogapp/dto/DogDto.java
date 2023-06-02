package ru.vsu.dogapp.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Data
@ApiModel(description = "Entity of a dog.")
public class DogDto {

    @ApiModelProperty(hidden = true)
    private Integer id;

    @NotBlank(message = "Name of a dog must not be blank.")
    @Size(min = 2, max = 30)
    @Pattern(regexp = "^[a-zA-Zа-яА-Я\\s]+$",
            message = "Name should be between 2 and 30 characters and contain only letters.")
    @ApiModelProperty(value = "Name of a dog.", required = true, example = "Рекс")
    private String name;

    @ApiModelProperty(value = "Dog`s birthday.", example = "2020-07-23")
    private LocalDate birthday;

    @ApiModelProperty(value = "Sex of a dog (true - male, false - female).", example = "true")
    private Boolean sex;

    @Size(min = 2, max = 60)
    @Pattern(regexp = "^[а-яА-Я\\-\\s]+$",
            message = "Breed should be between 2 and 60 characters and contain only russian letters.")
    @ApiModelProperty(value = "Breed of a dog.", example = "Немецкая овчарка")
    private String breed;

    public DogDto(Integer id, String name, String birthday, Boolean sex, String breed) {
        this.id = id;
        this.name = name;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            this.birthday = LocalDate.parse(birthday, formatter);
        } catch (DateTimeParseException e) {
            System.out.println("Error of parsing date: " + e.getMessage());
        }
        this.sex = sex;
        this.breed = breed;
    }

    public void setBirthday(String birthday) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            this.birthday = LocalDate.parse(birthday, formatter);
        } catch (DateTimeParseException e) {
            System.out.println("Error of parsing date: " + e.getMessage());
        }
    }

}
