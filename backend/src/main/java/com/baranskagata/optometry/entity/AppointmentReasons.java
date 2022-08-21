package com.baranskagata.optometry.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
@Table(name = "appointmentReasons")
@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentReasons {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long id;

    @Basic
    private String name;
}
