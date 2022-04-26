package com.baranskagata.optometry.dao;

import com.baranskagata.optometry.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role,Long> {
    Role findByName(String name);
}
