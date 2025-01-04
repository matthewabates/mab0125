package com.example.tool_rental.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class DateUtilTest {

    DateUtil dateUtil;

    @BeforeEach
    void setUp() {
        dateUtil = new DateUtil();
    }

    @ParameterizedTest(name = "{index}) {0}; {1}; {2}")
    @CsvSource({
            "2025-01-03, true,  Friday",
            "2025-01-04, false, Saturday",
            "2025-01-05, false, Sunday",
            "2025-01-06, true,  Monday",
    })
    void testIsWeekday(LocalDate date, boolean expectedIsWeekday, String description) {
        assertEquals(expectedIsWeekday, dateUtil.isWeekday(date));
    }

    @ParameterizedTest(name = "{index}) {0}; {1}; {2}")
    @CsvSource({
            "2025-01-03, false, Friday",
            "2025-01-04, true,  Saturday",
            "2025-01-05, true,  Sunday",
            "2025-01-06, false, Monday",
    })
    void testIsWeekend(LocalDate date, boolean expectedIsWeekend, String description) {
        assertEquals(expectedIsWeekend, dateUtil.isWeekend(date));
    }

    @ParameterizedTest(name = "{index}) {0}; {1}; {2}")
    @CsvSource({
        "2025-07-04, true,  Independence Day on a Friday is a holiday",
        "2021-07-04, false, Independence Day on a Sunday is not a holiday",
        "2021-07-03, false, Independence Day on a Sunday so the previous Saturday is not a holiday",
        "2021-07-02, false, Independence Day on a Sunday so the previous Friday is not a holiday",
        "2021-07-05, true,  Independence Day on a Sunday so the next Monday is a holiday",
        "2024-09-01, false, Day Before Labor Day is not a holiday",
        "2024-09-02, true,  Labor Day is a holiday",
        "2024-09-03, false, Day After Labor Day is not a holiday",
        "2025-01-03, false, Random date not a holiday",
    })
    void testIsHoliday(LocalDate date, boolean expectedIsHoliday, String description) {
        assertEquals(expectedIsHoliday, dateUtil.isHoliday(date));
    }

    @ParameterizedTest(name = "{index}) {0} + {1} = {2}; {3}")
    @CsvSource({
            "2025-01-03, 1, 2025-01-04, 1 day - same month",
            "2025-01-03, 2, 2025-01-05, 2 days - same month",
            "2025-01-31, 2, 2025-02-02, 2 days - next month",
            "2025-11-11, 60, 2026-01-10, 60 days - next year",
    })
    void testCalculateDueDate(LocalDate date, int days, LocalDate expectedDueDate, String description) {
        assertEquals(expectedDueDate, dateUtil.calculateDueDate(date, days));
    }

}