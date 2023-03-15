package com.app.volunteer.repository;

import com.app.volunteer.model.Volunteer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@SuppressWarnings("unused")
@Repository
public interface VolunteerRepository extends JpaRepository<Volunteer, Long> {
    Volunteer findByUsers_Id(Integer userId);
}
