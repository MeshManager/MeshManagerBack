package org.agent.service.dto;

public record SaveClusterStateRequest(
        String hash,
        //Map<String, Object> namespaces,
        String uuid
) {}
