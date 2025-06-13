package org.management.service.api;

import lombok.RequiredArgsConstructor;
import org.management.service.common.StatusResponse;
import org.management.service.dto.request.ClusterResourceRequest;
import org.management.service.dto.response.ClusterNamespacesResponse;
import org.management.service.dto.response.ClusterServicesResponse;
import org.management.service.dto.response.ClusterDetailResponse;
import org.management.service.service.ManagementService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RequestMapping("/api/v1/management/cluster")
@RestController
public class ManagementController {

  private final ManagementService managementService;

  @PostMapping
  public StatusResponse saveClusterResource(@RequestBody ClusterResourceRequest request) {
    return managementService.saveClusterResource(request);
  }

  @PatchMapping("/{clusterId}/changes")
  public StatusResponse notifyAgentOnResourceChange(@PathVariable(name = "clusterId") String clusterId) {
    return managementService.notifyAgentOnResourceChange(clusterId);
  }

  @GetMapping("/namespaces")
  public ClusterNamespacesResponse getClusterNamespaces(@RequestParam(name = "clusterId") UUID clusterId) {
    return managementService.getClusterNamespaces(clusterId);
  }

  @GetMapping("/namespaces/{namespace}/services")
  public ClusterServicesResponse getServicesByNamespace(
      @PathVariable(name = "namespace") String namespace,
      @RequestParam(name = "clusterId") UUID clusterId
  ) {
    return managementService.getServicesByNamespace(namespace, clusterId);
  }

  @GetMapping("/namespaces/{namespace}/{kind}/{resourceName}/yaml")
  public ResponseEntity<String> getResourceYaml(
      @RequestParam(name = "clusterId") UUID clusterId,
      @PathVariable(name = "namespace") String namespace,
      @PathVariable(name = "kind") String kind,
      @PathVariable(name = "resourceName") String resourceName
  ) {
    return managementService.getClusterResourceYaml(clusterId, namespace, kind, resourceName)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  @GetMapping("/{clusterId}/details")
  public ClusterDetailResponse getClusterDetails(@PathVariable(name = "clusterId") UUID clusterId) {
    return managementService.getClusterDetails(clusterId);
  }
}
