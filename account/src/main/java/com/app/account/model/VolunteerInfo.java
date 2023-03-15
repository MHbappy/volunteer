package com.app.account.model;
import lombok.Data;
import javax.persistence.*;

@Entity
@Table(name = "volunteer_info")
@Data
public class VolunteerInfo {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    Long volunteerId;
}
