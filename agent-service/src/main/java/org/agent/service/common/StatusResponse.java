package org.agent.service.common;

public record StatusResponse(Boolean status) {

  public static StatusResponse from(Boolean status) {
    return new StatusResponse(status);
  }
}
