package com.example.tool_rental.service;

import com.example.tool_rental.model.RentalAgreement;
import com.example.tool_rental.model.ToolType;
import com.example.tool_rental.util.CostUtil;
import com.example.tool_rental.util.DateUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CostUtilTest {

    CostUtil costUtil;

    @BeforeEach
    void setUp() {
        DateUtil dateUtil = new DateUtil();
        costUtil = new CostUtil(dateUtil);
    }

    @ParameterizedTest
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

        boolean actualIsChargeable = costUtil.isChargeable(date, toolType);

        assertEquals(expectedIsChargeable, actualIsChargeable);
    }

    @ParameterizedTest
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

        int actualChargeDays = costUtil.calculateChargeDays(rentalAgreement, toolType);

        assertEquals(expectedChargeDays, actualChargeDays);
    }

    @ParameterizedTest
    @CsvSource({
            "5, 19.99, 99.95",
            "10, 10.00, 100.00",
            "3, 15.75, 47.25",
            "0, 19.99, 0.00",
            "7, 0.00, 0.00"
    })
    void testCalculatePreDiscountCharge(int chargeDays, BigDecimal dailyRentalCharge, BigDecimal expectedCharge) {
        RentalAgreement rentalAgreement = new RentalAgreement();
        rentalAgreement.setChargeDays(chargeDays);
        rentalAgreement.setDailyRentalCharge(dailyRentalCharge);

        BigDecimal actualCharge = costUtil.calculatePreDiscountCharge(rentalAgreement);

        assertEquals(expectedCharge, actualCharge);
    }

    @ParameterizedTest
    @CsvSource({
            "100.00, 10, 10.00",
            "200.00, 15, 30.00",
            "50.00, 20, 10.00",
            "0.00, 25, 0.00",
            "100.00, 0, 0.00",
            "123.45, 15, 18.52",
            "78.99, 7, 5.53",
            "999.99, 12, 120.00",
            "45.67, 8, 3.65",
            "89.76, 25, 22.44"
    })
    void testCalculateDiscountAmount(BigDecimal preDiscountCharge, int discountPercent, BigDecimal expectedDiscountAmount) {
        RentalAgreement rentalAgreement = new RentalAgreement();
        rentalAgreement.setPreDiscountCharge(preDiscountCharge);
        rentalAgreement.setDiscountPercent(discountPercent);

        BigDecimal actualDiscountAmount = costUtil.calculateDiscountAmount(rentalAgreement);

        assertEquals(expectedDiscountAmount, actualDiscountAmount);
    }

    @ParameterizedTest
    @CsvSource({
            "100.00, 10.00, 90.00",
            "200.00, 30.00, 170.00",
            "50.00, 10.00, 40.00",
            "0.00, 0.00, 0.00",
            "100.00, 0.00, 100.00",
            "123.45, 18.52, 104.93",
            "78.99, 5.53, 73.46",
            "999.99, 120.00, 879.99",
            "45.67, 3.65, 42.02",
            "89.76, 22.44, 67.32"
    })
    void testCalculateFinalCharge(BigDecimal preDiscountCharge, BigDecimal discountAmount, BigDecimal expectedFinalCharge) {
        RentalAgreement rentalAgreement = new RentalAgreement();
        rentalAgreement.setPreDiscountCharge(preDiscountCharge);
        rentalAgreement.setDiscountAmount(discountAmount);

        BigDecimal actualFinalCharge = costUtil.calculateFinalCharge(rentalAgreement);

        assertEquals(expectedFinalCharge, actualFinalCharge);
    }

}