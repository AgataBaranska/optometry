package com.baranskagata.optometry.dao;

import com.baranskagata.optometry.entity.Disease;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DiseaseRepository extends JpaRepository<Disease,Long> {

    List<Disease> findByRelatedOrgan(String relatedOrgan);
}
