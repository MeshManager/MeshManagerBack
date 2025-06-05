package org.management.service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.Map;

public record ClusterResourceRequest(
    @NotBlank String hash,
    @NotEmpty Map<String, Object> json
) {
}
