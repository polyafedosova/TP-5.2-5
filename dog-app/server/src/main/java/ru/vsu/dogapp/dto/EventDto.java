package ru.vsu.dogapp.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
public class EventDto {

    @JsonIgnore
    private Integer id;
    @NotBlank
    @Pattern(regexp = "^[A-Za-zА-Яа-я0-9.,!?:;()\\[\\]{}'\"\\s]{3,40}$",
            message = "Name should be between 3 and 40 characters and contain only letters, digits, and basic punctuation.")
    private String name;
    private LocalDate date;
    @Size(max = 1000, message = "Description should be no more than 1000 characters.")
    @Pattern(regexp = "^[A-Za-zА-Яа-я0-9.,!?:;()\\[\\]{}'\"\\s]{0,1000}$",
            message = "Description should contain only letters, digits, and basic punctuation.")
    private String description;
}
