package com.baranskagata.optometry.service;

import com.baranskagata.optometry.dao.OptometristRepository;
import com.baranskagata.optometry.dao.UserRepository;
import com.baranskagata.optometry.dao.WorkRepository;
import com.baranskagata.optometry.dto.AppUserOptometrist;
import com.baranskagata.optometry.dto.AppointmentPatientOptometrist;
import com.baranskagata.optometry.entity.AppUser;
import com.baranskagata.optometry.entity.Optometrist;
import com.baranskagata.optometry.entity.Work;
import com.baranskagata.optometry.exception.OptometristNotFoundException;
import com.baranskagata.optometry.exception.WorkNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class OptometristServiceImpl implements OptometristService {
    private final OptometristRepository optometristRepository;
    private final WorkRepository workRepository;
    private final UserRepository userRepository;

    @Override
    public Page<AppUserOptometrist> getAllOptometrists(Pageable pageable) {
        return optometristRepository.findAllAppUserOptometrist(pageable);
    }

    @Override
    public Optometrist getById(Long optometristId) {
        return optometristRepository.findById(optometristId).orElseThrow(()->new OptometristNotFoundException("Optometrist not found with id: "+ optometristId));
    }

    @Override
    public AppUserOptometrist getOptometristByOptometristNumber(String optometristNumber) {
        return optometristRepository.findByOptometristNumber(optometristNumber).orElseThrow(() -> new OptometristNotFoundException("Optometrist not found with optometristNumber: " + optometristNumber));
    }

    public AppUserOptometrist updateAppUserOptometrist(Long appUserId,AppUserOptometrist userOptometristData) {
        AppUser appUser = userRepository.getById(appUserId);
        Optometrist optometrist = appUser.getOptometrist();
        if (optometrist == null) {
            throw new OptometristNotFoundException("App user does not have optometrist role with appUser id: " + appUserId);
        }
        appUser.setFirstName(userOptometristData.getFirstName());
        appUser.setLastName(userOptometristData.getLastName());
        appUser.setPesel(userOptometristData.getPesel());
        appUser.setEmail(userOptometristData.getEmail());
        appUser.setCity(userOptometristData.getCity());
        appUser.setCountry(userOptometristData.getCountry());
        appUser.setPostalCode(userOptometristData.getPostalCode());
        optometrist.setOptometristNumber(userOptometristData.getOptometristNumber());
        userRepository.save(appUser);
        return userOptometristData;

    }

    @Override
    public Optometrist updateOptometrist(Long optometristId, Optometrist optometristData) {
        Optometrist optometrist = optometristRepository.findById(optometristId).orElseThrow(() -> new OptometristNotFoundException("Optometrist not found with the id:" + optometristId));
        optometrist.setOptometristNumber(optometristData.getOptometristNumber());
        optometristRepository.save(optometrist);
        return optometrist;
    }

    @Override
    public List<Work> getAllWorks(Long appUserId) {
        AppUser appUser = userRepository.findById(appUserId).orElseThrow(() -> new UsernameNotFoundException("Optometrist not found with the appUserId:" + appUserId));
        Optometrist optometrist = appUser.getOptometrist();
        if (optometrist == null) {
            throw new OptometristNotFoundException("App user does not have optometrist role with appUser id: " + appUserId);
        }
        return optometrist.getWorks();
    }

    @Override
    public List<Work> addWork(Long appUserId, String workName) {
        AppUser appUser = userRepository.findById(appUserId).orElseThrow(() -> new UsernameNotFoundException("Optometrist not found with the appUserId:" + appUserId));
        Work work = workRepository.findByName(workName).orElseThrow(() -> new WorkNotFoundException("Work not found with name: " + workName));
        Optometrist optometrist = appUser.getOptometrist();
        if (optometrist == null) {
            throw new OptometristNotFoundException("App user does not have optometrist role with appUser id: " + appUserId);
        }
        optometrist.getWorks().add(work);
        userRepository.save(appUser);
        optometristRepository.save(optometrist);
        workRepository.save(work);
        return optometrist.getWorks();
    }

    @Override
    public void removeWork(Long appUserId, String workName) {
        AppUser appUser = userRepository.findById(appUserId).orElseThrow(() -> new UsernameNotFoundException("Optometrist not found with the appUserId:" + appUserId));
        Work work = workRepository.findByName(workName).orElseThrow(() -> new WorkNotFoundException("Work not found with name: " + workName));
        Optometrist optometrist = appUser.getOptometrist();
        if (optometrist == null) {
            throw new OptometristNotFoundException("App user does not have optometrist role with appUser id: " + appUserId);
        }
        optometrist.getWorks().remove(work);
        optometristRepository.save(optometrist);
        workRepository.save(work);
    }


}
