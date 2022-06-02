package com.baranskagata.optometry.service;

import com.baranskagata.optometry.dto.AppUserOptometrist;
import com.baranskagata.optometry.entity.Optometrist;
import com.baranskagata.optometry.entity.Work;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OptometristService {
    Page<AppUserOptometrist> getAllOptometrists(Pageable pageable);

    AppUserOptometrist getOptometristByOptometristNumber(String optometristNumber);

    Optometrist updateOptometrist(Long optometristId,Optometrist optometristData);

    List<Work> addWork(Long appUserId,String name);

    void removeWork(Long appUserId, String workName);
}
