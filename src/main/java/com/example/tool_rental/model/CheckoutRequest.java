package com.example.tool_rental.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class CheckoutRequest {
    //TODO add validation such as @NotNull etc
    String toolCode;
    int rentalDayCount;
    float discountPercent;
    Date checkoutDate;
}
