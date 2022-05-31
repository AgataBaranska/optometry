package com.baranskagata.optometry.dao;

import com.baranskagata.optometry.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin,Long> {
}
