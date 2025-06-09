package org.management.service.dto.response;

import java.util.List;

public record ClusterNamespacesResponse(
    List<String> namespaces
) {

  public static ClusterNamespacesResponse of(List<String> namespaces) {
    return new ClusterNamespacesResponse(namespaces);
  }
}
