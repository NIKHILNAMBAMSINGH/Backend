package com.Backend.ProfileMs.Repository;

import com.Backend.ProfileMs.Entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DoctorRepository extends JpaRepository<Doctor,Doctor> {
    Optional<Doctor> findById(Long id);

    Optional<Doctor> findByEmail(String email);
}
