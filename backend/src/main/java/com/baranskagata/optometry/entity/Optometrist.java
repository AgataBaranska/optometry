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
public class Optometrist{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long id;

    @Column(name = "optometrist_number")
    private String optometristNumber;

    @OneToMany(mappedBy = "optometrist", cascade = CascadeType.ALL)
    private List<Appointment> appointmentList;

    @ManyToMany
    @JoinTable(name = "optometrist_work", joinColumns = @JoinColumn(name = "id_optometrist"), inverseJoinColumns = @JoinColumn(name = "id_work"))
    private List<Work> works;

    @OneToOne
    @JoinColumn(name="user_id")
    private AppUser appUser;

    @OneToOne(mappedBy = "optometrist", cascade = CascadeType.ALL)
    private WorkingPlan workingPlan;


}
