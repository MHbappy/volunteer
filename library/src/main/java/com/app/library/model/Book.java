package com.app.library.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "book")
@Data
public class Book {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    private LocalDateTime borrowedDateTime;

    private LocalDateTime returnDateTIme;

    @ManyToOne
    private VolunteerInfo volunteerInfo;

    @Column(name = "is_active")
    private Boolean isActive;
}
