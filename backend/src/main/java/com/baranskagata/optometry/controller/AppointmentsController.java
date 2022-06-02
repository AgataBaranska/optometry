package com.baranskagata.optometry.controller;

import com.baranskagata.optometry.dto.AppointmentPatientOptometrist;
import com.baranskagata.optometry.entity.Appointment;
import com.baranskagata.optometry.model.TimePeriod;
import com.baranskagata.optometry.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@RestController
@Slf4j
@CrossOrigin("http://localhost:4200/")
@RequestMapping("/appointments")
public class AppointmentsController {
    private final AppointmentService appointmentService;

    @GetMapping()
    public ResponseEntity<Page<AppointmentPatientOptometrist>> getAllAppointments(Pageable pageable) {
        return ResponseEntity.ok().body(appointmentService.loadAppointmentsPatient(pageable));
    }

    @GetMapping("/appointments/{id}")
    public ResponseEntity<AppointmentPatientOptometrist> getAppointments(@PathVariable Long id) {
        return ResponseEntity.ok().body(appointmentService.getAppointmentById(id)
        );
    }

    @GetMapping("appointments/availableHours/{optometristId}/{patientId}/{workId}/{date}")
    public ResponseEntity<List<TimePeriod>> getAvailableTimePeriods(@PathVariable Long optometristId, @PathVariable Long patientId, @PathVariable Long workId, @PathVariable String date) {
        return ResponseEntity.ok().body(appointmentService.getAvailableTimePeriodsForWork(optometristId, patientId, workId, LocalDate.parse(date)));
    }

    @PostMapping("/appointments/save")
    public ResponseEntity<Appointment> saveNewAppointment(@RequestBody Appointment appointment) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/appointments/save").toUriString());
        return ResponseEntity.created(uri).body(appointmentService.saveAppointment(appointment));
    }


}


