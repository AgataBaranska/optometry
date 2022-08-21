package com.baranskagata.optometry.dto;


import com.baranskagata.optometry.entity.AppointmentStatus;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.*;


@NoArgsConstructor
@Data
@Builder
public class AppointmentPatientOptometristDto {
    private Long id;
    private LocalDate date;

    private String patientFirstName;
    private String patientLastName;
    private String optometristFirstName;
    private String optometristLastName;
    private AppointmentStatus status;
    private String workName;
    private Integer slot;

    public AppointmentPatientOptometristDto(Long id, LocalDate date, String patientFirstName, String patientLastName, String optometristFirstName, String optometristLastName, AppointmentStatus status, String workName,Integer slot) {
        this.id = id;
        this.date=date;
        this.patientFirstName = patientFirstName;
        this.patientLastName = patientLastName;
        this.optometristFirstName = optometristFirstName;
        this.optometristLastName = optometristLastName;
        this.status = status;
        this.workName = workName;
        this.slot=slot;
    }

}
