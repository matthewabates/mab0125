package com.example.tool_rental.util;

import com.example.tool_rental.model.RentalAgreement;
import com.example.tool_rental.model.ToolType;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

@Service
public class CostUtil {

    DateUtil dateUtil;

    public CostUtil(DateUtil dateUtil) {
        this.dateUtil = dateUtil;
    }

    //Count of chargeable days, from day after checkout through and including due date, excluding “no charge” days as specified by the tool type.
    public int calculateChargeDays(RentalAgreement rentalAgreement, ToolType toolType) {
        LocalDate dayAfterCheckout = rentalAgreement.getCheckoutDate().plusDays(1);
        LocalDate dueDate = rentalAgreement.getDueDate().plusDays(1);
        return dayAfterCheckout.datesUntil(dueDate)
                .filter(date -> isChargeable(date, toolType))
                .toList()
                .size();
    }

    // A date is chargeable if it does not fall on a day where charges are disabled.
    // In other words, a date is free of charge if charges are explicitly disabled for that type of day.
    public boolean isChargeable(LocalDate date, ToolType toolType) {
        return  (toolType.getWeekdayCharge() || !dateUtil.isWeekday(date)) &&
                (toolType.getWeekendCharge() || !dateUtil.isWeekend(date)) &&
                (toolType.getHolidayCharge() || !dateUtil.isHoliday(date));
    }

    //Calculated as charge days X daily charge. Resulting total rounded half up to cents.
    public BigDecimal calculatePreDiscountCharge(RentalAgreement rentalAgreement) {
        BigDecimal chargeDays = BigDecimal.valueOf(rentalAgreement.getChargeDays());
        BigDecimal dailyRentalCharge = rentalAgreement.getDailyRentalCharge();
        return chargeDays.multiply(dailyRentalCharge).setScale(2, RoundingMode.HALF_UP);
    }

    //Calculated from discount % and pre-discount charge. Resulting amount rounded half up to cents.
    public BigDecimal calculateDiscountAmount(RentalAgreement rentalAgreement) {
        BigDecimal discountPercent = BigDecimal.valueOf(rentalAgreement.getDiscountPercent()).divide(BigDecimal.valueOf(100));
        BigDecimal preDiscountCharge = rentalAgreement.getPreDiscountCharge();
        return discountPercent.multiply(preDiscountCharge).setScale(2, RoundingMode.HALF_UP);
    }

    //Calculated as pre-discount charge - discount amount.
    public BigDecimal calculateFinalCharge(RentalAgreement rentalAgreement) {
        BigDecimal preDiscountCharge = rentalAgreement.getPreDiscountCharge();
        BigDecimal discountAmount = rentalAgreement.getDiscountAmount();
        return preDiscountCharge.subtract(discountAmount).setScale(2, RoundingMode.HALF_UP);
    }
}
