package com.baranskagata.optometry.dto;

import com.baranskagata.optometry.entity.Address;
import com.baranskagata.optometry.entity.AppUser;
import lombok.Data;

@Data
public class Registration {
    private AppUser user;
    private Address address;
}
