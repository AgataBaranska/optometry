package com.baranskagata.optometry.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "appointment_card")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppointmentCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @JsonBackReference(value="appointment-appointment-card")
    @OneToOne
    @JoinColumn(name="appointment_id")
    private Appointment appointment;

    @JsonManagedReference(value="appointment-card-appointment-reasons")
    @ManyToMany
    @JoinTable(name = "appointment_reasons", joinColumns = @JoinColumn(name = "id_appointment_card"), inverseJoinColumns = @JoinColumn(name = "id_appointment_reasons"))
    private List<AppointmentReasons> appointmentReasons;

    @JsonManagedReference(value="appointment-card-diseases")
    @ManyToMany
    @JoinTable(name = "appointment_diseases", joinColumns = @JoinColumn(name = "id_appointment_card"), inverseJoinColumns = @JoinColumn(name = "id_disease"))
    private List<Disease> diseases;

    @JsonManagedReference(value = "appointment-card-glasses-correction")
    @OneToOne(mappedBy = "appointmentCard")
    private GlassesCorrection glassesCorrection;

    @JsonManagedReference(value = "appointment-card-lenses-correction")
    @OneToOne(mappedBy = "appointmentCard")
    private ContactLensesCorrection contactLensesCorrection;
}
