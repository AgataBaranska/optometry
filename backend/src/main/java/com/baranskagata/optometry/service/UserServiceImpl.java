package com.baranskagata.optometry.service;


import com.baranskagata.optometry.dao.*;
import com.baranskagata.optometry.dto.UpdatePasswordDto;
import com.baranskagata.optometry.entity.*;
import com.baranskagata.optometry.exception.RoleNotFoundException;
import com.baranskagata.optometry.exception.UsernameAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j

//implement userDetailsManager to create delete passwords etc.
public class UserServiceImpl implements UserService, UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private OptometristRepository optometristRepository;
    @Autowired
    private ReceptionistRepository receptionistRepository;
    @Autowired
    private AdminRepository adminRepository;

    //from UserDetailsService
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found in the database with username: " + username));
        return new SecurityAppUser(appUser);
    }

    @Override
    public Page<AppUser> getUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public AppUser saveUser(AppUser user) {
        Optional<AppUser> appUser = userRepository.findByUsername(user.getUsername());
        if (appUser.isPresent()) {
            throw new UsernameAlreadyExistsException("Username already taken: " + user.getUsername());

        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Role userRole = roleRepository.findByName("USER").orElseThrow(()->new RoleNotFoundException("Role user not found"));
        user.getRoles().add(userRole);
        return userRepository.save(user);
    }

    @Override
    public AppUser updateUser(Long id, AppUser userData) {
        AppUser appUser = userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("User not found in the database with id:" + id));

        if (userData.getFirstName() != null) {
            appUser.setFirstName(userData.getFirstName());
        }
        if (userData.getLastName() != null) {
            appUser.setLastName(userData.getLastName());
        }
        if (userData.getPassword() != null) {
            appUser.setPassword(passwordEncoder.encode(userData.getPassword()));
        }
        if (userData.getCity() != null) {
            appUser.setCity(userData.getCity());
        }
        if (userData.getCountry() != null) {
            appUser.setCountry(userData.getCountry());
        }
        if (userData.getEmail() != null) {
            appUser.setEmail(userData.getEmail());
        }
        if (userData.getPesel() != null) {
            appUser.setPesel(userData.getPesel());
        }
        return userRepository.save(appUser);
    }

    @Override
    public void deleteUser(Long id) {
        AppUser user = userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("User not found in the database with id: " + id));
        userRepository.delete(user);
    }

    @Override
    public AppUser updatePassword(Long userId, UpdatePasswordDto updatePasswordDto) {
        AppUser user = userRepository.findById(userId).orElseThrow(() -> new UsernameNotFoundException("User not found in the database with id: " + userId));
        if (passwordEncoder.matches(updatePasswordDto.getCurrentPassword(), user.getPassword()) && updatePasswordDto.getNewPassword() != null) {
            user.setPassword(updatePasswordDto.getNewPassword());
            return userRepository.save(user);
        }

        return null;
    }

    @Override
    public List<Role> getUserRoles(String username) {
        AppUser user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found in the database with username:" + username));
        return user.getRoles();
    }


    @Override
    public void addRoleToUser(String username, String roleName) {
        log.info("Adding role {} to user {}", roleName, username);

        AppUser user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found in the database with username:" + username));
        Role role = roleRepository.findByName(roleName).orElseThrow(() -> new RoleNotFoundException("Role not found in the database with roleName: " + roleName));

        if(user.getRoles().contains(role)) return;
        
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
    public void removeRoleFromUser(String username, String roleName) {
        AppUser user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found in the database with username:" + username));
        Role role = roleRepository.findByName(roleName).orElseThrow(() -> new RoleNotFoundException("Role not found in the database with roleName: " + roleName));
        ;
        user.getRoles().remove(role);
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
    public AppUser getUser(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
    }


}
