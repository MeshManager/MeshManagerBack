package org.list.service.dto;

import lombok.Builder;
import lombok.Getter;
import java.util.UUID;

@Getter
@Builder
public class ClusterDto {
    private UUID uuid;
    private String name;
    private String token;
    private String prometheusUrl;
}
