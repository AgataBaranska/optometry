package com.baranskagata.optometry.dao;

import com.baranskagata.optometry.entity.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AppointmentRepositoryTest {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AppointmentRepository appointmentRepository;



    @Test
    @DisplayName("test findAllAppointments")
    void givenAppointmentPatientOptometristDto_whenFindAllAppointments_thenReturnAppointmentPatientOptometristPage() {

        AppUser appUserPatient1 = AppUser.builder()
                .username("patient1")
                .password("12345")
                .firstName("Ala")
                .lastName("Bak")
                .pesel("12345678909")
                .telephone("234234567")
                .email("alabak@gmail.com")
                .street("Wakacyjna")
                .city("Warsaw")
                .postalCode("123321")
                .country("Poland")
                .build();
        appUserPatient1.setPatient(new Patient());

        userRepository.save(appUserPatient1);

        AppUser appUserPatient2 = AppUser.builder()
                .username("patient2")
                .password("12345")
                .firstName("Ala")
                .lastName("Bak")
                .pesel("12345678909")
                .telephone("234234567")
                .email("alabak@gmail.com")
                .street("Wakacyjna")
                .city("Warsaw")
                .postalCode("123321")
                .country("Poland")
                .build();
        appUserPatient2.setPatient(new Patient());

        userRepository.save(appUserPatient2);

        AppUser appUserOptometrist1 = AppUser.builder()
                .username("optometrist1")
                .password("12345")
                .firstName("Ala")
                .lastName("Bak")
                .pesel("12345678909")
                .telephone("234234567")
                .email("alabak@gmail.com")
                .street("Wakacyjna")
                .city("Warsaw")
                .postalCode("123321")
                .country("Poland")
                .build();
        appUserOptometrist1.setOptometrist(new Optometrist());
        userRepository.save(appUserOptometrist1);

        AppUser appUserOptometrist2 = AppUser.builder()
                .username("optometrist2")
                .password("12345")
                .firstName("Ala")
                .lastName("Bak")
                .pesel("12345678909")
                .telephone("234234567")
                .email("alabak@gmail.com")
                .street("Wakacyjna")
                .city("Warsaw")
                .postalCode("123321")
                .country("Poland")
                .build();
        appUserOptometrist2.setOptometrist(new Optometrist());

        userRepository.save(appUserOptometrist2);

        Appointment appointment = Appointment.builder()
                .patient(appUserPatient1.getPatient())
                .optometrist(appUserOptometrist1.getOptometrist())
                .start(LocalDateTime.of(2022,10,21,12,00,00,00))
                .end(LocalDateTime.of(2022,10,21,12,30,00,00))
                .status(AppointmentStatus.SCHEDULED)
                .build();
        appointmentRepository.save(appointment);

       // AppointmentPatientOptometrist appointmentPatientOptometrist = appointmentRepository.findAllAppointments();




    }
}

//   @Query(value = "SELECT new com.baranskagata.optometry.dto.AppointmentPatientOptometrist" +
//            "(a.id,a.start,a.end,up.firstName,up.lastName,uo.firstName," +
//            "uo.lastName,a.status,w.name)" +
//            " FROM Appointment a  join a.patient p join a.optometrist o join a.work w join o.appUser uo join p.appUser up")
//    Page<AppointmentPatientOptometrist> findAllAppointments(Pageable pageable);