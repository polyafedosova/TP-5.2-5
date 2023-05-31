package ru.vsu.dogapp.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@ApiModel(description = "Entity of a veterinary clinic.")
public class VetclinicDto {

    @ApiModelProperty(hidden = true)
    private Integer id;

    @NotBlank(message = "Name must not be blank.")
    @Pattern(regexp = "^[A-Za-zА-Яа-я0-9.,!?:;()\\[\\]{}'\"\\s]{3,40}$",
            message = "Name should be between 3 and 40 characters and contain only letters, digits, and basic punctuation.")
    @ApiModelProperty(value = "Name of a vet clinic.", required = true, example = "Здоровье животных")
    private String name;

    @Size(min = 10, max = 20, message = "Phone number must be between 10 and 20 characters long.")
    @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "Phone number must be a valid international phone number.")
    @ApiModelProperty(value = "Phone number of a vet clinic.", example = "89087803328")
    private String phone;

    @Size(max = 1000, message = "Description should be no more than 1000 characters.")
    @Pattern(regexp = "^[A-Za-zА-Яа-я0-9.,!?:;()\\[\\]{}'\"\\s]{0,1000}$",
            message = "Description should contain only letters, digits, and basic punctuation.")
    @ApiModelProperty(value = "Description (availability of vet pharmacies, etc.)",
            example = "Вход со двора. Рядом находится ветаптека")
    private String description;

    @Size(min = 2, max = 50, message = "Country must be between 2 and 50 characters long.")
    @Pattern(regexp = "^[A-Za-zА-Яа-я0-9()\\[\\]'\"\\s-]+$", message = "Country must be a valid address.")
    @ApiModelProperty(hidden = true, value = "The country of location a vet clinic.", example = "Россия")
    private String country = "Россия";

    @NotBlank(message = "Region must not be blank.")
    @Size(min = 2, max = 100, message = "Region must be between 2 and 100 characters long.")
    @Pattern(regexp = "^[A-Za-zА-Яа-я0-9()\\[\\]'\"\\s-]+$", message = "Region must be a valid address.")
    @ApiModelProperty(value = "The region of location a vet clinic.", required = true, example = "Воронежская область")
    private String region;

    @NotBlank(message = "City must not be blank.")
    @Size(max = 100, message = "City must be no longer than 100 characters.")
    @Pattern(regexp = "^[A-Za-zА-Яа-я0-9()\\[\\]'\"\\s-]+$", message = "City must be a valid address.")
    @ApiModelProperty(value = "The city of location a vet clinic.", required = true, example = "Воронеж")
    private String city;

    @NotBlank(message = "Street must not be blank.")
    @Size(max = 100, message = "Street must be no longer than 100 characters.")
    @Pattern(regexp = "^[A-Za-zА-Яа-я0-9()\\[\\]'\"\\s-]+$", message = "Street must be a valid address.")
    @ApiModelProperty(value = "The street of location a vet clinic.", required = true, example = "Шишкова")
    private String street;

    @NotBlank(message = "House number must not be blank.")
    @Size(max = 10, message = "House number must be no longer than 10 characters.")
    @Pattern(regexp = "^[A-Za-zА-Яа-я0-9()\\[\\]'\"\\s/-]+$", message = "House number must be a valid address.")
    @ApiModelProperty(value = "The house of location a vet clinic.", required = true, example = "72/2")
    private String house;

    public void setPhone(String phoneNumber) {
        if (phoneNumber != null) {
            this.phone = phoneNumber.replaceAll("[^0-9]", "");
        }
    }
}
