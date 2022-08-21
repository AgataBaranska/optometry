package com.baranskagata.optometry.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
public class Optometrist{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long id;

    @Column(name = "optometrist_number")
    private String optometristNumber;

    @JsonManagedReference(value = "optometrist-appointments")
    @OneToMany(mappedBy = "optometrist", cascade = CascadeType.ALL)
    private List<Appointment> appointmentList;

    @JsonIgnoreProperties("optometrists")
    @ManyToMany
    @JoinTable(name = "optometrist_work", joinColumns = @JoinColumn(name = "id_optometrist"), inverseJoinColumns = @JoinColumn(name = "id_work"))
    private List<Work> works;


    @JsonBackReference(value = "app-user-optometrist")
    @OneToOne
    @JoinColumn(name="user_id")
    private AppUser appUser;




}
