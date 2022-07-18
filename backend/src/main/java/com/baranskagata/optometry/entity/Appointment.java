package com.baranskagata.optometry.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "appointment")
@Data
@NoArgsConstructor

public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "start")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime start;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @Column(name = "end")
    private LocalDateTime end;
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private AppointmentStatus status;
    @ManyToOne
    @JoinColumn(name = "id_work")
    private Work work;

    @JsonBackReference(value="patient-appointments")
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @JsonBackReference(value = "optometrist-appointments")
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "optometrist_id")
    private Optometrist optometrist;

    public Appointment(Long id, LocalDateTime start, LocalDateTime end, AppointmentStatus status, Work work, Patient patient, Optometrist optometrist) {

        this.id = id;
        this.start = start;
        this.end = end;
        this.status = status;
        this.work = work;
        this.patient = patient;
        this.optometrist = optometrist;

    }


}
