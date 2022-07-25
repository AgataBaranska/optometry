package com.baranskagata.optometry;

import com.baranskagata.optometry.dao.WorkRepository;
import com.baranskagata.optometry.service.AppointmentService;
import com.baranskagata.optometry.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class OptometryApplication {

    public static void main(String[] args) {
        SpringApplication.run(OptometryApplication.class, args);
    }

    @Bean
    CommandLineRunner run(UserService userService, AppointmentService appointmentService, WorkRepository workRepository) {
        return args -> {
//
//
//            AppUser testAdmin1 = AppUser.builder().username("testAdmin1").password("12345").firstName("John").lastName("Doe").pesel("12334567890")
//                    .email("test@email.com").telephone("123212431").city("Warsaw").country("Poland").postalCode("02-222").build();
//            AppUser testPatient1 = AppUser.builder().username("testPatient1").password("12345").firstName("Bob").lastName("Doe").pesel("12334567890")
//                    .email("test@email.com").telephone("123212431").city("Warsaw").country("Poland").postalCode("02-222").build();
//            AppUser testOptometrist1 = AppUser.builder().username("testOptometrist1").password("12345").firstName("Tom").lastName("Doe").pesel("12334567890")
//                    .email("test@email.com").telephone("123212431").city("Warsaw").country("Poland").postalCode("02-222").build();
//            AppUser testReceptionist1 = AppUser.builder().username("testReceptionist1").password("12345").firstName("Hanna").lastName("Doe").pesel("12334567890")
//                    .email("test@email.com").telephone("123212431").city("Warsaw").country("Poland").postalCode("02-222").build();
//            AppUser testUser1 = AppUser.builder().username("testUser1").password("12345").firstName("Anna").lastName("Doe").pesel("12334567890")
//                    .email("test@email.com").telephone("123212431").city("Warsaw").country("Poland").postalCode("02-222").build();
//
//
//            userService.saveUser(testAdmin1);
//            userService.saveUser(testPatient1);
//            userService.saveUser(testOptometrist1);
//            userService.saveUser(testReceptionist1);
//            userService.saveUser(testUser1);
//
//            userService.addRoleToUser("testAdmin1", "ADMIN");
//            userService.addRoleToUser("testPatient1", "PATIENT");
//            userService.addRoleToUser("testOptometrist1", "OPTOMETRIST");
//            userService.addRoleToUser("testReceptionist1", "RECEPTIONIST");

//            Work work = new Work(null,"Contact lenses","contacts fitting",150,40,new ArrayList<>());
//
//            Appointment appointment = new Appointment(null, LocalDateTime.parse("2022-06-12T12:00:00"), LocalDateTime.parse("2022-06-12T12:30:00"), AppointmentStatus.SCHEDULED,work,testPatient1,null);
//           workRepository.save(work);
//            appointmentService.saveAppointment(appointment);
        };


    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
