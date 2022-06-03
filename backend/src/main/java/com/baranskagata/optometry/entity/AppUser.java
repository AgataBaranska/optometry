package com.baranskagata.optometry.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@Table(name = "user")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long id;

    @Basic
    private String username;
    @Basic
    private String password;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Basic
    private String pesel;
    @Basic
    private String telephone;
    @Basic
    private String email;
    @Basic
    private String street;
    @Basic
    private String city;
    @Column(name = "postal_code")
    private String postalCode;
    @Basic
    private String country;

    public AppUser(Long id, String username, String password, String firstName, String lastName, String pesel, String telephone,
                   String email, String street, String city, String postalCode, String country) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.pesel = pesel;
        this.telephone = telephone;
        this.email = email;
        this.street = street;
        this.city = city;
        this.postalCode = postalCode;
        this.country = country;
        this.roles = new ArrayList<>();

    }

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles;


    @OneToOne(mappedBy = "appUser",cascade = CascadeType.ALL)
    private Optometrist optometrist;


    @OneToOne(mappedBy = "appUser",cascade = CascadeType.ALL)
    private Patient patient;


    @OneToOne(mappedBy = "appUser",cascade = CascadeType.ALL)
    private Admin admin;

    @OneToOne(mappedBy = "appUser",cascade = CascadeType.ALL)
    private Receptionist receptionist;


}
