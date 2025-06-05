package org.agent.service.dto;

import jakarta.validation.constraints.NotBlank;

public record RegisterAgentRequest(@NotBlank String name) {
}
