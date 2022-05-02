package com.baranskagata.optometry.service;

import com.baranskagata.optometry.dao.AddressRepository;
import com.baranskagata.optometry.dao.RoleRepository;
import com.baranskagata.optometry.dao.UserRepository;
import com.baranskagata.optometry.dto.Registration;
import com.baranskagata.optometry.entity.Address;
import com.baranskagata.optometry.entity.AppUser;
import com.baranskagata.optometry.entity.Role;
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
@RequiredArgsConstructor//for injections
@Transactional
@Slf4j
public class UserServiceImpl implements  UserService, UserDetailsService {
    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;


    //from UserDetailsServiceco
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser user = userRepository.findByUsername(username);
        if(user==null){
            log.error("User not found in the database");
            throw new UsernameNotFoundException("User not found in the database");
        }else{
            log.info("User found in the database: {}",username);
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();

        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        });
        log.info("User {} authorities: {}", username,authorities.toString());
        return new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPassword(),authorities);
    }
    @Override
    public AppUser saveUser(AppUser user) {
        log.info("Saving new user to db {}",user);
        //check if user already exists in db
        //encode password
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public AppUser saveRegistration(Registration registration) {
        log.info("Saving new registration to db {}",registration);
        //TODO check if already exists in db, exception if not found


       //create new user based on registration
        AppUser user = registration.getUser();
        Address address = registration.getAddress();
        //set users address
        user.setAddress(address);
        //encode password
        registration.getUser().setPassword(passwordEncoder.encode(registration.getUser().getPassword()));
        return userRepository.save(user);

    }

    @Override
    public Role saveRole(Role role) {
        log.info("Saving new role to db {}",role);
        return roleRepository.save(role);
    }

    @Override
    public void addRoleToUser(String username, String roleName) {
        log.info("Adding role {} to user {}",roleName,username);
        //TODO validation
        AppUser user = userRepository.findByUsername(username);
        Role role = roleRepository.findByName(roleName);
        user.getRoles().add(role);
    }

    @Override
    public AppUser getUser(String username) {
        log.info("Fetching user",username);
        return userRepository.findByUsername(username);
    }

    @Override
    public Page<AppUser> getUsers( Pageable pageable) {
        log.info("Fetching all users");
        return userRepository.findAll(pageable);
    }


}
