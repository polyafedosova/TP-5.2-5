package ru.vsu.dogapp.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Data
public class EventDto {

    private Integer id;
    @NotBlank
    @Pattern(regexp = "^[A-Za-zА-Яа-я0-9.,!?:;()\\[\\]{}'\"\\s]{3,40}$",
            message = "Name should be between 3 and 40 characters and contain only letters, digits, and basic punctuation.")
    private String name;
    private LocalDate date;
    private LocalTime time;
    @Size(max = 1000, message = "Description should be no more than 1000 characters.")
    @Pattern(regexp = "^[A-Za-zА-Яа-я0-9.,!?:;()\\[\\]{}'\"\\s]{0,1000}$",
            message = "Description should contain only letters, digits, and basic punctuation.")
    private String description;

    public void setDate(String date) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            this.date = LocalDate.parse(date, formatter);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    public void setTime(String time) {
        String format = "HH:mm:ss";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        try {
            this.time = LocalTime.parse(time, formatter);
        } catch (DateTimeParseException e) {
            System.out.println("Ошибка парсинга времени: " + e.getMessage());
        }
    }

    public EventDto(Integer id, String name, String date, String time, String description) {
        this.id = id;
        this.name = name;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        this.date = LocalDate.parse(date, formatter);
        String format = "HH:mm:ss";
        DateTimeFormatter formatterTime = DateTimeFormatter.ofPattern(format);
        try {
            this.time = LocalTime.parse(time, formatterTime);
        } catch (DateTimeParseException e) {
            System.out.println("Ошибка парсинга времени: " + e.getMessage());
        }
        this.description = description;
    }
}
