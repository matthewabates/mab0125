package com.example.tool_rental.model;

import java.util.Date;

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
