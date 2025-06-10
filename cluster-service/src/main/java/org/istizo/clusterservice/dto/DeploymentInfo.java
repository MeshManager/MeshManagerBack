package org.istizo.clusterservice.dto;

import java.util.List;
import java.util.Map;

public record DeploymentInfo(
    String name,
    List<ContainerInfo> containers,
    Map<String, String> podLabels,
    int replicas
) {
}