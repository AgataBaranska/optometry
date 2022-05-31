package com.baranskagata.optometry.service;

import com.baranskagata.optometry.dao.AppointmentRepository;
import com.baranskagata.optometry.dto.AppointmentPatientOptometrist;
import com.baranskagata.optometry.entity.AppUser;
import com.baranskagata.optometry.entity.Appointment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AppointmentServiceImpl implements AppointmentService{

    private final AppointmentRepository appointmentRepository;
    @Override
    public Page<AppointmentPatientOptometrist> loadAppointmentsPatient(Pageable page) {
        return appointmentRepository.loadAppointments(page);
    }

    @Override
    public AppointmentPatientOptometrist getAppointmentById(Long id) {
        return appointmentRepository.getAppointmentById(id);
    }
    @Override
    public Appointment saveAppointment(Appointment appointment) {
        log.info("Saving new appoitment to db {}", appointment);
        //validation

        return appointmentRepository.save(appointment);
    }
}
