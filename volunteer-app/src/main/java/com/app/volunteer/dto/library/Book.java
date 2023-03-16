package com.app.volunteer.dto.library;

import com.app.volunteer.dto.VolunteerInfo;
import com.app.volunteer.enumuration.BookStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Book {
    private Long id;
    private String name;
    private LocalDateTime borrowedDateTime;
    private LocalDateTime returnDateTIme;
    private VolunteerInfo volunteerInfo;
    private BookStatus bookStatus;
    private String isbn;
    private Boolean isActive;
}
