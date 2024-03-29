package com.baranskagata.optometry.service;


import com.baranskagata.optometry.dao.*;
import com.baranskagata.optometry.dto.AppUserDto;
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
    public Page<AppUser> getUsersByLastName(String lastName,Pageable pageable){
        return userRepository.findByLastNameContaining(lastName, pageable);
    }


    @Override
    public AppUser saveUser(AppUser user) {
        Optional<AppUser> appUser = userRepository.findByUsername(user.getUsername());
        if (appUser.isPresent()) {
            throw new UsernameAlreadyExistsException("Username already taken: " + user.getUsername());
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Role userRole = roleRepository.findByName("USER").orElseThrow(() -> new RoleNotFoundException("Role user not found"));
      //  Role patientRole = roleRepository.findByName("PATIENT").orElseThrow(() -> new RoleNotFoundException("Patient user not found"));
        user.getRoles().add(userRole);
     //   user.getRoles().add(patientRole);
        return userRepository.save(user);
    }

    @Override
    public AppUserDto updateUser(String username, AppUserDto userData) {

        if (userRepository.findByUsername(userData.getUsername()).isPresent()&& !username.equals(userData.getUsername())) {
            throw new UsernameAlreadyExistsException("Username already taken: " + userData.getUsername());
        }
        AppUser appUser = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found in the database with username:" + username));

        appUser.setUsername(userData.getUsername());
            appUser.setFirstName(userData.getFirstName());
            appUser.setLastName(userData.getLastName());
            appUser.setPesel(userData.getPesel());
            appUser.setTelephone(userData.getTelephone());
            appUser.setEmail(userData.getEmail());
            appUser.setPostalCode(userData.getPostalCode());
            appUser.setStreet(userData.getStreet());
            appUser.setCountry(userData.getCountry());
            appUser.setCity(userData.getCity());
        for (Role role:userData.getRoles()
             ) {
          addRoleToUser(appUser.getUsername(),role.getName());
        }

        return userData;
    }

    @Override
    public void deleteUser(String username ) {
        AppUser user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found in the database with username: " + username));
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
    public List<Role> getAllAppRoles() {
        return roleRepository.findAll();
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

        if (user.getRoles().contains(role)) return;

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
