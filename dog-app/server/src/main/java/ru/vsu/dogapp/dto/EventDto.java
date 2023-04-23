package ru.vsu.dogapp.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
public class EventDto {

    @NotBlank
    @Pattern(regexp = "^[A-Za-zА-Яа-я0-9.,!?:;()\\[\\]{}'\"\\s]{3,40}$",
            message = "Name should be between 3 and 40 characters and contain only letters, digits, and basic punctuation.")
    private String name;
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private Date date;
    @Size(max = 1000, message = "Description should be no more than 1000 characters.")
    @Pattern(regexp = "^[A-Za-zА-Яа-я0-9.,!?:;()\\[\\]{}'\"\\s]{0,1000}$",
            message = "Description should contain only letters, digits, and basic punctuation.")
    private String description;
}
