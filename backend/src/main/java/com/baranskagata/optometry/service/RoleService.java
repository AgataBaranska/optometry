package com.baranskagata.optometry.service;

import com.baranskagata.optometry.entity.Role;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface RoleService {


    List<Role> getRoles();

    Role getRoleByName(String name);

    Role saveRole(Role role);

    Role updateRole(Long id, Role roleData);

    void deleteRole(Long id);
}
