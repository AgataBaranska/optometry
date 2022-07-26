package com.baranskagata.optometry.service;

import com.baranskagata.optometry.dto.AppointmentPatientOptometrist;
import com.baranskagata.optometry.dao.Appointment;
import com.baranskagata.optometry.datetimeutil.TimePeriod;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface AppointmentService {


    Page<AppointmentPatientOptometrist> getAppointments(Pageable page);

    AppointmentPatientOptometrist getAppointmentById(Long appointmentId);

    Appointment saveAppointment(Appointment appointment);

    void deleteAppointment(Long appointmentId);

    List<TimePeriod> getAvailableTimePeriodsForWork(Long optometristId, Long patientId, Long workId, LocalDate date);

    List<Appointment> getOptometristAppointmentsAtDay(Long optometristId, LocalDate day);

    List<Appointment> getPatientAppointmentsAtDay(Long patientId, LocalDate day);

    List<TimePeriod> excludeAppointmentsFromTimePeriods(List<TimePeriod> periods, List<Appointment> appointments);


}
