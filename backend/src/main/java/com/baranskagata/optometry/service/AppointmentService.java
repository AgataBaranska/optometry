package com.baranskagata.optometry.service;

import com.baranskagata.optometry.dto.AppointmentPatientOptometrist;
import com.baranskagata.optometry.entity.Appointment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AppointmentService {


    Page<AppointmentPatientOptometrist> loadAppointmentsPatient(Pageable page);
     AppointmentPatientOptometrist getAppointmentById(Long id);

    public Appointment saveAppointment(Appointment appointment);
}
