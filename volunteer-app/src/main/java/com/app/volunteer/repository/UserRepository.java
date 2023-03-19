package com.app.volunteer.repository;

import javax.transaction.Transactional;

import com.app.volunteer.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Users, Integer> {
  boolean existsByEmail(String email);
  Users findByEmail(String email);
}
