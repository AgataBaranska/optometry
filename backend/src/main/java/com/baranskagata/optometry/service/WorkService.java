package com.baranskagata.optometry.service;

import com.baranskagata.optometry.entity.Optometrist;
import com.baranskagata.optometry.entity.Work;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface WorkService {
    Work getWorkById(Long id);

    List<Work> getWorks();

    Work saveWork(Work work);


    Work updateWork(Long workId, Work updateWorkData);

    void deleteWorkById(Long workId);


    Page<Optometrist> getOptometristForWorkId(Long workId, Pageable pageable);




}
