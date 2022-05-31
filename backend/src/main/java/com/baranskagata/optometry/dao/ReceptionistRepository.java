package com.baranskagata.optometry.dao;

import com.baranskagata.optometry.entity.Receptionist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReceptionistRepository extends JpaRepository<Receptionist,Long> {
}
