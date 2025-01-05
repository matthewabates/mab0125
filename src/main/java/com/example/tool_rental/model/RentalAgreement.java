package com.example.tool_rental.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Getter
@Setter
public class RentalAgreement {
    private String toolCode; //Specified at checkout
    private String toolType; //From tool info
    private String toolBrand; //From tool info
    private BigDecimal dailyRentalCharge; //Amount per day, specified by the tool type.

    private Integer rentalDays; //Specified at checkout
    private LocalDate checkoutDate; //Specified at checkout
    private Integer discountPercent; //Specified at checkout.

    private LocalDate dueDate; //Calculated from checkout date and rental days.
    private Integer chargeDays; //Count of chargeable days, from day after checkout through and including due date, excluding “no charge” days as specified by the tool type.
    private BigDecimal preDiscountCharge; //Calculated as charge days X daily charge. Resulting total rounded half up to cents.
    private BigDecimal discountAmount; //calculated from discount % and pre-discount charge. Resulting amount rounded half up to cents.
    private BigDecimal finalCharge; //Calculated as pre-discount charge - discount amount.

    public void printToConsole() {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yy");
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(Locale.US);
        NumberFormat percentFormatter = NumberFormat.getPercentInstance(Locale.US);
        percentFormatter.setMaximumFractionDigits(0);

        System.out.println("Tool code: " + toolCode);
        System.out.println("Tool type: " + toolType);
        System.out.println("Tool brand: " + toolBrand);
        System.out.println("Rental days: " + rentalDays);
        System.out.println("Checkout date: " + checkoutDate.format(dateFormatter));
        System.out.println("Due date: " + dueDate.format(dateFormatter));
        System.out.println("Daily rental charge: " + currencyFormatter.format(dailyRentalCharge));
        System.out.println("Pre-discount charge: " + currencyFormatter.format(preDiscountCharge));
        System.out.println("Discount percent: " + percentFormatter.format(discountPercent / 100.0));
        System.out.println("Discount amount: " + currencyFormatter.format(discountAmount));
        System.out.println("Final charge: " + currencyFormatter.format(finalCharge));
    }
}
