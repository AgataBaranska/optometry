package com.baranskagata.optometry.service;

import com.baranskagata.optometry.dto.AppointmentDto;
import com.baranskagata.optometry.dto.AppointmentPatientOptometristDto;
import com.baranskagata.optometry.dto.PatientDto;
import com.baranskagata.optometry.entity.Appointment;
import com.baranskagata.optometry.entity.ContactLenses;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AppointmentService {


    List<AppointmentPatientOptometristDto> getAppointments(Optional<String> patientId, Optional<String> optometristId);

    AppointmentPatientOptometristDto getAppointmentById(Long appointmentId);

    AppointmentDto saveAppointment(AppointmentDto appointmentDto);

    void deleteAppointment(Long appointmentId);


    List<Appointment> getOptometristAppointmentsAtDay(Long optometristId, LocalDate day);

    List<Appointment> getPatientAppointmentsAtDay(Long patientId, LocalDate day);


    List<Integer> availableSlotsForOptometristForDay(Long optometristId, LocalDate date);

    List<ContactLenses> getAvailableContactLenses();

    PatientDto getPatientByAppointmentId(Long appointmentId);
}
