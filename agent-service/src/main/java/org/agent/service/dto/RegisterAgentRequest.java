package org.agent.service.dto;

import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public record RegisterAgentRequest(
    @NotBlank String name, @NotBlank UUID clusterId
) {
}
