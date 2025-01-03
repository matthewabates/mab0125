package com.example.tool_rental.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class HolidayServiceTest {

    HolidayService holidayService;

    @BeforeEach
    void setUp() {
        holidayService = new HolidayService();
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
    void testIsHoliday(LocalDate date, boolean isHoliday, String description) {
        assertEquals(isHoliday, holidayService.isHoliday(date));
    }
}