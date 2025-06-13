package org.management.service.dto.response;

import lombok.Builder;
import lombok.Value;

import java.util.List;
import java.util.UUID;

@Value
@Builder
public class ClusterDetailResponse {
    UUID clusterId;
    List<NamespaceDetail> namespaces;
} 