package com.baranskagata.optometry.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collection;


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
                   String email, String street, String city, String postalCode, String country, Collection<Role> roles) {
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
        this.roles = roles;
    }

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Collection<Role> roles;


    @OneToOne(mappedBy = "appUser")
    private Optometrist optometrist;


    @OneToOne(mappedBy = "appUser")
    private Patient patient;


    @OneToOne(mappedBy = "appUser")
    private Admin admin;

    @OneToOne(mappedBy = "appUser")
    private Receptionist receptionist;


}
