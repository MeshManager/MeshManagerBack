package org.management.service.dto.response;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ResourceDetail {
    String name;
    String kind;
    String yaml;
} 