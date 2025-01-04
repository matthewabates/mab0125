package com.example.tool_rental.util;

import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.TemporalAdjusters;

@Service
public class DateUtil {

    public boolean isWeekday(LocalDate date) {
        return !isWeekend(date);
    }

    public boolean isWeekend(LocalDate date) {
        DayOfWeek day = date.getDayOfWeek();
        return day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY;
    }

    public boolean isHoliday(LocalDate date) {
        return (isIndependenceDay(date) || isLaborDay(date));
    }

    //July 4th - If falls on weekend, it is observed on the closest weekday (if Sat, then Friday before, if Sunday, then Monday after)
    private boolean isIndependenceDay(LocalDate date) {
        LocalDate independenceDay = LocalDate.of(date.getYear(), Month.JULY, 4);

        if (independenceDay.getDayOfWeek() == DayOfWeek.SATURDAY) {
            independenceDay = independenceDay.minusDays(1);
        } else if (independenceDay.getDayOfWeek() == DayOfWeek.SUNDAY) {
            independenceDay = independenceDay.plusDays(1);
        }

        return date.equals(independenceDay);
    }

    // Labor Day is the first Monday in September
    private boolean isLaborDay(LocalDate date) {
        LocalDate laborDay = LocalDate.of(date.getYear(), Month.SEPTEMBER, 1)
                .with(TemporalAdjusters.firstInMonth(DayOfWeek.MONDAY));

        return date.equals(laborDay);
    }

    public LocalDate calculateDueDate(LocalDate date, int days) {
        return date.plusDays(days);
    }
}
