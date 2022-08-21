package com.baranskagata.optometry.dao;

import com.baranskagata.optometry.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface PatientRepository extends JpaRepository<Patient,Long> {

   Optional<Patient> findById(Long id);

  // Page<Patient> findByLastNameContaining(@RequestParam("lastName") String lastName, Pageable pageable);
}
