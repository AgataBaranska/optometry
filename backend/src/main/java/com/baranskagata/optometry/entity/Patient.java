package com.baranskagata.optometry.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Patient extends AppUser{

    @OneToMany(mappedBy ="patient", cascade = CascadeType.ALL)
    private List<Appointment> appointmentList;

}
