package com.baranskagata.optometry.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class PatientDto {
    private Long id;
    public String firstName;
    public String lastName;
    public String pesel;
    public String telephone;
    public String email;
}
