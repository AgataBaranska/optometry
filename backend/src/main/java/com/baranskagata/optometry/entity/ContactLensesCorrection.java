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
@Table(name = "contact_lenses_correction")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContactLensesCorrection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @JsonBackReference(value = "appointment-card-lenses-correction")
    @OneToOne
    @JoinColumn(name="appointment_card_id")
    private AppointmentCard appointmentCard;


    @JsonManagedReference(value = "contact-lenses-lenses-correction")
    @OneToMany(mappedBy = "contactLensesCorrection", cascade = CascadeType.ALL)
    private List<ContactLenses> contactLenses;
}
