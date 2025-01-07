package com.example.tool_rental.service;

import com.example.tool_rental.model.Tool;
import com.example.tool_rental.repo.ToolRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
@DirtiesContext
class ToolServiceTest {

    @Autowired
    private ToolService toolService;

    @MockitoBean
    private ToolRepository toolRepository;

    @Test
    void testGetToolThrowsExceptionWhenNotFound() {
        String toolCode = "INVALID_CODE";
        when(toolRepository.findByToolCode(toolCode)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> toolService.getTool(toolCode));
        assertThat(exception.getMessage()).isEqualTo("Tool with code INVALID_CODE not found.");

        verify(toolRepository, times(1)).findByToolCode(toolCode);
    }

    @Test
    void testGetToolReturnsTool() {
        String toolCode = "VALID_CODE";
        Tool tool = new Tool();
        when(toolRepository.findByToolCode(toolCode)).thenReturn(Optional.of(tool));

        Tool actualTool = toolService.getTool(toolCode);

        assertEquals(actualTool, tool);
    }

    @Test
    void testGetToolCaching() {
        String toolCode = "123";
        Tool tool = new Tool();
        tool.setToolCode(toolCode);

        when(toolRepository.findByToolCode(toolCode)).thenReturn(Optional.of(tool));

        // First call should invoke the repository
        assertThat(toolService.getTool(toolCode)).isSameAs(tool);
        verify(toolRepository, times(1)).findByToolCode(toolCode);

        // Second call should fetch from the cache
        assertThat(toolService.getTool(toolCode)).isSameAs(tool);
        verifyNoMoreInteractions(toolRepository);
    }
}