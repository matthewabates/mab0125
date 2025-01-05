package com.example.tool_rental.repo;

import com.example.tool_rental.model.ToolType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ToolTypeRepository extends JpaRepository<ToolType, Long> {
    ToolType findByName(String name);
}
