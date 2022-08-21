package com.baranskagata.optometry;

import com.baranskagata.optometry.dao.AppointmentReasonsRepository;
import com.baranskagata.optometry.dao.DiseaseRepository;
import com.baranskagata.optometry.dao.RoleRepository;
import com.baranskagata.optometry.dao.WorkRepository;
import com.baranskagata.optometry.dto.AppointmentDto;
import com.baranskagata.optometry.entity.*;
import com.baranskagata.optometry.service.AppointmentService;
import com.baranskagata.optometry.service.OptometristService;
import com.baranskagata.optometry.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SpringBootApplication
public class OptometryApplication {

    public static void main(String[] args) {
        SpringApplication.run(OptometryApplication.class, args);
    }

    @Bean
    CommandLineRunner run(UserService userService, AppointmentService appointmentService, OptometristService optometristService, RoleRepository roleRepository, WorkRepository workRepository, DiseaseRepository diseaseRepository, AppointmentReasonsRepository appointmentReasonsRepository) {
        return args -> {

            Set<Role> appRoles = new HashSet<>();
            appRoles.add(new Role(null, "USER"));
            appRoles.add(new Role(null, "ADMIN"));
            appRoles.add(new Role(null, "OPTOMETRIST"));
            appRoles.add(new Role(null, "PATIENT"));
            appRoles.add(new Role(null, "RECEPTIONIST"));
            roleRepository.saveAll(appRoles);

            AppUser testAdmin1 = AppUser.builder().username("testAdmin1").password("12345").firstName("John").lastName("Doe").pesel("93050645678")
                    .email("test@email.com").telephone("123212431").street("Napoleona 2").city("Warsaw").country("Poland").postalCode("02-222").build();
            AppUser testPatient1 = AppUser.builder().username("testPatient1").password("12345").firstName("Bob").lastName("Boe").pesel("93050645678")
                    .email("test@email.com").telephone("123212431").street("Napoleona 2").city("Warsaw").country("Poland").postalCode("02-222").build();
            AppUser testOptometrist1 = AppUser.builder().username("testOptometrist1").password("12345").firstName("Tom").lastName("Doe").pesel("93050645678")
                    .email("test@email.com").telephone("123212431").street("Napoleona 2").city("Warsaw").country("Poland").postalCode("02-222").build();
            AppUser testReceptionist1 = AppUser.builder().username("testReceptionist1").password("12345").firstName("Hanna").lastName("Doe").pesel("93050645678")
                    .email("test@email.com").telephone("123212431").street("Napoleona 2").city("Warsaw").country("Poland").postalCode("02-222").build();
            AppUser testUser1 = AppUser.builder().username("testUser1").password("12345").firstName("Anna").lastName("Doe").pesel("93050645678")
                    .email("test@email.com").telephone("123212431").street("Napoleona 2").city("Warsaw").country("Poland").postalCode("02-222").build();


            userService.saveUser(testAdmin1);
            userService.saveUser(testPatient1);
            userService.saveUser(testOptometrist1);
            userService.saveUser(testReceptionist1);
            userService.saveUser(testUser1);

            userService.addRoleToUser("testAdmin1", "ADMIN");
            userService.addRoleToUser("testPatient1", "PATIENT");
            userService.addRoleToUser("testAdmin1", "PATIENT");
            userService.addRoleToUser("testReceptionist1", "PATIENT");
            userService.addRoleToUser("testOptometrist1", "OPTOMETRIST");
            userService.addRoleToUser("testAdmin1", "OPTOMETRIST");
            userService.addRoleToUser("testReceptionist1", "RECEPTIONIST");

            Work work1 = Work.builder().name("Contact lenses").description("contacts fitting").price(150).build();
            workRepository.save(work1);
            optometristService.addWork(testOptometrist1.getId(),"Contact lenses");
            Work work2 = Work.builder().name("Glasses").description("glasses correction").price(150).build();
            workRepository.save(work2);
            optometristService.addWork(testOptometrist1.getId(),"Glasses");


            AppointmentDto appointment1 = AppointmentDto.builder().optometristId(1L).patientId(1L)
                    .workName(work1.getName()).date("2022-02-23").slot(9).build();
            AppointmentDto appointment2 = AppointmentDto.builder().optometristId(1L).patientId(2L)
                    .workName(work2.getName()).date("2022-02-23").slot(11).build();
            AppointmentDto appointment3 = AppointmentDto.builder().optometristId(1L).patientId(3L)
                    .workName(work1.getName()).date("2022-02-24").slot(15).build();


            appointmentService.saveAppointment(appointment1);
            appointmentService.saveAppointment(appointment2);
            appointmentService.saveAppointment(appointment3);


            List<Disease> eyeDiseases = new ArrayList<>();
            Disease glaucoma = Disease.builder().name("Glaucoma").relatedOrgan("eye").build();
            Disease cataract = Disease.builder().name("Cataract").relatedOrgan("eye").build();
            Disease keratoconus = Disease.builder().name("Keratoconus").relatedOrgan("eye").build();
            Disease squint = Disease.builder().name("Squint").relatedOrgan("eye").build();
            eyeDiseases.add(glaucoma);
            eyeDiseases.add(cataract);
            eyeDiseases.add(keratoconus);
            eyeDiseases.add(squint);
            diseaseRepository.saveAll(eyeDiseases);

            List<Disease> generalDiseases = new ArrayList<>();
            Disease hypertension = Disease.builder().name("Hypertension").relatedOrgan("general").build();
            Disease diabetes = Disease.builder().name("Diabetes").relatedOrgan("general").build();
            Disease atherosclerosis = Disease.builder().name("Atherosclerosis").relatedOrgan("general").build();
            Disease skinDiseases = Disease.builder().name("SkinDiseases").relatedOrgan("general").build();
            generalDiseases.add(hypertension);
            generalDiseases.add(diabetes);
            generalDiseases.add(atherosclerosis);
            generalDiseases.add(skinDiseases);
            diseaseRepository.saveAll(generalDiseases);

            List<Disease> visionConditions = new ArrayList<>();
            Disease myopia = Disease.builder().name("Myopia").relatedOrgan("visionCondition").build();
            Disease hyperopia = Disease.builder().name("Hyperopia").relatedOrgan("visionCondition").build();
            Disease astigmatism = Disease.builder().name("Astigmatism").relatedOrgan("visionCondition").build();
            visionConditions.add(myopia);
            visionConditions.add(hyperopia);
            visionConditions.add(astigmatism);
            diseaseRepository.saveAll(visionConditions);

            List<AppointmentReasons> appointmentReasons = new ArrayList<>();
            AppointmentReasons appointmentReasons1 = AppointmentReasons.builder().name("Head pain").build();
            AppointmentReasons appointmentReasons2 = AppointmentReasons.builder().name("Eye strain").build();
            AppointmentReasons appointmentReasons3 = AppointmentReasons.builder().name("Watery eyes").build();
            AppointmentReasons appointmentReasons4 = AppointmentReasons.builder().name("Worse distance vision").build();
            AppointmentReasons appointmentReasons5 = AppointmentReasons.builder().name("Worse near vision").build();
            AppointmentReasons appointmentReasons6 = AppointmentReasons.builder().name("Control visit").build();
            AppointmentReasons appointmentReasons7 = AppointmentReasons.builder().name("Coontact lenses fitting").build();
            appointmentReasons.add(appointmentReasons1);
            appointmentReasons.add(appointmentReasons2);
            appointmentReasons.add(appointmentReasons3);
            appointmentReasons.add(appointmentReasons4);
            appointmentReasons.add(appointmentReasons5);
            appointmentReasons.add(appointmentReasons6);
            appointmentReasons.add(appointmentReasons7);
            appointmentReasonsRepository.saveAll(appointmentReasons);



     };


    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
