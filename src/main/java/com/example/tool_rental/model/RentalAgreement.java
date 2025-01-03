package com.example.tool_rental.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class RentalAgreement {
    Tool tool;
    CheckoutRequest checkoutRequest;
    Date duedate;
    float dailyRentalCharge;
    int chargeDays;
    float preDiscountCharge;
    float discountAmount;
    float finalCharge;
    //TODO override tostring or some other print method
}
