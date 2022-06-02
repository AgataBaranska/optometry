package com.baranskagata.optometry.service;

import com.baranskagata.optometry.entity.Optometrist;
import com.baranskagata.optometry.entity.Work;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface WorkService {
    Work getWorkById(Long id);

    Page<Work> getWorks(Pageable pageable);

    List<Optometrist> getOptometristForWorkId(Long workId);

    Work updateWork(Long workId,Work updateWorkData);

    void deleteWorkById(Long workId);

    List<Work> getWorkByOptometristId(Long optometristId);

    Work saveWork(Work work);
}
