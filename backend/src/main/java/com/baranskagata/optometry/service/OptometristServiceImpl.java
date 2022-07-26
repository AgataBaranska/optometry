package com.baranskagata.optometry.service;

import com.baranskagata.optometry.repository.OptometristRepository;
import com.baranskagata.optometry.repository.UserRepository;
import com.baranskagata.optometry.repository.WorkRepository;
import com.baranskagata.optometry.dto.AppUserOptometrist;
import com.baranskagata.optometry.dao.AppUser;
import com.baranskagata.optometry.dao.Optometrist;
import com.baranskagata.optometry.dao.Work;
import com.baranskagata.optometry.exception.OptometristNotFoundException;
import com.baranskagata.optometry.exception.WorkNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    public List<AppUserOptometrist> getAllOptometrists() {
        return optometristRepository.findAllAppUserOptometrist();
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

        //null na optional
        if (optometrist == null) {
            throw new OptometristNotFoundException("App user does not have optometrist role with appUser id: " + appUserId);
        }

        //builder
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
        Optometrist optometrist = optometristRepository.findById(optometristId).orElseThrow(() -> {
            String s = "Optometrist not found with the id:";
            return new OptometristNotFoundException(s + optometristId);
        });
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
