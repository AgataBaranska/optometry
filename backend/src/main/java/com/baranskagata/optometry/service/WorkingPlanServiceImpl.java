package com.baranskagata.optometry.service;

import com.baranskagata.optometry.dao.WorkingPlanRepository;
import com.baranskagata.optometry.entity.Optometrist;
import com.baranskagata.optometry.entity.WorkingPlan;
import com.baranskagata.optometry.exception.WorkNotFoundException;
import com.baranskagata.optometry.exception.WorkingPlanNotFoundException;
import com.baranskagata.optometry.model.TimePeriod;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class WorkingPlanServiceImpl implements WorkingPlanService {

    private final WorkingPlanRepository workingPlanRepository;
    private final OptometristService optometristService;

    @Override
    public WorkingPlan getWorkingPlanByOptometristId(Long optometristId) {
        return workingPlanRepository.findByOptometristId(optometristId);
    }

    @Override
    public WorkingPlan updateWorkingPlan(WorkingPlan workingPlanData) {
        WorkingPlan workingPlan = workingPlanRepository.findById(workingPlanData.getId()).orElseThrow(() -> new WorkNotFoundException("Working plan not found for a id:" + workingPlanData.getId()));
        workingPlan.setMonday(workingPlanData.getMonday());
        workingPlan.setThursday(workingPlanData.getThursday());
        workingPlan.setWednesday(workingPlanData.getWednesday());
        workingPlan.setTuesday(workingPlanData.getTuesday());
        workingPlan.setFriday(workingPlanData.getFriday());
        workingPlan.setSaturday(workingPlanData.getSaturday());
        workingPlan.setSunday(workingPlanData.getSunday());
        workingPlanRepository.save(workingPlan);

        return workingPlan;
    }

    @Override
    public WorkingPlan addBreakToWorkingPlan(TimePeriod breakPeriod, Long optometristId, String dayOfWeek) {
        Optometrist optometrist = optometristService.getOptometristById(optometristId);
        WorkingPlan workingPlan = workingPlanRepository.findByOptometristId(optometrist.getId());
        workingPlan.getDayPlan(dayOfWeek).getBreaks().add(breakPeriod);
        workingPlanRepository.save(workingPlan);
        return workingPlan;
    }

    @Override
    public void deleteBreakFromWorkingPlan(TimePeriod breakPeriod, Long optometristId, String dayOfWeek) {
        Optometrist optometrist = optometristService.getOptometristById(optometristId);
        WorkingPlan workingPlan = workingPlanRepository.findByOptometristId(optometrist.getId());
        workingPlan.getDayPlan(dayOfWeek).getBreaks().remove(breakPeriod);
        workingPlanRepository.save(workingPlan);
    }

}
