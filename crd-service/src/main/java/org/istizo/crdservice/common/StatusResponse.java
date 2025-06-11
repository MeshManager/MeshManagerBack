package org.istizo.crdservice.common;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record StatusResponse(
    boolean status, String errorMessage
) {

  public static StatusResponse of(Boolean status) {
    return new StatusResponse(status, null);
  }

  public static StatusResponse of(Boolean status, String errorMessage) {
    return new StatusResponse(status, errorMessage);
  }
}
