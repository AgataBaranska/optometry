package com.baranskagata.optometry.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "optometrist")
@NamedEntityGraph(
        name = "graph.Optometrist.appointmentList",
        attributeNodes = @NamedAttributeNode(value = "appointmentList"))

public class Optometrist extends Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "optometrist_number")
    private String optometristNumber;

    @OneToMany( mappedBy="optometrist",cascade = CascadeType.ALL)
    private List<Appointment> appointmentList;

    public Optometrist() {
    }

    public Optometrist(String firstName, String lastName, String pesel, String telephone, String email, Address address, int id, String optometristNumber, List<Appointment> appointmentList) {
        super(firstName, lastName, pesel, telephone, email, address);
        this.id = id;
        this.optometristNumber = optometristNumber;
        this.appointmentList = appointmentList;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public String getOptometristNumber() {
        return optometristNumber;
    }

    public void setOptometristNumber(String optometristNumber) {
        this.optometristNumber = optometristNumber;
    }

    public List<Appointment> getAppointmentList() {
        return appointmentList;
    }

    public void setAppointmentList(List<Appointment> appointmentList) {
        this.appointmentList = appointmentList;
    }
}
