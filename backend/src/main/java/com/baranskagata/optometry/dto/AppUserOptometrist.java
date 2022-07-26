package com.baranskagata.optometry.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AppUserOptometrist {
    private Long id;
    private String firstName;
    private String lastName;
    private String pesel;
    private String telephone;
    private String email;
    private String street;
    private String city;
    private String postalCode;
    private String country;
    private String optometristNumber;
}
