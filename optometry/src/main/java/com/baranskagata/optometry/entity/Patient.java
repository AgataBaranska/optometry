package com.baranskagata.optometry.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "patient")
@NamedEntityGraph(
        name = "graph.Patient.appointmentList.optometrist",
        attributeNodes = @NamedAttributeNode(value = "appointmentList", subgraph = "subgraph.appointment"),
        subgraphs = {
                @NamedSubgraph(name = "subgraph.appointment",
                        attributeNodes = @NamedAttributeNode(value = "optometrist")),
        })
public class Patient extends Person {

    @OneToMany(mappedBy ="patient", cascade = CascadeType.ALL)
    private List<Appointment> appointmentList;

    public Patient() {
    }

    public Patient(String firstName, String lastName, String pesel, String telephone, String email, Address address, List<Appointment> appointmentList) {
        super(firstName, lastName, pesel, telephone, email, address);
    }

    public List<Appointment> getAppointmentList() {
        return appointmentList;
    }

    public void setAppointmentList(List<Appointment> appointmentList) {
        this.appointmentList = appointmentList;
    }
}
