package com.baranskagata.optometry.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Table(name = "reason")
@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentReasons {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long id;

    @Basic
    private String name;
    @JsonBackReference(value="appointment-card-appointment-reasons")
    @ManyToMany
    @JoinTable(name = "appointment_reasons", joinColumns = @JoinColumn(name = "id_appointment_reasons"), inverseJoinColumns = @JoinColumn(name = "id_appointment_card"))
    private List<AppointmentCard> appointmentCard;

}
