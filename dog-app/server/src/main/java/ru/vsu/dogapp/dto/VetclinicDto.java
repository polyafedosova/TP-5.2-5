package ru.vsu.dogapp.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
public class VetclinicDto {

    @JsonIgnore
    private Integer id;
    @NotBlank
    @Pattern(regexp = "^[A-Za-zА-Яа-я0-9.,!?:;()\\[\\]{}'\"\\s]{3,40}$",
            message = "Name should be between 3 and 40 characters and contain only letters, digits, and basic punctuation.")
    private String name;

    @NotBlank(message = "Phone number must not be blank.")
    @Size(min = 10, max = 20, message = "Phone number must be between 10 and 20 characters long.")
    @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "Phone number must be a valid international phone number.")
    private String phone;

    @Size(max = 1000, message = "Description should be no more than 1000 characters.")
    @Pattern(regexp = "^[A-Za-zА-Яа-я0-9.,!?:;()\\[\\]{}'\"\\s]{0,1000}$",
            message = "Description should contain only letters, digits, and basic punctuation.")
    private String description;

    @NotBlank(message = "Country must not be blank.")
    @Size(min = 2, max = 50, message = "Country must be between 2 and 50 characters long.")
    @Pattern(regexp = "^[A-Za-zА-Яа-я0-9()\\[\\]'\"\\s-]+$", message = "Country must be a valid address.")
    private String country;

    @NotBlank(message = "Region must not be blank.")
    @Size(min = 2, max = 100, message = "Region must be between 2 and 100 characters long.")
    @Pattern(regexp = "^[A-Za-zА-Яа-я0-9()\\[\\]'\"\\s-]+$", message = "Region must be a valid address.")
    private String region;

    @Size(max = 100, message = "District must be no longer than 100 characters.")
    @Pattern(regexp = "^[A-Za-zА-Яа-я0-9()\\[\\]'\"\\s-]+$", message = "District must be a valid address.")
    private String district;

    @Size(max = 100, message = "City must be no longer than 100 characters.")
    @Pattern(regexp = "^[A-Za-zА-Яа-я0-9()\\[\\]'\"\\s-]+$", message = "City must be a valid address.")
    private String city;

    @NotBlank(message = "Street must not be blank.")
    @Size(max = 100, message = "Street must be no longer than 100 characters.")
    @Pattern(regexp = "^[A-Za-zА-Яа-я0-9()\\[\\]'\"\\s-]+$", message = "Street must be a valid address.")
    private String street;

    @NotBlank(message = "House number must not be blank.")
    @Size(max = 10, message = "House number must be no longer than 10 characters.")
    @Pattern(regexp = "^[A-Za-zА-Яа-я0-9()\\[\\]'\"\\s/-]+$", message = "House number must be a valid address.")
    private String house;
    public void setPhone(String phoneNumber) {
        if (phoneNumber != null) {
            this.phone = phoneNumber.replaceAll("[^0-9]", "");
        }
    }
}