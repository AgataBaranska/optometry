package com.baranskagata.optometry.dao;

import com.baranskagata.optometry.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepository extends JpaRepository<Appointment,Integer> {
}
