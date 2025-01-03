package com.example.tool_rental.service;

import com.example.tool_rental.model.CheckoutRequest;
import com.example.tool_rental.model.RentalAgreement;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class CheckoutService {

    public RentalAgreement checkout(CheckoutRequest checkoutRequest) {
        return new RentalAgreement();
    }

    //Calculated from checkout date and rental days.
    public LocalDate calculateDueDate(LocalDate date, int days) {
        return date.plusDays(days);
    }
}
