package com.baranskagata.optometry.repository;

import com.baranskagata.optometry.dao.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;


public interface RoleRepository extends JpaRepository<Role,Long> {
    Optional<Role> findByName(String name);

    Optional<Role> getByName(String name);
}
