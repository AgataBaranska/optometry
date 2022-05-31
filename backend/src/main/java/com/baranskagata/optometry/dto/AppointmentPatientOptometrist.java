package com.baranskagata.optometry.dto;


import com.baranskagata.optometry.entity.AppointmentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;


@NoArgsConstructor
@Data
public class AppointmentPatientOptometrist {
    private Long id;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Duration duration;
    private String patientFirstName;
    private String patientLastName;
    private String optometristFirstName;
    private String optometristLastName;
    private AppointmentStatus status;
    private String workName;

    public AppointmentPatientOptometrist(Long id, LocalDateTime startTime, LocalDateTime endTime, String patientFirstName, String patientLastName, String optometristFirstName, String optometristLastName, AppointmentStatus status, String workName) {
        this.id = id;
        this.startTime = startTime;
        this.endTime = endTime;
        this.patientFirstName = patientFirstName;
        this.patientLastName = patientLastName;
        this.optometristFirstName = optometristFirstName;
        this.optometristLastName = optometristLastName;
        this.status = status;
        this.workName = workName;
        this.duration =this.getDuration();
    }

    public Duration getDuration() {
        Instant startInstant = startTime.toInstant(ZoneOffset.UTC);
        Instant endInstant = endTime.toInstant(ZoneOffset.UTC);

        return Duration.between(startInstant, endInstant);
    }
}
