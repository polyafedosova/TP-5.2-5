package ru.vsu.dogapp.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.sql.Time;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table()
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotEmpty
    private String name;
    private LocalDate date;
    private Time time;
    private String description;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "owner")
    private Owner owner;
}
