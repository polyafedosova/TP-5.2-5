package ru.vsu.dogapp.dto;

import lombok.Data;

import javax.validation.constraints.*;
import java.math.BigDecimal;
@Data
public class TreatmentDto {

    @NotBlank
    @Size(min = 2, max = 70)
    @Pattern(regexp = "^[a-zA-Zа-яА-Я0-9\\s-]+$",
            message = "Name should be between 5 and 30 characters and contain only letters.")
    private String name;

    @NotNull
    @Positive(message = "Price must be a positive value.")
    private BigDecimal price;
}
