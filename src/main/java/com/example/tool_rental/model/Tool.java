package com.example.tool_rental.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Tool {
    String toolCode;
    ToolType toolType;
    String brand;
}
