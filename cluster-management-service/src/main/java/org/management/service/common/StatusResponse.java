package org.management.service.common;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record StatusResponse(
    boolean status, List<String> data, String errorMessage
) {

  public static StatusResponse of(Boolean status) {
    return new StatusResponse(status, null, null);
  }

  public static StatusResponse of(List<String> data) {
    return new StatusResponse(true, data, null);
  }

  public static StatusResponse of(Boolean status, String errorMessage) {
    return new StatusResponse(status, null, errorMessage);
  }
}
