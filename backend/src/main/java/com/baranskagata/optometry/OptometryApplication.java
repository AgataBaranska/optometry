package com.baranskagata.optometry;

import com.baranskagata.optometry.dao.RoleRepository;
import com.baranskagata.optometry.dao.WorkRepository;
import com.baranskagata.optometry.service.AppointmentService;
import com.baranskagata.optometry.service.OptometristService;
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
    CommandLineRunner run(UserService userService, AppointmentService appointmentService, OptometristService optometristService,RoleRepository roleRepository, WorkRepository workRepository) {
        return args -> {

//            Set<Role> appRoles = new HashSet<>();
//            appRoles.add(new Role(null, "USER"));
//            appRoles.add(new Role(null, "ADMIN"));
//            appRoles.add(new Role(null, "OPTOMETRIST"));
//            appRoles.add(new Role(null, "PATIENT"));
//            appRoles.add(new Role(null, "RECEPTIONIST"));
//            roleRepository.saveAll(appRoles);
//
//            AppUser testAdmin1 = AppUser.builder().username("testAdmin1").password("12345").firstName("John").lastName("Doe").pesel("12334567890")
//                    .email("test@email.com").telephone("123212431").street("Napoleona 2").city("Warsaw").country("Poland").postalCode("02-222").build();
//            AppUser testPatient1 = AppUser.builder().username("testPatient1").password("12345").firstName("Bob").lastName("Boe").pesel("12334567890")
//                    .email("test@email.com").telephone("123212431").street("Napoleona 2").city("Warsaw").country("Poland").postalCode("02-222").build();
//            AppUser testOptometrist1 = AppUser.builder().username("testOptometrist1").password("12345").firstName("Tom").lastName("Doe").pesel("12334567890")
//                    .email("test@email.com").telephone("123212431").street("Napoleona 2").city("Warsaw").country("Poland").postalCode("02-222").build();
//            AppUser testReceptionist1 = AppUser.builder().username("testReceptionist1").password("12345").firstName("Hanna").lastName("Doe").pesel("12334567890")
//                    .email("test@email.com").telephone("123212431").street("Napoleona 2").city("Warsaw").country("Poland").postalCode("02-222").build();
//            AppUser testUser1 = AppUser.builder().username("testUser1").password("12345").firstName("Anna").lastName("Doe").pesel("12334567890")
//                    .email("test@email.com").telephone("123212431").street("Napoleona 2").city("Warsaw").country("Poland").postalCode("02-222").build();
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
//            userService.addRoleToUser("testAdmin1", "PATIENT");
//            userService.addRoleToUser("testReceptionist1", "PATIENT");
//            userService.addRoleToUser("testOptometrist1", "OPTOMETRIST");
//            userService.addRoleToUser("testAdmin1", "OPTOMETRIST");
//            userService.addRoleToUser("testReceptionist1", "RECEPTIONIST");
//
//            Work work1 = Work.builder().name("Contact lenses").description("contacts fitting").price(150).build();
//            workRepository.save(work1);
//            optometristService.addWork(testOptometrist1.getId(),"Contact lenses");
//            Work work2 = Work.builder().name("Glasses").description("glasses correction").price(150).build();
//            workRepository.save(work2);
//            optometristService.addWork(testOptometrist1.getId(),"Glasses");
//
//
//            AppointmentDto appointment1 = AppointmentDto.builder().optometristId(1L).patientId(1L)
//                    .workName(work1.getName()).date("2022-02-23").slot(9).build();
//            AppointmentDto appointment2 = AppointmentDto.builder().optometristId(1L).patientId(2L)
//                    .workName(work2.getName()).date("2022-02-23").slot(11).build();
//            AppointmentDto appointment3 = AppointmentDto.builder().optometristId(1L).patientId(3L)
//                    .workName(work1.getName()).date("2022-02-24").slot(15).build();
//
//
//            appointmentService.saveAppointment(appointment1);
//            appointmentService.saveAppointment(appointment2);
//            appointmentService.saveAppointment(appointment3);
     };


    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
