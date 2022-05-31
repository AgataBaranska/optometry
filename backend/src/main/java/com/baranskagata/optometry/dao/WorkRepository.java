package com.baranskagata.optometry.dao;

import com.baranskagata.optometry.entity.Admin;
import com.baranskagata.optometry.entity.Work;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkRepository extends JpaRepository<Work,Long> {
}
