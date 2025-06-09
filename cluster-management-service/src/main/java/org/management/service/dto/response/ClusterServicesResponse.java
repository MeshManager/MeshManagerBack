package org.management.service.dto.response;

import java.util.List;

public record ClusterServicesResponse(
    List<String> serviceNames
) {

  public static ClusterServicesResponse of(List<String> serviceNames) {
    return new ClusterServicesResponse(serviceNames);
  }
}
