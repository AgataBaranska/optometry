package com.baranskagata.optometry.controller;

import com.baranskagata.optometry.dto.AppointmentDto;
import com.baranskagata.optometry.dto.AppointmentPatientOptometristDto;
import com.baranskagata.optometry.entity.Appointment;
import com.baranskagata.optometry.entity.ContactLenses;
import com.baranskagata.optometry.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@Slf4j
@CrossOrigin("http://localhost:4200/")
@RequestMapping("/appointments")
public class AppointmentsController {
    private final AppointmentService appointmentService;

    @GetMapping()
    public ResponseEntity<List<AppointmentPatientOptometristDto>> getAllAppointments(@RequestParam(required = false) Optional<String> patientUsername,@RequestParam(required = false)  Optional<String> optometristUsername) {



        return ResponseEntity.ok().body(appointmentService.getAppointments(patientUsername,optometristUsername));
    }

    @GetMapping("{appointmentId}")
    public ResponseEntity<AppointmentPatientOptometristDto> getAppointment(@PathVariable Long appointmentId) {
        return ResponseEntity.ok().body(appointmentService.getAppointmentById(appointmentId)
        );
    }

    @PostMapping()
    public ResponseEntity<Appointment> saveAppointment(@RequestBody AppointmentDto appointmentDTO) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("").toUriString());
        return ResponseEntity.created(uri).body(appointmentService.saveAppointment(appointmentDTO));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<HttpStatus> deleteAppointment(@PathVariable Long id){
        appointmentService.deleteAppointment(id);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/slots")
    public ResponseEntity<List<Integer>> getAvailableSlotsForOptometristForDay(@RequestParam  Long optometristId, @RequestParam String date) {
        return ResponseEntity.ok().body(appointmentService.availableSlotsForOptometristForDay(optometristId,LocalDate.parse(date)));
    }

    @GetMapping("/contactLenses")
    public ResponseEntity<List<ContactLenses>> getAvailableContactLenses() {
        return ResponseEntity.ok().body(appointmentService.getAvailableContactLenses());
    }



}



