package org.istizo.canaryservice.api;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.istizo.canaryservice.service.ClusterCRDService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RequiredArgsConstructor
@RequestMapping("/api/v1/crd")
@RestController
public class ClusterCRDController {

  private final ClusterCRDService clusterCRDService;

  @Operation(
      summary = "고객 k8s 클러스터의 CRD 조회 API"
  )
  @GetMapping
  public ResponseEntity<String> getClusterCRD(@RequestParam(name = "clusterId") UUID clusterId) {
    return ResponseEntity.ok()
        .header(HttpHeaders.CONTENT_TYPE, "text/plain; charset=UTF-8")
        .body(clusterCRDService.getClusterCRD(clusterId));
  }
}
