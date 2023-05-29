package ru.vsu.dogapp.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.HashSet;
import java.util.Set;

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
    @OneToMany(mappedBy = "vetclinic", cascade = CascadeType.ALL)
    private Set<Treatment> treatments = new HashSet<>();
}
