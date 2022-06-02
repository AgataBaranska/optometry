package com.baranskagata.optometry.service;


import com.baranskagata.optometry.dao.*;

import com.baranskagata.optometry.entity.*;
import com.baranskagata.optometry.exception.RoleAlreadyExistsException;
import com.baranskagata.optometry.exception.RoleNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final PatientRepository patientRepository;
    private final OptometristRepository optometristRepository;
    private final ReceptionistRepository receptionistRepository;
    private final AdminRepository adminRepository;

    //from UserDetailsService
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found in the database with username: "+ username));
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role ->
            authorities.add(new SimpleGrantedAuthority(role.getName())));
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
    }

    @Override
    public Page<AppUser> getUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public AppUser saveUser(AppUser user) {
        AppUser appUser = userRepository.findByUsername(user.getUsername()).orElseThrow(() -> new UsernameNotFoundException("User not found in the database with username:"+user.getUsername()));
        //encode password
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public AppUser updateUser(Long id, AppUser userData) {
        AppUser appUser = userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("User not found in the database with id:"+id));


        appUser.setFirstName(userData.getFirstName());
        appUser.setLastName(userData.getLastName());
        appUser.setPassword(userData.getPassword());
        appUser.setCity(userData.getCity());
        appUser.setCountry(userData.getCountry());
        appUser.setEmail(userData.getEmail());
        appUser.setPesel(userData.getPesel());
        return null;
    }

    public boolean hasRole(String roleName) {
        UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        for (GrantedAuthority grantedAuthority : user.getAuthorities()) {
            if (grantedAuthority.getAuthority().equals(roleName)) {
                return true;
            }
        }
        return false;
    }


    public Optometrist getOptometristById(Long id) {
        return optometristRepository.getById(id);
    }


    @Override
    public Role saveRole(Role role) {
        Role dbRole = roleRepository.getByName(role.getName()).orElseThrow(()->new RoleAlreadyExistsException("Role already exists eith name:"+ role.getName()));
        log.info("Saving new role to db {}", role);
        return roleRepository.save(role);
    }


    @Override
    public void addRoleToUser(String username, String roleName) {
        log.info("Adding role {} to user {}", roleName, username);
        //TODO validation
        AppUser user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found in the database with username:"+username));
        Role role = roleRepository.findByName(roleName).orElseThrow(() -> new RoleNotFoundException("Role not found in the database with roleName: "+ roleName));;
        user.getRoles().add(role);
        switch (roleName) {
            case "PATIENT": {
                Patient patient = new Patient();

                user.setPatient(patient);
                patient.setAppUser(user);
                patientRepository.save(patient);
            }
            break;
            case "ADMIN": {
                Admin admin = new Admin();
                user.setAdmin(admin);
                admin.setAppUser(user);
                adminRepository.save(admin);


            }
            break;
            case "RECEPTIONIST": {
                Receptionist receptionist = new Receptionist();
                user.setReceptionist(receptionist);
                receptionist.setAppUser(user);
                receptionistRepository.save(receptionist);
            }
            break;
            case "OPTOMETRIST": {
                Optometrist optometrist = new Optometrist();
                user.setOptometrist(optometrist);
                optometrist.setAppUser(user);

                WorkingPlan workingPlan = WorkingPlan.generateDefaultWorkingPlan();
                workingPlan.setOptometrist(optometrist);
                optometrist.setWorkingPlan(workingPlan);
                optometristRepository.save(optometrist);
            }
            break;

        }

    }

    @Override
    public AppUser getUser(String username) {

        return userRepository.findByUsername(username).orElseThrow();
    }


}
