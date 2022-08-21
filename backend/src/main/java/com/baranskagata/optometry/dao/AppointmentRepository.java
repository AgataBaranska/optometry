package com.baranskagata.optometry.dao;

import com.baranskagata.optometry.dto.AppointmentPatientOptometristDto;
import com.baranskagata.optometry.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    @Query(value = "SELECT new com.baranskagata.optometry.dto.AppointmentPatientOptometristDto" +
            "(a.id,a.date,up.firstName,up.lastName,uo.firstName," +
            "uo.lastName,a.status,w.name,a.slot)" +
            " FROM Appointment a  join a.patient p join a.optometrist o join a.work w join o.appUser uo join p.appUser up")
    List<AppointmentPatientOptometristDto> findAllAppointments();


    @Query(value = "SELECT new com.baranskagata.optometry.dto.AppointmentPatientOptometristDto" +
            "(a.id,a.date,up.firstName,up.lastName,uo.firstName," +
            "uo.lastName,a.status,w.name,a.slot)" +
            "FROM Appointment a  join a.patient p join a.optometrist o join a.work w join o.appUser uo join p.appUser up WHERE a.id= :id")
    Optional<AppointmentPatientOptometristDto> getAppointmentById(@Param("id") Long id);

    @Query(value = "SELECT a FROM Appointment a WHERE a.optometrist =:optometristId and a.date=:date")
    List<Appointment> findOptometristAppointmentsByDate(@Param("optometristId") Long optometristId, @Param("date") LocalDate date);


    @Query(value = "SELECT a FROM Appointment a WHERE a.patient =:patientId and a.date=:date")
    List<Appointment> findPatientsAppointmentsByDate(@Param("patientId") Long patientId, @Param("date") LocalDate date);


    @Query(value = "SELECT slot FROM Appointment WHERE optometrist_id=:optometristId AND date =:date")
    List<Appointment> findAppointmentsSlotsByOptometristIdByDay(@Param("optometristId")Long optometristId,@Param("date") LocalDate date );

   List<Appointment> findByPatientId(@Param("patientId") Long patientId);
   List<Appointment> findByOptometristId(@Param("optometristId") Long optometristId);

}
