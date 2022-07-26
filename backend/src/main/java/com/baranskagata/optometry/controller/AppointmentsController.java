package com.baranskagata.optometry.controller;

import com.baranskagata.optometry.dto.AppointmentPatientOptometrist;
import com.baranskagata.optometry.dao.Appointment;
import com.baranskagata.optometry.datetimeutil.TimePeriod;
import com.baranskagata.optometry.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
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
        return ResponseEntity.ok().body(appointmentService.getAppointments(pageable));
    }

    @GetMapping("{appointmentId}")
    public ResponseEntity<AppointmentPatientOptometrist> getAppointment(@PathVariable Long appointmentId) {
        return ResponseEntity.ok().body(appointmentService.getAppointmentById(appointmentId)
        );
    }

    @PostMapping()
    public ResponseEntity<Appointment> saveAppointment(@RequestBody Appointment appointment) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("").toUriString());
        return ResponseEntity.created(uri).body(appointmentService.saveAppointment(appointment));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<HttpStatus> deleteAppointment(@PathVariable Long id){
        appointmentService.deleteAppointment(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("commonAvailableHours/{optometristId}/{patientId}/{workId}/{date}")
    public ResponseEntity<List<TimePeriod>> getAvailableTimePeriods(@RequestParam Long optometristId, @RequestParam Long patientId, @RequestParam Long workId, @RequestParam String date) {
        return ResponseEntity.ok().body(appointmentService.getAvailableTimePeriodsForWork(optometristId, patientId, workId, LocalDate.parse(date)));
    }




}


