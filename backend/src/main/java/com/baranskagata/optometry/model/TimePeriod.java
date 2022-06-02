package com.baranskagata.optometry.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class TimePeriod implements Comparable<TimePeriod>{

    private LocalTime start;
    private LocalTime end;


    @Override
    public int compareTo(TimePeriod o) {
        return this.getStart().compareTo(o.getStart());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TimePeriod period = (TimePeriod) o;
        return this.start.equals(period.getStart()) && this.end.equals(period.getEnd());
    }

    @Override
    public int hashCode() {
        return Objects.hash(start, end);
    }


}
