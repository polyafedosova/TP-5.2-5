package ru.vsu.dogapp.entity.type;

import jakarta.persistence.Embeddable;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Embeddable
public class Address {
    private String country;
    private String region;
    private String district;
    private String city;
    private String street;
    private String house;
}
