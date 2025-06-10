package org.istizo.clusterservice.dto.response;

import java.util.UUID;

public record RegisterClusterResponse(UUID uuid) {

  public static RegisterClusterResponse of(UUID uuid) {
    return new RegisterClusterResponse(uuid);
  }
}
