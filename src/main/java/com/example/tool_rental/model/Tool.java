package com.example.tool_rental.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Tool {
    @Id
    private String toolCode;

    @ManyToOne
    @JoinColumn(name = "toolType", referencedColumnName = "name")
    private ToolType toolType;

    private String brand;
}
