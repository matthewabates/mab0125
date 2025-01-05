package com.example.tool_rental.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class CheckoutRequest {
    @NotBlank
    String toolCode;

    @NotNull
    @Min(1)
    Integer rentalDayCount; //The number of days for which the customer wants to rent the tool. (e.g. 4 days)

    @NotNull
    @Min(0)
    @Max(100)
    Integer discountPercent; //As a whole number, 0-100 (e.g. 20 = 20%)

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "M/d/yy")
    LocalDate checkoutDate;
}
