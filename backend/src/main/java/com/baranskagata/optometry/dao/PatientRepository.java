package com.baranskagata.optometry.dao;

import com.baranskagata.optometry.entity.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


public interface PatientRepository extends JpaRepository<Patient,Integer> {

   Page<Patient> findByLastNameContaining(@RequestParam("lastName") String lastName, Pageable pageable);
}
