package org.management.service.api;

import lombok.RequiredArgsConstructor;
import org.management.service.common.DataResponse;
import org.management.service.common.StatusResponse;
import org.management.service.dto.request.ClusterResourceRequest;
import org.management.service.dto.response.ClusterNamespacesResponse;
import org.management.service.dto.response.ClusterServicesResponse;
import org.management.service.dto.response.DeploymentResponse;
import org.management.service.service.ManagementService;
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

  @GetMapping
  public DataResponse<DeploymentResponse> getContainers(
      @RequestParam(name = "namespace") String namespace,
      @RequestParam(name = "serviceName") String serviceName,
      @RequestParam(name = "clusterId") UUID clusterId
  ) {
    return managementService.fetchContainers(clusterId, namespace, serviceName);
  }
}
