package com.example.tool_rental.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
public class ToolType {
    @Id
    String name;
    BigDecimal dailyCharge;
    Boolean weekdayCharge;
    Boolean weekendCharge;
    Boolean holidayCharge;
}
