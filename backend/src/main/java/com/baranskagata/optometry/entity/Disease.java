package com.baranskagata.optometry.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Table(name = "disease")
@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Disease {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long id;

    @Basic
    private String name;
    @Basic
    @Column(name="related_organ")
    private String relatedOrgan;
    @JsonManagedReference(value="appointment-card-diseases")

    @JsonBackReference(value="appointment-card-diseases")
    @ManyToMany
    @JoinTable(name = "appointment_diseases", joinColumns = @JoinColumn(name = "id_disease"), inverseJoinColumns = @JoinColumn(name = "id_appointment_card"))
    private List<AppointmentCard> appointmentCards;
}
