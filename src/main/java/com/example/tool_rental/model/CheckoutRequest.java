package com.example.tool_rental.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class CheckoutRequest {
    @NotBlank
    String toolCode;
    int rentalDayCount;
    float discountPercent;
    Date checkoutDate;
}
