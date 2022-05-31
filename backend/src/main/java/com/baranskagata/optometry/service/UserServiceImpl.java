package com.baranskagata.optometry.service;


import com.baranskagata.optometry.dao.*;

import com.baranskagata.optometry.entity.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final PatientRepository patientRepository;
    private final OptometristRepository optometristrepository;
    private final ReceptionistRepository receptionistRepository;
    private final AdminRepository adminRepository;

    //from UserDetailsService
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser user = userRepository.findByUsername(username);
        if (user == null) {
            log.error("User not found in the database");
            throw new UsernameNotFoundException("User not found in the database");
        } else {
            log.info("User found in the database: {}", username);
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();

        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        });
        log.info("User {} authorities: {}", username, authorities.toString());
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
    }

    @Override
    public AppUser saveUser(AppUser user) {
        log.info("Saving new user to db {}", user);
        //check if user already exists in db
        //encode password
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }


    @Override
    public Role saveRole(Role role) {
        log.info("Saving new role to db {}", role);
        return roleRepository.save(role);
    }

    @Override
    public void addRoleToUser(String username, String roleName) {
        log.info("Adding role {} to user {}", roleName, username);
        //TODO validation
        AppUser user = userRepository.findByUsername(username);
        Role role = roleRepository.findByName(roleName);
        user.getRoles().add(role);
        switch(roleName){
            case "PATIENT":{
                Patient patient = new Patient();

                user.setPatient(patient);
                patient.setAppUser(user);
                patientRepository.save(patient);
            }
            break;
            case "ADMIN":{
                Admin admin = new Admin();
                user.setAdmin(admin);
                admin.setAppUser(user);
                adminRepository.save(admin);


            }
            break;
            case "RECEPTIONIST":{
                Receptionist receptionist = new Receptionist();
                user.setReceptionist(receptionist);
                receptionist.setAppUser(user);
                receptionistRepository.save(receptionist);
            }
            break;
            case "OPTOMETRIST":{
                Optometrist optometrist = new Optometrist();
                user.setOptometrist(optometrist);
                optometrist.setAppUser(user);
                optometristrepository.save(optometrist);
            }
            break;

        }

    }

    @Override
    public AppUser getUser(String username) {
        log.info("Fetching user", username);
        return userRepository.findByUsername(username);
    }

    @Override
    public Page<AppUser> getUsers(Pageable pageable) {
        log.info("Fetching all users");
        return userRepository.findAll(pageable);
    }


}
