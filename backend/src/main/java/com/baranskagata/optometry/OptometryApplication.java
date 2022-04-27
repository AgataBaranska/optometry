package com.baranskagata.optometry;

import com.baranskagata.optometry.entity.AppUser;
import com.baranskagata.optometry.entity.Role;
import com.baranskagata.optometry.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;

@SpringBootApplication
public class OptometryApplication {

    public static void main(String[] args) {
        SpringApplication.run(OptometryApplication.class, args);
    }

    @Bean
    CommandLineRunner run(UserService userService) {
        return args -> {
            userService.saveRole(new Role(null, "ROLE_PATIENT"));
            userService.saveRole(new Role(null, "ROLE_OPTOMETRIST"));
            userService.saveRole(new Role(null, "ROLE_ADMIN"));
            userService.saveRole(new Role(null, "ROLE_RECEPTION"));

            userService.saveUser((new AppUser(null, "john doe", "johnd", "1234", new ArrayList<>())));
            userService.saveUser((new AppUser(null, "ola bąk", "olab", "1234", new ArrayList<>())));
            userService.saveUser((new AppUser(null, "olek dąb", "olekd", "1234", new ArrayList<>())));
            userService.saveUser((new AppUser(null, "dori cyk", "doric", "1234", new ArrayList<>())));

            userService.addRoleToUser("johnd", "ROLE_ADMIN");
            userService.addRoleToUser("olab", "ROLE_OPTOMETRIST");
            userService.addRoleToUser("olekd", "ROLE_RECEPTION");
            userService.addRoleToUser("doric", "ROLE_PATIENT");
            userService.addRoleToUser("doric", "ROLE_RECEPTION");


        };

    }
    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


}
