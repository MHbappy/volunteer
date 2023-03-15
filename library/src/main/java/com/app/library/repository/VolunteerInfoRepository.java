package com.app.library.repository;

import com.app.library.model.VolunteerInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VolunteerInfoRepository extends JpaRepository<VolunteerInfo, Long> {
    VolunteerInfo findByVolunteerId(Long volunteerId);
}
