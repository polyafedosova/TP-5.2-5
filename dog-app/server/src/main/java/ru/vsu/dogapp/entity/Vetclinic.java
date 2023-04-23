package ru.vsu.dogapp.entity;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class Vetclinic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotEmpty
    private String name;
    private String phone;
    private String description;
    private String country;
    private String region;
    private String district;
    private String city;
    private String street;
    private String house;
}
