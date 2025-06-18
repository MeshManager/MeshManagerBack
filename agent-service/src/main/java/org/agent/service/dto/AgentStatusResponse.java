package org.agent.service.dto;

import java.util.UUID;

public record AgentStatusResponse(
    String name,
    UUID clusterId,
    boolean isConnected
) {
} 