package com.example.tool_rental.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ToolType {
    String name; //id
    float dailyCharge;
    boolean weekdayCharge;
    boolean weekendCharge;
    boolean holidayCharge;
}
