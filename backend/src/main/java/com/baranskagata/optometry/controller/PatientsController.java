package com.baranskagata.optometry.controller;

import com.baranskagata.optometry.dto.PatientDto;
import com.baranskagata.optometry.entity.AppointmentReasons;
import com.baranskagata.optometry.entity.Disease;
import com.baranskagata.optometry.service.PatientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@Slf4j
@CrossOrigin("http://localhost:4200/")
@RequestMapping("/patients")
public class PatientsController {
    private final PatientService patientService;

    @GetMapping({"{id}"})
    public ResponseEntity<PatientDto> getById(@PathVariable Long id){
        return ResponseEntity.ok().body(patientService.getPatient(id));
    }

    @GetMapping({"/availableDiseases"})
    public ResponseEntity<List<Disease>> getAvailableEyeDiseases(@RequestParam String relatedOrgan){
        return ResponseEntity.ok().body(patientService.getAvailableDiseases(relatedOrgan));
    }


@GetMapping("/availableAppointmentReasons")
public ResponseEntity<List<AppointmentReasons>> getAvailableAppointmentReasons(){
    return ResponseEntity.ok().body(patientService.getAvailableAppointmentReasons());
}
}
