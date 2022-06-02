package com.baranskagata.optometry.service;

import com.baranskagata.optometry.dao.RoleRepository;
import com.baranskagata.optometry.entity.Role;
import com.baranskagata.optometry.exception.RoleAlreadyExistsException;
import com.baranskagata.optometry.exception.RoleNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public List<Role> getRoles() {
        return roleRepository.findAll();
    }

    @Override
    public Role getRoleByName(String name) {
        return roleRepository.findByName(name).orElseThrow(() -> new RoleNotFoundException("Role not found with name: " + name));
    }

    @Override
    public Role saveRole(Role role) {
        Role dbRole = roleRepository.findByName(role.getName()).orElseThrow(() -> new RoleAlreadyExistsException("Role already exists with name: " + role.getName()));
        roleRepository.save(role);
        return role;
    }

    @Override
    public Role updateRole(Long id, Role roleData) {
        Role dbRole = roleRepository.findById(id).orElseThrow(() -> new RoleNotFoundException("Role not found with id: " + id));
        dbRole.setName(roleData.getName());
        roleRepository.save(dbRole);
        return dbRole;
    }

    @Override
    public void deleteRole(Long id) {
        Role dbRole = roleRepository.findById(id).orElseThrow(() -> new RoleNotFoundException("Role not found with id: " + id));
        roleRepository.deleteById(id);
    }
}
