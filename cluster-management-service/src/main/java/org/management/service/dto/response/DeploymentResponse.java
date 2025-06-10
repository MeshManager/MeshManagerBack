package org.management.service.dto.response;

import org.management.service.dto.ContainerInfo;

import java.util.List;
import java.util.Map;

public record DeploymentResponse(
    String name,
    List<ContainerInfo> containers,
    Map<String, String> podLabels,
    int replicas
) {
}
