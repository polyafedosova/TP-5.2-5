package ru.vsu.dogapp.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Data
@ApiModel(description = "Entity of an event.")
public class EventDto {

    @ApiModelProperty(hidden = true)
    private Integer id;

    @NotBlank(message = "Name of an event must not be blank.")
    @Pattern(regexp = "^[A-Za-zА-Яа-я0-9.,!?:;()\\[\\]{}'\"\\s]{3,40}$",
            message = "Name should be between 3 and 40 characters and contain only letters, digits, and basic punctuation.")
    @ApiModelProperty(value = "Name of an event.", required = true, example = "Запись к ветеринару")
    private String name;

    @ApiModelProperty(value = "Date of an event.", example = "2023-07-23")
    private LocalDate date;

    @ApiModelProperty(value = "Time of an event.", example = "20:30:00")
    private LocalTime time;

    @Size(max = 1000, message = "Description should be no more than 1000 characters.")
    @Pattern(regexp = "^[A-Za-zА-Яа-я0-9.,!?:;()\\[\\]{}'\"\\s]{0,1000}$",
            message = "Description should contain only letters, digits, and basic punctuation.")
    @ApiModelProperty(value = "Description of an event.", example = "Взять ветпаспорт")
    private String description;

    public EventDto(Integer id, String name, String date, String time, String description) throws DateTimeParseException {
        this.id = id;
        this.name = name;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            this.date = LocalDate.parse(date, formatter);
        } catch (DateTimeParseException e) {
            throw new DateTimeParseException("Error parsing date", date, 0);
        }
        try {
            DateTimeFormatter formatterTime = DateTimeFormatter.ofPattern("HH:mm:ss");
            this.time = LocalTime.parse(time, formatterTime);
        } catch (DateTimeParseException e) {
            throw new DateTimeParseException("Error parsing time", time, 0);
        }
        this.description = description;
    }
}