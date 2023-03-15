package com.app.volunteer.model;

import lombok.Data;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;

/**
 * A Course.
 */
@Entity
@Table(name = "course")
@Data
public class Course{
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "course_price")
    private Double coursePrice;

    @Column(name = "is_active")
    private Boolean isActive;
}
