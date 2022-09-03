package com.baranskagata.optometry.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Table(name = "contactLenses")
@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class ContactLenses  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long id;

    @Basic
    private String name;

    @Basic
    @Column(name="oxygen_permeability")
    private Integer oxygenPermeability;

    @Basic
    @Column(name="wearing_mode")
    private String mode;

    @JsonBackReference(value = "contact-lenses-lenses-correction")
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "contact_lenses_id")
    private ContactLensesCorrection contactLensesCorrection;

}
