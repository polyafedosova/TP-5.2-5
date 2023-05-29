package ru.vsu.dogapp.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

@Data
public class VetSimpleDto {

    @NotBlank
    private VetclinicDto vetclinicDto;
    private BigDecimal minPrice;

    public VetSimpleDto(VetclinicDto vetclinicDto, BigDecimal minPrice) {
        this.vetclinicDto = vetclinicDto;
        this.minPrice = minPrice;
    }
}
