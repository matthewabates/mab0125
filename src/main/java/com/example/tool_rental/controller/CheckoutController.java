package com.example.tool_rental.controller;

import com.example.tool_rental.model.CheckoutRequest;
import com.example.tool_rental.model.RentalAgreement;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CheckoutController {

    @PostMapping("checkout")
    public RentalAgreement checkout(@RequestBody CheckoutRequest checkoutRequest) {
        return null;
    }
}
