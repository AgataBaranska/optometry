package com.baranskagata.optometry.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Receptionist{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long id;

    @OneToMany( cascade = CascadeType.ALL)
    private List<Language> languagesList;


    @JsonBackReference(value = "app-user-receptionist")
    @OneToOne()
    @JoinColumn(name="user_id")
    private AppUser appUser;

}
