package com.baranskagata.optometry.service;

import com.baranskagata.optometry.dao.AppointmentRepository;
import com.baranskagata.optometry.dto.AppointmentPatientOptometrist;
import com.baranskagata.optometry.entity.*;
import com.baranskagata.optometry.model.DayPlan;
import com.baranskagata.optometry.model.TimePeriod;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final UserService userService;
    private final WorkService workService;

    @Override
    public Page<AppointmentPatientOptometrist> loadAppointmentsPatient(Pageable page) {
        return appointmentRepository.loadAppointments(page);
    }

    @Override
    public AppointmentPatientOptometrist getAppointmentById(Long id) {
        return appointmentRepository.getAppointmentById(id);
    }

    @Override
    public Appointment saveAppointment(Appointment appointment) {
        log.info("Saving new appoitment to db {}", appointment);
        //validation

        return appointmentRepository.save(appointment);
    }

    @Override
    public List<TimePeriod> getAvailableTimePeriodsForWork(Long optometristId, Long patientId, Long workId, LocalDate date) {

        Optometrist optometrist = userService.getOptometristById(optometristId);
        WorkingPlan workingPlan = optometrist.getWorkingPlan();
        log.info("workingPlan", workingPlan);
        DayPlan optometristDayPlan = workingPlan.getDayPlan(date.getDayOfWeek().toString().toLowerCase());
        List<Appointment> optometristAppointmentsInDay = getOptometristAppointmentsAtDay(optometristId, date);
        List<Appointment> patientAppointmentsInDay = getPatientAppointmentsAtDay(patientId, date);

        List<TimePeriod> availableTimePeriods = new ArrayList<>();
        availableTimePeriods = optometristDayPlan.getTimePeriodsWithoutBreaks();
        availableTimePeriods = excludeAppointmentsFromTimePeriods(availableTimePeriods, optometristAppointmentsInDay);
        availableTimePeriods = excludeAppointmentsFromTimePeriods(availableTimePeriods, patientAppointmentsInDay);
        availableTimePeriods = getPeriodsWork(availableTimePeriods, workService.getWorkById(workId));

        return availableTimePeriods;
    }

    private List<TimePeriod> getPeriodsWork(List<TimePeriod> availableTimePeriods, Work work) {
        ArrayList<TimePeriod> availableWorkPeriods = new ArrayList<>();
        for (TimePeriod period : availableTimePeriods) {
            TimePeriod workPeriod = new TimePeriod(period.getStart(), period.getStart().plusMinutes(work.getDuration()));
            while (workPeriod.getEnd().isBefore(period.getEnd()) || workPeriod.getEnd().equals(period.getEnd())) {
                availableWorkPeriods.add(new TimePeriod(period.getStart(), period.getStart().plusMinutes(work.getDuration())));

                workPeriod.setStart(workPeriod.getStart().plusMinutes(work.getDuration()));
                workPeriod.setEnd(workPeriod.getEnd().plusMinutes(work.getDuration()));
            }
        }
        return availableWorkPeriods;
    }


    public List<Appointment> getOptometristAppointmentsAtDay(Long optometristId, LocalDate day) {
        return appointmentRepository.findByOptometristIdInPeriod(optometristId, day.atStartOfDay(), day.atStartOfDay().plusDays(1));
    }

    public List<Appointment> getPatientAppointmentsAtDay(Long patientId, LocalDate day) {
        return appointmentRepository.findByPatientIdInPeriod(patientId, day.atStartOfDay(), day.atStartOfDay().plusDays(1));
    }

    @Override
    public List<TimePeriod> excludeAppointmentsFromTimePeriods(List<TimePeriod> periods, List<Appointment> appointments) {
        List<TimePeriod> timePeriodsToAdd = new ArrayList<TimePeriod>();

        for (Appointment appointment : appointments) {
            for (TimePeriod period : periods) {
                if ((appointment.getStart().toLocalTime().isBefore(period.getStart()) || (appointment.getStart().toLocalTime().equals(period.getStart())) && appointment.getEnd().toLocalTime().isBefore(period.getEnd())));
                {
                    period.setStart(appointment.getEnd().toLocalTime());
                }
                if ((appointment.getEnd().toLocalTime().isAfter(period.getEnd()) || appointment.getEnd().toLocalTime().equals(period.getEnd())) && appointment.getStart().toLocalTime().isAfter(period.getStart())) {
                    period.setEnd(appointment.getStart().toLocalTime());
                }
                if (appointment.getStart().toLocalTime().isAfter(period.getStart()) && appointment.getEnd().toLocalTime().isBefore(period.getEnd())) {
                    timePeriodsToAdd.add(new TimePeriod(period.getStart(), appointment.getStart().toLocalTime()));
                    period.setStart(appointment.getEnd().toLocalTime());
                }
            }
        }
        periods.addAll(timePeriodsToAdd);
        Collections.sort(periods);
        return periods;
    }
}
