package com.example.tool_rental.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
public class RentalAgreement {

//    Tool tool;
    String toolCode; //Specified at checkout
    String toolType; //From tool info
    String toolBrand; //From tool info
    Float dailyRentalCharge; //Amount per day, specified by the tool type.

//    CheckoutRequest checkoutRequest;
    Integer rentalDays; //Specified at checkout
    LocalDate checkoutDate; //Specified at checkout
    Integer discountPercent; //Specified at checkout.

    @DateTimeFormat(pattern = "MM/dd/yy")
    LocalDate dueDate; //Calculated from checkout date and rental days.
    Integer chargeDays; //Count of chargeable days, from day after checkout through and including due date, excluding “no charge” days as specified by the tool type.
    Float preDiscountCharge; //Calculated as charge days X daily charge. Resulting total rounded half up to cents.
    Float discountAmount; //calculated from discount % and pre-discount charge. Resulting amount rounded half up to cents.
    Float finalCharge; //Calculated as pre-discount charge - discount amount.

    //TODO consider Currency data type
    //TODO override tostring or some other print method
}
