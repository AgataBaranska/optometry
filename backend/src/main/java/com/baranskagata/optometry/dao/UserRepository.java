package com.baranskagata.optometry.dao;

import com.baranskagata.optometry.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.CrossOrigin;

public interface UserRepository extends JpaRepository<AppUser,Long> {

    AppUser findByUsername(String username);

}
