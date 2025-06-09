package org.istizo.clusterservice.dto.request;

import jakarta.validation.constraints.NotBlank;

public record RegisterClusterRequest(
    @NotBlank String name,
    @NotBlank String token,
    String prometheusUrl
) {
}
