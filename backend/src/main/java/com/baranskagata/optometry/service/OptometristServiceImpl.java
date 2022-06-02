package com.baranskagata.optometry.service;

import com.baranskagata.optometry.dao.OptometristRepository;
import com.baranskagata.optometry.dao.WorkRepository;
import com.baranskagata.optometry.entity.Optometrist;
import com.baranskagata.optometry.entity.Work;
import com.baranskagata.optometry.exception.OptometristNotFoundException;
import com.baranskagata.optometry.exception.WorkNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class OptometristServiceImpl implements OptometristService{
    private final OptometristRepository optometristRepository;
    private final WorkRepository workRepository;
    @Override
    public Optometrist getOptometristById(Long optometristId) {
        return optometristRepository.findById(optometristId).orElseThrow(()->new OptometristNotFoundException("Optometrist not found with id:" + optometristId));
    }

    @Override
    public Page<Optometrist> getAllOptometrists(Pageable pageable, String sortBy) {
        return optometristRepository.findAll(pageable, Sort.by(Sort.Direction.ASC,sortBy));
    }

    @Override
    public Optometrist updateOptometrist(Long optometristId, Optometrist optometristData) {
        Optometrist optometrist = optometristRepository.findById(optometristId).orElseThrow(()->new OptometristNotFoundException("Optometrist not found with the id:"+ optometristId));
        optometrist.setOptometristNumber(optometristData.getOptometristNumber());
        optometristRepository.save(optometrist);
        return optometrist;
    }

    @Override
    public List<Work> addWork(Long optometristId, Long workId) {
        Optometrist optometrist = optometristRepository.findById(optometristId).orElseThrow(()->new OptometristNotFoundException("Optometrist not found with the id:"+ optometristId));
       Work work = workRepository.findById(workId).orElseThrow(()-> new WorkNotFoundException("Work not found with id:"+workId));
       optometrist.getWorks().add(work);
       optometristRepository.save(optometrist);
       workRepository.save(work);
        return optometrist.getWorks();
    }
}
