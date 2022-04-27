package com.baranskagata.optometry.dao;

import com.baranskagata.optometry.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin("http://localhost:4200")
public interface RoleRepository extends JpaRepository<Role,Long> {
    Role findByName(String name);
}
