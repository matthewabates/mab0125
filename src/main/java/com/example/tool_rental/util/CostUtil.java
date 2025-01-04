package com.example.tool_rental.util;

import com.example.tool_rental.model.RentalAgreement;
import com.example.tool_rental.model.ToolType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class CostUtil {

    DateUtil dateUtil;

    @Autowired
    public CostUtil(DateUtil dateUtil) {
        this.dateUtil = dateUtil;
    }

    //Count of chargeable days, from day after checkout through and including due date, excluding “no charge” days as specified by the tool type.
    public int calculateChargeDays(RentalAgreement rentalAgreement, ToolType toolType) {
        LocalDate first = rentalAgreement.getCheckoutDate().plusDays(1);
        LocalDate last = rentalAgreement.getDueDate().plusDays(1);
        return first.datesUntil(last)
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
    public float calculatePreDiscountCharge(RentalAgreement rentalAgreement) {
        return rentalAgreement.getChargeDays() * rentalAgreement.getDailyRentalCharge();
        //TODO round
    }

    //Calculated from discount % and pre-discount charge. Resulting amount rounded half up to cents.
    public float calculateDiscountAmount(RentalAgreement rentalAgreement) {
        return rentalAgreement.getDiscountPercent() * rentalAgreement.getPreDiscountCharge();
        //TODO round
    }

    //Calculated as pre-discount charge - discount amount.
    public Float calculateFinalCharge(RentalAgreement rentalAgreement) {
        return rentalAgreement.getPreDiscountCharge() - rentalAgreement.getDiscountAmount();
        //TODO round
    }
}
