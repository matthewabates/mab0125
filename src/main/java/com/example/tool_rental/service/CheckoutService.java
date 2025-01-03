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

    public LocalDate getDueDate(LocalDate date, int days) {
        return date.plusDays(days);
    }
}
