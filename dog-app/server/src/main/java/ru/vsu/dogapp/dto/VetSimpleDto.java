package ru.vsu.dogapp.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

@Data
@ApiModel(description = "The veterinary clinic and minimum price for veterinary clinic treatments " +
        "that contain the transmitted value \"treatment\" in the name.")
public class VetSimpleDto {

    @NotBlank
    private VetclinicDto vetclinicDto;
    @ApiModelProperty(value = "The minimum price for treatments.", example = "1000.00")
    private BigDecimal minPrice;

    public VetSimpleDto(VetclinicDto vetclinicDto, BigDecimal minPrice) {
        this.vetclinicDto = vetclinicDto;
        this.minPrice = minPrice;
    }
}
