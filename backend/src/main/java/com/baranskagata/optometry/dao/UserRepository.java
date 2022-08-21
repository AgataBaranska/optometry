package com.baranskagata.optometry.dao;

import com.baranskagata.optometry.entity.AppUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<AppUser,Long> {

    Optional<AppUser> findByUsername(String username);
    Page<AppUser> findByLastNameContaining(String lastName, Pageable pageable);
}
