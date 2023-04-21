package ru.vsu.dogapp.dto;

import lombok.Data;
import java.math.BigDecimal;
@Data
public class TreatmentDto {

    private String name;
    private BigDecimal price;
}
