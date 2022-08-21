package com.baranskagata.optometry.dateutlis;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Data
@NoArgsConstructor

public class DayPlan {
    private TimePeriod workingHours;
    private List<TimePeriod> breaks;

    public DayPlan(TimePeriod workingHours) {
        this.workingHours = workingHours;
        this.breaks = new ArrayList<>();
    }

    public List<TimePeriod> getTimePeriodsWithoutBreaks() {
        ArrayList<TimePeriod> timePeriodsWithoutBreaks = new ArrayList<>();
        timePeriodsWithoutBreaks.add(getWorkingHours());

        if (!breaks.isEmpty()) {

            ArrayList<TimePeriod> workingPeriodsToAdd = new ArrayList<>();

            for (TimePeriod tempBreak : getBreaks()) {
                if (tempBreak.getStart().isBefore(workingHours.getStart())) {
                    tempBreak.setStart(workingHours.getStart());
                }
                if (tempBreak.getEnd().isAfter(workingHours.getEnd())) {
                    tempBreak.setEnd(workingHours.getEnd());
                }
                for (TimePeriod period : timePeriodsWithoutBreaks) {
                    if (tempBreak.getStart().equals(period.getStart()) && tempBreak.getEnd().isAfter(period.getStart()) && tempBreak.getEnd().isBefore(period.getEnd())) {
                        period.setStart(tempBreak.getEnd());
                    }
                    if (tempBreak.getEnd().equals(period.getEnd()) && tempBreak.getStart().isAfter(period.getStart()) && tempBreak.getStart().isBefore(period.getEnd())) {
                        period.setEnd(tempBreak.getStart());
                    }
                    if (tempBreak.getStart().isAfter(period.getStart()) && tempBreak.getEnd().isBefore(period.getEnd())) {
                        workingPeriodsToAdd.add(new TimePeriod(period.getStart(), tempBreak.getStart()));
                        period.setStart(tempBreak.getEnd());
                    }
                }
                timePeriodsWithoutBreaks.addAll(workingPeriodsToAdd);
                Collections.sort(timePeriodsWithoutBreaks);

            }


        }
        return timePeriodsWithoutBreaks;
    }
}
