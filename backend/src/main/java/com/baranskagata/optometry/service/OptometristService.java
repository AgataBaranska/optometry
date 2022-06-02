package com.baranskagata.optometry.service;

import com.baranskagata.optometry.entity.Optometrist;
import com.baranskagata.optometry.entity.Work;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OptometristService {
    Page<Optometrist> getAllOptometrists(Pageable pageable, String sortBy);
    Optometrist getOptometristById(Long optometristId);

    Optometrist updateOptometrist(Long optometristId,Optometrist optometristData);

    List<Work> addWork(Long optometristId, Long workId);
}
