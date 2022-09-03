package com.baranskagata.optometry.service;

import com.baranskagata.optometry.dto.PatientDto;
import com.baranskagata.optometry.entity.AppointmentReasons;
import com.baranskagata.optometry.entity.Disease;

import java.util.List;

public interface PatientService {

    PatientDto getPatient(String id);

    List<Disease> getAvailableDiseases(String relatedOrgan);

    List<AppointmentReasons> getAvailableAppointmentReasons();

    List<PatientDto> getAllPatients();
}
