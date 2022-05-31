package com.baranskagata.optometry.dao;

import com.baranskagata.optometry.entity.Patient;

import org.springframework.data.jpa.repository.JpaRepository;




public interface PatientRepository extends JpaRepository<Patient,Long> {

  // Page<Patient> findByLastNameContaining(@RequestParam("lastName") String lastName, Pageable pageable);
}
