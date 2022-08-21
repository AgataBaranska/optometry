package com.baranskagata.optometry.dao;

import com.baranskagata.optometry.entity.Optometrist;
import com.baranskagata.optometry.entity.Work;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface WorkRepository extends JpaRepository<Work, Long> {

    Optional<Work> findByName(String workName);

    @Query(value = "SELECT w.optometrists FROM Work w WHERE w.id=:workId")
    Page<Optometrist> getOptometristForWorkId(@Param("workId") Long workId, Pageable pageable);


}
