package org.management.service.dto;

import java.util.List;
import java.util.Map;

public record Resource(
    String namespace,
    List<Map<String, Object>> deployments,
    List<Map<String, Object>> services
) {
}
