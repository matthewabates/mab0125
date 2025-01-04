package com.example.tool_rental.service;

import com.example.tool_rental.model.RentalAgreement;
import com.example.tool_rental.model.ToolType;
import com.example.tool_rental.util.CostUtil;
import com.example.tool_rental.util.DateUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CostUtilTest {

    CostUtil costUtil;

    @BeforeEach
    void setUp() {
        DateUtil dateUtil = new DateUtil(); //TODO mock
        costUtil = new CostUtil(dateUtil);
    }

    @ParameterizedTest(name = "{index}) {0}; {1}; {2}; {3}; {4}; {5}")
    @CsvSource({
        "2025-01-03, true,  false, false, true,  weekday with weekdayCharge",
        "2025-01-03, false, false, false, false, weekday without weekdayCharge",
        "2025-01-03, false, true,  true,  false, weekday without weekdayCharge with other charge days",
        "2025-01-04, true,  true,  false, true,  weekend with weekendCharge",
        "2025-01-04, true,  false, false, false, weekend without weekendCharge",
        "2025-01-04, true,  false, true,  false, weekend without weekendCharge with other charge days",
        "2025-07-04, true,  false, true,  true,  holiday with holidayCharge",
        "2025-07-04, true,  false, false, false, holiday without holidayCharge",
        "2025-07-04, true,  true,  false, false, holiday without holidayCharge with other charge days",
    })
    void testIsChargeable(LocalDate date, boolean weekdayCharge, boolean weekendCharge, boolean holidayCharge, boolean expectedIsChargeable, String description) {
        ToolType toolType = new ToolType();
        toolType.setWeekdayCharge(weekdayCharge);
        toolType.setWeekendCharge(weekendCharge);
        toolType.setHolidayCharge(holidayCharge);

        assertEquals(expectedIsChargeable, costUtil.isChargeable(date, toolType));
    }

    @ParameterizedTest(name = "{index}) {0}; {1}; {2}; {3}; {4}; {5}")
    @CsvSource({
        "2025-07-03, 2025-07-09, false, false, false, 0, Wednesday - Tuesday (holiday on Friday) with no days charged",
        "2025-07-03, 2025-07-09, true,  false, false, 3, Wednesday - Tuesday (holiday on Friday) with only weekdays charged",
        "2025-07-03, 2025-07-09, false, true,  false, 2, Wednesday - Tuesday (holiday on Friday) with only weekends charged",
        "2025-07-03, 2025-07-09, true,  true,  false, 5, Wednesday - Tuesday (holiday on Friday) with only weekdays and weekends charged",
        "2025-07-03, 2025-07-09, true,  false, true,  4, Wednesday - Tuesday (holiday on Friday) with only weekdays and holidays charged",
        "2025-07-03, 2025-07-09, true,  true,  true,  6, Wednesday - Tuesday (holiday on Friday) with all days charged"
    })
    void testCalculateChargeDays(LocalDate checkoutDate, LocalDate dueDate, boolean weekdayCharge, boolean weekendCharge, boolean holidayCharge, int expectedChargeDays, String description) {
        RentalAgreement rentalAgreement = new RentalAgreement();
        rentalAgreement.setCheckoutDate(checkoutDate);
        rentalAgreement.setDueDate(dueDate);

        ToolType toolType = new ToolType();
        toolType.setWeekdayCharge(weekdayCharge);
        toolType.setWeekendCharge(weekendCharge);
        toolType.setHolidayCharge(holidayCharge);

        assertEquals(expectedChargeDays, costUtil.calculateChargeDays(rentalAgreement, toolType));
    }

    //TODO calculatePreDiscountCharge
    //TODO calculateDiscountAmount
    //TODO calculateFinalCharge

}