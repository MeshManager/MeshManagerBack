package org.management.service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record ClusterResourceRequest(
    @NotBlank String hash,
    @NotBlank String uuid,
    @NotEmpty List<Resource> namespaces
) {
}
