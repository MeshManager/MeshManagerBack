package org.istizo.clusterservice.dto.response;

import org.istizo.clusterservice.entity.Cluster;

import java.util.UUID;

public record ClusterResponse(
    UUID uuid,
    String name,
    String token,
    String prometheusUrl
) {
    public static ClusterResponse of(Cluster cluster) {
        return new ClusterResponse(
                cluster.getUuid(),
                cluster.getName(),
                cluster.getToken(),
                cluster.getPrometheusUrl()
        );
    }
} 