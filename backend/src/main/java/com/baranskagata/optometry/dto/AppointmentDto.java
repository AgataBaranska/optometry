package com.baranskagata.optometry.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@NoArgsConstructor
@Data
@Builder
@AllArgsConstructor
public class AppointmentDto {
    private String date;
    private Long patientId;
    private Long optometristId;
    private String workName;
    private Integer slot;
}

