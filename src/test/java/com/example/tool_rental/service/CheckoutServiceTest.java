package com.example.tool_rental.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class CheckoutServiceTest {

    CheckoutService checkoutService;

    @BeforeEach
    void setUp() {
        checkoutService = new CheckoutService();
    }

    @ParameterizedTest(name = "{index}) {0} + {1} = {2}; {3}")
    @CsvSource({
            "2025-01-03, 1, 2025-01-04, 1 day - same month",
            "2025-01-03, 2, 2025-01-05, 2 days - same month",
            "2025-01-31, 2, 2025-02-02, 2 days - next month",
            "2025-11-11, 60, 2026-01-10, 60 days - next year",
    })
    void testIsHoliday(LocalDate date, int days, LocalDate dueDate, String description) {
        assertEquals(dueDate, checkoutService.getDueDate(date, days));
    }
}