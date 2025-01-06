package com.example.tool_rental.model;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


class CheckoutRequestTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @ParameterizedTest
    @CsvSource({
            ", 1, 1, 2025-01-01, toolCode: must not be blank",
            "ABC, , 1, 2025-01-01, rentalDayCount: must not be null",
            "ABC, 1, , 2025-01-01, discountPercent: must not be null",
            "ABC, 1, 1, , checkoutDate: must not be null",
            "ABC, 0, 1, 2025-01-01, rentalDayCount: must be greater than or equal to 1",
            "ABC, 1, -1, 2025-01-01, discountPercent: must be greater than or equal to 0",
            "ABC, 1, 101, 2025-01-01, discountPercent: must be less than or equal to 100"
    })
    void testValidCheckoutRequest(String toolCode, Integer rentalDayCount, Integer discountPercent, LocalDate checkoutDate, String expectedViolation) {
        CheckoutRequest request = new CheckoutRequest();
        request.setToolCode(toolCode);
        request.setRentalDayCount(rentalDayCount);
        request.setDiscountPercent(discountPercent);
        request.setCheckoutDate(checkoutDate);

        List<String> actualViolations = validator.validate(request).stream()
                .map(violation -> violation.getPropertyPath() + ": " + violation.getMessage())
                .toList();

        assertEquals(Arrays.asList(expectedViolation), actualViolations);
    }
}