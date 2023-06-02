package ru.vsu.dogapp.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.*;
import java.math.BigDecimal;

@Data
@ApiModel(description = "Entity of a treatment.")
public class TreatmentDto {

    @ApiModelProperty(hidden = true)
    private Integer id;

    @NotBlank(message = "Name of a treatment must not be blank.")
    @Size(min = 2, max = 70)
    @Pattern(regexp = "^[a-zA-Zа-яА-Я0-9\\s-]+$",
            message = "Name should be between 5 and 30 characters and contain only letters.")
    @ApiModelProperty(value = "Name of a treatment.", required = true, example = "УЗИ")
    private String name;

    @Positive(message = "Price must be a positive value.")
    @ApiModelProperty(value = "Price of a treatment.", required = true, example = "1750.00")
    private BigDecimal price;
}
