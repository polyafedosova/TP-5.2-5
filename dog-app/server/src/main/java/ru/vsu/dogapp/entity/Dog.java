package ru.vsu.dogapp.entity;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table()
public class Dog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotEmpty
    private String name;
    private LocalDate birthday;
    private Boolean sex;
    private String breed;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "owner")
    private Owner owner;
}
