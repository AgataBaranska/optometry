package com.baranskagata.optometry.dao;

import com.baranskagata.optometry.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;


public interface RoleRepository extends JpaRepository<Role,Long> {
    Optional<Role> findByName(String name);

    Optional<Role> getByName(String name);
}
