package com.baranskagata.optometry.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long id;

    @JsonManagedReference(value="patient-appointments")
    @OneToMany(mappedBy ="patient", cascade = CascadeType.ALL)
    private List<Appointment> appointmentList;

    @JsonBackReference(value = "app-user-patient")
    @OneToOne()
    @JoinColumn(name="user_id")
    private AppUser appUser;

}
