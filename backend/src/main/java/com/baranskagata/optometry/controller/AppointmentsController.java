package com.baranskagata.optometry.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.baranskagata.optometry.dto.AppointmentPatientOptometrist;
import com.baranskagata.optometry.entity.AppUser;
import com.baranskagata.optometry.entity.Role;
import com.baranskagata.optometry.service.AppointmentService;
import com.baranskagata.optometry.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequiredArgsConstructor
@RestController
@Slf4j
@CrossOrigin("http://localhost:4200/")
public class AppointmentsController {
    private final AppointmentService appointmentService;


    @GetMapping("/appointments")
    public ResponseEntity<Page<AppointmentPatientOptometrist>> getAppointments(Pageable pageable) {
        return ResponseEntity.ok().body(appointmentService.loadAppointmentsPatient(pageable));
    }

    @GetMapping("/appointments/{id}")
    public ResponseEntity<AppointmentPatientOptometrist> getAppointments(@PathVariable Long id) {
        return ResponseEntity.ok().body(appointmentService.getAppointmentById(id)
        );
    }
}


