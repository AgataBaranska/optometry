package com.baranskagata.optometry.service;

import com.baranskagata.optometry.dao.WorkingPlan;
import com.baranskagata.optometry.datetimeutil.TimePeriod;

public interface WorkingPlanService {
    public WorkingPlan getWorkingPlanByOptometristId(Long optometristId);

    public WorkingPlan updateWorkingPlan(WorkingPlan workingPlanData);

    public WorkingPlan addBreakToWorkingPlan(TimePeriod breakPeriod, Long optometristId, String dayOfWeek);

    public void deleteBreakFromWorkingPlan(TimePeriod breakPeriod, Long optometristId, String dayOfWeek);
}
