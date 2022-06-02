package com.baranskagata.optometry.dao;

import com.baranskagata.optometry.entity.WorkingPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface WorkingPlanRepository extends JpaRepository<WorkingPlan,Long> {

    @Query(value="SELECT w FROM WorkingPlan  w WHERE w.optometrist.id = :optometristId")
    WorkingPlan findByOptometristId(@Param("optometristId") Long optometristId);
}
