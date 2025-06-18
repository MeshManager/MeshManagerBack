package org.istizo.clusterservice.dto.response;

import lombok.Builder;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public record ClusterDetailResponse(
        UUID uuid,
        String name,
        String prometheusUrl,
        String token,
        String namespace,
        List<ContainerInfo> containers,
        Map<String, String> podLabels,
        Integer replicaCount,
        List<ServiceInfo> services
) {
    @Builder
    public ClusterDetailResponse {}

    public record ContainerInfo(
            String name,
            String image
    ) {}

    public record ServiceInfo(
            String name,
            String clusterIp,
            String type,
            Map<String, String> selector
    ) {}
} 