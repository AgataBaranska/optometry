package com.baranskagata.optometry.service;

import com.baranskagata.optometry.dto.AppUserOptometrist;
import com.baranskagata.optometry.entity.Optometrist;
import com.baranskagata.optometry.entity.Work;

import java.util.List;

public interface OptometristService {
    List<AppUserOptometrist> getAllOptometrists();

    Optometrist  getById(Long optometristId);

    AppUserOptometrist getOptometristByOptometristNumber(String optometristNumber);

    AppUserOptometrist updateAppUserOptometrist(Long appUserId, AppUserOptometrist userOptometristData);

    Optometrist updateOptometrist(Long optometristId, Optometrist optometristData);

    List<Work> addWork(Long appUserId, String name);

    void removeWork(Long appUserId, String workName);

    List<Work> getAllWorks(Long appUserId);

}
