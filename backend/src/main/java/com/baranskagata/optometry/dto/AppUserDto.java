package com.baranskagata.optometry.dto;

import com.baranskagata.optometry.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
@Builder
@AllArgsConstructor
public class AppUserDto {

    private String username;
    private String firstName;
    private String lastName;
    private String pesel;
    private String email;
    private String telephone;
    private String street;
    private String city;
    private String country;
    private String postalCode;
    private List<Role> roles;

}
