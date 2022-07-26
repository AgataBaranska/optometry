package com.baranskagata.optometry.repository;

import com.baranskagata.optometry.dao.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin,Long> {
}
