package org.agent.service.dto;

import java.util.Map;

public record SaveClusterStateRequest(Map<String, Object> state) {
}
