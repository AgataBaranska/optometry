package com.baranskagata.optometry.repository;

import com.baranskagata.optometry.dao.Patient;

import org.springframework.data.jpa.repository.JpaRepository;




public interface PatientRepository extends JpaRepository<Patient,Long> {

  // Page<Patient> findByLastNameContaining(@RequestParam("lastName") String lastName, Pageable pageable);
}
