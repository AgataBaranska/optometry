package com.baranskagata.optometry.dao;

import com.baranskagata.optometry.entity.Admin;
import com.baranskagata.optometry.entity.Optometrist;
import com.baranskagata.optometry.entity.Work;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WorkRepository extends JpaRepository<Work,Long> {

    @Query(value="SELECT w.optometrists FROM Work w WHERE w.id=:workId")
    Page<Optometrist> getOptometristForWorkId(@Param("workId") Long workId, Pageable pageable);

    @Query(value ="SELECT w FROM Work w JOIN w.optometrists o WHERE o.id = :optometristId")
    Page<Work> getWorkByOptometristId(@Param("optometristId") Long optometristId, Pageable pageable);
}
