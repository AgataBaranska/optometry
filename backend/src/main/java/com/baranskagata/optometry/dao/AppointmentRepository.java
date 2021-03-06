package com.baranskagata.optometry.dao;

import com.baranskagata.optometry.dto.AppointmentPatientOptometrist;
import com.baranskagata.optometry.entity.Appointment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    @Query(value = "SELECT new com.baranskagata.optometry.dto.AppointmentPatientOptometrist" +
            "(a.id,a.start,a.end,up.firstName,up.lastName,uo.firstName," +
            "uo.lastName,a.status,w.name)" +
            " FROM Appointment a  join a.patient p join a.optometrist o join a.work w join o.appUser uo join p.appUser up")
    Page<AppointmentPatientOptometrist> findAllAppointments(Pageable pageable);


    @Query(value = "SELECT new com.baranskagata.optometry.dto.AppointmentPatientOptometrist" +
            "(a.id,a.start,a.end,up.firstName,up.lastName,uo.firstName," +
            "uo.lastName,a.status,w.name)" +
            "FROM Appointment a  join a.patient p join a.optometrist o join a.work w join o.appUser uo join p.appUser up WHERE a.id= :id")
    Optional<AppointmentPatientOptometrist> getAppointmentById(@Param("id") Long id);

    @Query(value = "SELECT a FROM Appointment a WHERE a.optometrist =:optometristId and a.start>=:startPeriod and a.end<=:endPeriod")
    List<Appointment> findByOptometristIdInPeriod(@Param("optometristId") Long optometristId, @Param("startPeriod") LocalDateTime startPeriod, @Param("endPeriod") LocalDateTime endPeriod);

    @Query(value = "SELECT a FROM Appointment a WHERE a.patient =:patientId and a.start>=:startPeriod and a.end<=:endPeriod")
    List<Appointment> findByPatientIdInPeriod(@Param("patientId") Long patientId, @Param("startPeriod") LocalDateTime startPeriod, @Param("endPeriod") LocalDateTime endPeriod);
}
