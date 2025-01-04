package com.example.tool_rental.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ToolType {
    String name;
    Float dailyCharge;
    Boolean weekdayCharge;
    Boolean weekendCharge;
    Boolean holidayCharge;
}
