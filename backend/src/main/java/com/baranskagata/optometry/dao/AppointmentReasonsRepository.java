package com.baranskagata.optometry.dao;

import com.baranskagata.optometry.entity.AppointmentReasons;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentReasonsRepository extends JpaRepository<AppointmentReasons,Long> {
}
