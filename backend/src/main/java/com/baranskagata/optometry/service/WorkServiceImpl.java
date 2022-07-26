package com.baranskagata.optometry.service;

import com.baranskagata.optometry.repository.WorkRepository;
import com.baranskagata.optometry.dao.Optometrist;
import com.baranskagata.optometry.dao.Work;
import com.baranskagata.optometry.exception.WorkNotFoundException;
import com.baranskagata.optometry.exception.WorkWithNameAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class WorkServiceImpl implements  WorkService{

   private final WorkRepository workRepository;


    @Override
    public List<Work> getWorks() {
        return workRepository.findAll();
    }

    @Override
    public Work getWorkById(Long id) {
        return workRepository.findById(id).orElseThrow(()-> new WorkNotFoundException("Work does not exist with id: "+ id));
    }

    @Override
    public Work saveWork(Work work) {
        Work dbWork = workRepository.findByName(work.getName()).orElseThrow(()->new WorkWithNameAlreadyExistsException("Work name already exists with name: "+ work.getName()));
        return workRepository.save(work);
    }

    @Override
    public Work updateWork(Long workId,Work updateWorkData) {
        Work work = workRepository.findById(workId).orElseThrow(()->new WorkNotFoundException("Work does not exist with id:"+ workId));
        work.setName(updateWorkData.getName());
        work.setDescription(updateWorkData.getDescription());
        work.setPrice(updateWorkData.getPrice());
        work.setDuration(updateWorkData.getDuration());
        workRepository.save(work);
        return work;
    }

    @Override
    public void deleteWorkById(Long workId) {
        Work work = workRepository.findById(workId).orElseThrow(()->new WorkNotFoundException("Work does not exist with id:"+ workId));
        workRepository.delete(work);
    }

    @Override
    public Page<Optometrist> getOptometristForWorkId(Long workId, Pageable pageable) {
        Work work = workRepository.findById(workId).orElseThrow(()->new WorkNotFoundException("Work does not exist with id:"+ workId));
        return workRepository.getOptometristForWorkId(workId, pageable);
    }


}
