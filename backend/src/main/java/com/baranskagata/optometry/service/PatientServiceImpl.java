package com.baranskagata.optometry.service;

import com.baranskagata.optometry.dao.AppointmentReasonsRepository;
import com.baranskagata.optometry.dao.DiseaseRepository;
import com.baranskagata.optometry.dao.PatientRepository;
import com.baranskagata.optometry.dao.UserRepository;
import com.baranskagata.optometry.dto.PatientDto;
import com.baranskagata.optometry.entity.AppUser;
import com.baranskagata.optometry.entity.AppointmentReasons;
import com.baranskagata.optometry.entity.Disease;
import com.baranskagata.optometry.entity.Patient;
import com.baranskagata.optometry.exception.PatientNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class PatientServiceImpl implements PatientService{

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DiseaseRepository eyeDiseaseRepository;

    @Autowired
    private AppointmentReasonsRepository appointmentReasonsRepository;


    @Override
    public PatientDto getPatient(Long patientId) {
        Patient patient = patientRepository.findById(patientId).orElseThrow(()-> new PatientNotFoundException("Patient not found in db with id: " + patientId));
        AppUser appUser = patient.getAppUser();

       return mapToPatientDto(patient,appUser);
    }

    @Override
    public List<Disease> getAvailableDiseases(String relatedOrgan) {
        return eyeDiseaseRepository.findByRelatedOrgan(relatedOrgan);
    }

    @Override
    public List<AppointmentReasons> getAvailableAppointmentReasons() {
        return appointmentReasonsRepository.findAll();
    }

    private PatientDto mapToPatientDto(Patient patient, AppUser appUser) {
       return PatientDto.builder()
                .id(patient.getId())
                .firstName(appUser.getFirstName())
                .lastName(appUser.getLastName())
                .email(appUser.getEmail())
                .telephone(appUser.getTelephone())
                .pesel(appUser.getPesel()).build();
    }

}
