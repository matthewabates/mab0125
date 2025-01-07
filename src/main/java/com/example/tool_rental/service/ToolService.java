package com.example.tool_rental.service;

import com.example.tool_rental.model.Tool;
import com.example.tool_rental.repo.ToolRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class ToolService {

    private final ToolRepository toolRepository;

    public ToolService(ToolRepository toolRepository) {
        this.toolRepository = toolRepository;
    }

    @Cacheable(value = "tools", key = "#toolCode")
    public Tool getTool(String toolCode) {
        return toolRepository.findByToolCode(toolCode)
                .orElseThrow(() -> new EntityNotFoundException("Tool with code " + toolCode + " not found."));
    }
}
