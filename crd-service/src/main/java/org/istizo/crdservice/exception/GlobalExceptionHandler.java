package org.istizo.crdservice.exception;

import lombok.extern.slf4j.Slf4j;
import org.management.service.common.StatusResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(Exception.class)
  public ResponseEntity<StatusResponse> handleException(Exception ex) {
    log.error("Exception occurred", ex);

    StatusResponse response = StatusResponse.of(false, ex.getMessage());
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
  }
}
