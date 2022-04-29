package com.baranskagata.optometry.service;

import com.baranskagata.optometry.dto.Registration;
import com.baranskagata.optometry.entity.AppUser;
import com.baranskagata.optometry.entity.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


public interface UserService {
    AppUser saveUser(AppUser user);

    AppUser saveRegistration(Registration registration);

    Role saveRole(Role role);

    void addRoleToUser(String username, String roleName);

    AppUser getUser(String username);

    Page<AppUser> getUsers(Pageable pageable);
}
