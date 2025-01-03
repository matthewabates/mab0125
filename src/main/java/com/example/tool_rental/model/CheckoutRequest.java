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

    @NotNull
    @Min(1)
    Integer rentalDayCount;

    @NotNull
    @Min(0)
    @Max(100)
    Integer discountPercent;

    @NotNull
    @Future
    Date checkoutDate;
}
