package com.baranskagata.optometry.service;

import com.baranskagata.optometry.dto.AppointmentPatientOptometrist;
import com.baranskagata.optometry.entity.Appointment;
import com.baranskagata.optometry.model.TimePeriod;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface AppointmentService {


    Page<AppointmentPatientOptometrist> loadAppointmentsPatient(Pageable page);

    AppointmentPatientOptometrist getAppointmentById(Long id);

   Appointment saveAppointment(Appointment appointment);


    List<TimePeriod> getAvailableTimePeriodsForWork(Long optometristId, Long patientId, Long workId, LocalDate date);

    List<Appointment> getOptometristAppointmentsAtDay(Long optometristId, LocalDate day);

    List<Appointment> getPatientAppointmentsAtDay(Long patientId, LocalDate day);

    List<TimePeriod> excludeAppointmentsFromTimePeriods(List<TimePeriod> periods, List<Appointment> appointments);
}
