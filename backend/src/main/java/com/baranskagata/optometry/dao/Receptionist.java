package com.baranskagata.optometry.dao;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Receptionist{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long id;


    @JsonBackReference(value = "app-user-receptionist")
    @OneToOne()
    @JoinColumn(name="user_id")
    private AppUser appUser;

}
