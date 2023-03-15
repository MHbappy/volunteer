package com.app.volunteer.dto.account;
import lombok.Data;

import javax.persistence.*;

@Data
public class VolunteerInfo {
    private Long id;
    Long volunteerId;
}
