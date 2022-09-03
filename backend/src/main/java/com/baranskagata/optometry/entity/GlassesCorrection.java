package com.baranskagata.optometry.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "glasses_correction")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GlassesCorrection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @JsonBackReference(value = "appointment-card-glasses-correction")
    @OneToOne
    @JoinColumn(name="appointment_card_id")
    private AppointmentCard appointmentCard;



}
