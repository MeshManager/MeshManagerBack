package org.agent.service.dto;

import java.util.Map;

public record SaveClusterStateRequest(
        String hash,
        Map<String, Object> namespaces,
        String uuid
) {}
