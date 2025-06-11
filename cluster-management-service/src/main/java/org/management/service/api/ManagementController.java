package org.management.service.api;

import lombok.RequiredArgsConstructor;
import org.management.service.common.DataResponse;
import org.management.service.common.StatusResponse;
import org.management.service.dto.request.ClusterResourceRequest;
import org.management.service.dto.response.*;
import org.management.service.service.ManagementService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RequestMapping("/api/v1/management/clusters")
@RestController
public class ManagementController {

  private final ManagementService managementService;

  @PostMapping
  public StatusResponse saveClusterResource(@RequestBody ClusterResourceRequest request) {
    return managementService.saveClusterResource(request);
  }

  @GetMapping("/namespaces")
  public ClusterNamespacesResponse getClusterNamespaces(@RequestParam(name = "clusterId") UUID clusterId) {
    return managementService.getClusterNamespaces(clusterId);
  }

  @GetMapping("/services")
  public ClusterServicesResponse getServicesByNamespace(
      @RequestParam(name = "namespace") String namespace,
      @RequestParam(name = "clusterId") UUID clusterId
  ) {
    return managementService.getServicesByNamespace(namespace, clusterId);
  }

  @GetMapping("/deployments")
  public DataResponse<DeploymentResponse> getContainers(
      @RequestParam(name = "namespace") String namespace,
      @RequestParam(name = "serviceName") String serviceName,
      @RequestParam(name = "clusterId") UUID clusterId
  ) {
    return managementService.fetchContainers(clusterId, namespace, serviceName);
  }

  @GetMapping("/crd")
  public ResponseEntity<String> getClusterCRD(@RequestParam(name = "clusterId") UUID clusterId) {
    return ResponseEntity.ok()
        .header(HttpHeaders.CONTENT_TYPE, "text/plain; charset=UTF-8")
        .body(managementService.getClusterCRD(clusterId));
  }
}
