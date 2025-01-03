package com.example.tool_rental.model;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class CheckoutRequest {
    @NotBlank
    String toolCode;

    //The number of days for which the customer wants to rent the tool. (e.g. 4 days)
    @NotNull
    @Min(1)
    Integer rentalDayCount;

    @NotNull
    @Min(0)
    @Max(100)
    //As a whole number, 0-100 (e.g. 20 = 20%)
    Integer discountPercent;

    @NotNull
    @Future
    Date checkoutDate;
}
