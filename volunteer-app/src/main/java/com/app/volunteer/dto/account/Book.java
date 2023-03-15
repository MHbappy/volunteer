package com.app.volunteer.dto.account;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
public class Book {
    private Long id;
    private String name;
    private LocalDateTime borrowedDateTime;
    private LocalDateTime returnDateTIme;
    private VolunteerInfo volunteerInfo;
    private Boolean isActive;
}
