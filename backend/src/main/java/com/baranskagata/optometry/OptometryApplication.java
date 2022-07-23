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

 //           userService.addRoleToUser("johnd","PATIENT");

//            userService.saveRole(new Role(null, "PATIENT"));
//            userService.saveRole(new Role(null, "OPTOMETRIST"));
//            userService.saveRole(new Role(null, "ADMIN"));
//            userService.saveRole(new Role(null, "RECEPTION"));
//
//
//            userService.saveUser(new AppUser(null, "johnd", "1234",
//                    "John", "Doe", "1214234141", "345234233",
//                    "joe2gmail.com", "Dworska", "Warsaw", "03-321", "Poland", new ArrayList<Role>()));
//            userService.saveUser(new AppUser(null, "olab", "1234", "John", "Doe", "1214234141", "345234233", "joe2gmail.com", "Dworska", "Warsaw", "03-321", "Poland", new ArrayList<Role>()));
//            userService.saveUser(new AppUser(null, "doric", "1234", "John", "Doe", "1214234141", "345234233", "joe2gmail.com", "Dworska", "Warsaw", "03-321", "Poland", new ArrayList<Role>()));
//            userService.saveUser(new AppUser(null, "optim", "1234", "John", "Doe", "1214234141", "345234233", "joe2gmail.com", "Dworska", "Warsaw", "03-321", "Poland", new ArrayList<Role>()));
//
//            userService.addRoleToUser("johnd","ADMIN");
//            userService.addRoleToUser("olab","PATIENT");
//            userService.addRoleToUser("doric","RECEPTION");
//            userService.addRoleToUser("optim","OPTOMETRIST");
//
//
//            Work work = new Work(null,"Contact lenses","contacts fitting",150,40,new ArrayList<>());
//            Appointment appointment = new Appointment(null,LocalDateTime.parse("2022-06-12T12:00:00"), LocalDateTime.parse("2022-06-12T12:30:00"), AppointmentStatus.SCHEDULED,work,null,null);
//           workRepository.save(work);
//            appointmentService.saveAppointment(appointment);
      };


  }
  @Bean
  PasswordEncoder passwordEncoder () {
      return new BCryptPasswordEncoder();
  }

    }
