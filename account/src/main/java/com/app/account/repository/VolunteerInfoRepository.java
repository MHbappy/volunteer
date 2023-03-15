package com.app.account.repository;

import com.app.account.model.VolunteerInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VolunteerInfoRepository extends JpaRepository<VolunteerInfo, Long> {
    VolunteerInfo findByVolunteerId(Long volunteerId);
}
