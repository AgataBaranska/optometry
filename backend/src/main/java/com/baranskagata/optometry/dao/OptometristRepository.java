package com.baranskagata.optometry.dao;

import com.baranskagata.optometry.entity.Optometrist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OptometristRepository extends JpaRepository<Optometrist,Integer> {
}
