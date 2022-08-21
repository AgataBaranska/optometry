package com.baranskagata.optometry.service;

import com.baranskagata.optometry.dao.*;
import com.baranskagata.optometry.dto.AppointmentDto;
import com.baranskagata.optometry.dto.AppointmentPatientOptometristDto;
import com.baranskagata.optometry.entity.*;
import com.baranskagata.optometry.exception.AppointmentNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;
    public static final List<Integer> optometristSlots = List.of(9,10,11,12,13,14,15,16);
    private final OptometristRepository optometristRepository;
    private final PatientRepository patientRepository;
    private final WorkRepository workRepository;
    private final UserRepository userRepository;


    @Override
    public List<AppointmentPatientOptometristDto> getAppointments(Optional<String> patientUsername,Optional<String> optometristUsername) {
        if (patientUsername.isEmpty() && optometristUsername.isEmpty()) {
            return appointmentRepository.findAllAppointments();
        }
        if (patientUsername.isPresent() && optometristUsername.isEmpty()) {
            List<AppointmentPatientOptometristDto> appointmentPatientOptometrists = new ArrayList<>();

            AppUser patientAppUser = userRepository.findByUsername(patientUsername.get()).orElseThrow(()->new UsernameNotFoundException("Username not found in database " + patientUsername));
            AppUser optometristAppUser = userRepository.findByUsername(optometristUsername.get()).orElseThrow(()->new UsernameNotFoundException("Username not found in database " + optometristUsername));
            Patient patient = patientAppUser.getPatient();
            Optometrist optometrist = optometristAppUser.getOptometrist();
            List<Appointment> appointments = appointmentRepository.findByPatientId(patient.getId());
            for (Appointment appointment : appointments) {

                appointmentPatientOptometrists.add(AppointmentPatientOptometristDto.builder().patientFirstName(patient.getAppUser().getFirstName())
                        .patientLastName(patient.getAppUser().getLastName())
                        .optometristFirstName(optometrist.getAppUser().getFirstName())
                        .optometristLastName(optometrist.getAppUser().getLastName())
                        .status(appointment.getStatus())
                        .workName(appointment.getWork().getName()).slot(appointment.getSlot()).
                                date(appointment.getDate()).id(appointment.getId()).
                                build());
            }
            return appointmentPatientOptometrists;
        }
        if (patientUsername.isEmpty() && optometristUsername.isPresent()) {
            List<AppointmentPatientOptometristDto> appointmentPatientOptometrists = new ArrayList<>();

            AppUser optometristAppUser = userRepository.findByUsername(optometristUsername.get()).orElseThrow(()->new UsernameNotFoundException("Username not found in database " + optometristUsername));
            Optometrist optometrist = optometristAppUser.getOptometrist();

            List<Appointment> appointments = appointmentRepository.findByOptometristId(optometrist.getId());
            for (Appointment appointment : appointments) {

                Patient patient = appointment.getPatient();
                appointmentPatientOptometrists.add(AppointmentPatientOptometristDto.builder().patientFirstName(patient.getAppUser().getFirstName())
                        .patientLastName(patient.getAppUser().getLastName())
                        .optometristFirstName(optometrist.getAppUser().getFirstName())
                        .optometristLastName(optometrist.getAppUser().getLastName())
                        .status(appointment.getStatus())
                        .workName(appointment.getWork().getName()).slot(appointment.getSlot()).date(appointment.getDate()).id(appointment.getId()).build());
            }
            return appointmentPatientOptometrists;
        }
        return new ArrayList<>();
    }

    @Override
    public AppointmentPatientOptometristDto getAppointmentById(Long appointmentId) {
        return appointmentRepository.getAppointmentById(appointmentId).orElseThrow(() -> new AppointmentNotFoundException("Appointment not found with id: " + appointmentId));
    }

    @Override
    public Appointment saveAppointment(AppointmentDto appointmentDto) {
        log.info("Saving new appointment to db {}", appointmentDto);
        Optometrist optometrist = optometristRepository.getById(appointmentDto.getOptometristId());
        Patient patient= patientRepository.getById(appointmentDto.getPatientId());
        Work work = workRepository.findByName(appointmentDto.getWorkName()).orElse(workRepository.getById(1L));
        Appointment appointment =Appointment.builder().date(LocalDate.parse(appointmentDto.getDate())).patient(patient).optometrist(optometrist).status(AppointmentStatus.SCHEDULED).work(work).slot(appointmentDto.getSlot()).build();
        return appointmentRepository.save(appointment);
    }

    @Override
    public void deleteAppointment(Long appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId).orElseThrow(() -> new AppointmentNotFoundException("Appointment not found with id: " + appointmentId));
        appointmentRepository.delete(appointment);
    }


    public List<Appointment> getOptometristAppointmentsAtDay(Long optometristId, LocalDate day) {
        return appointmentRepository.findOptometristAppointmentsByDate(optometristId, day);
    }
 
    public List<Appointment> getPatientAppointmentsAtDay(Long patientId, LocalDate day) {
        return appointmentRepository.findPatientsAppointmentsByDate(patientId, day);
    }

    @Override
    public List<Integer> availableSlotsForOptometristForDay(Long optometristId,LocalDate date) {
       List<Appointment> optometristAppointmentsSlots =appointmentRepository.findAppointmentsSlotsByOptometristIdByDay(optometristId,date);
        return optometristSlots.stream().filter(slot->!optometristAppointmentsSlots.contains(slot)).collect(Collectors.toList());
    }
}
