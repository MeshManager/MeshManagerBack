package org.istizo.clusterservice.dto.response;

import java.util.UUID;

public record ClusterListResponse(
    UUID uuid, String name
) {
}
