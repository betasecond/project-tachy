package edu.jimei.projecttachy.controller.dto;

import java.util.List;

public record AgentDto(
        Long id,
        String name,
        String description,
        String avatarUrl,
        List<String> compatibleSources
) {
} 