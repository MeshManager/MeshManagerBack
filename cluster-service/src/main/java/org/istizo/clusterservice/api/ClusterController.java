package org.istizo.clusterservice.api;

import lombok.RequiredArgsConstructor;
import org.istizo.clusterservice.common.DataResponse;
import org.istizo.clusterservice.dto.request.RegisterClusterRequest;
import org.istizo.clusterservice.dto.response.*;
import org.istizo.clusterservice.service.ClusterService;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RequestMapping("/api/v1/clusters")
@RestController
public class ClusterController {

  private final ClusterService clusterService;

  @PostMapping
  public RegisterClusterResponse registerAgent(@RequestBody RegisterClusterRequest request) {
    return clusterService.register(request);
  }

  @GetMapping
  public DataResponse<ClusterListResponse> getClustersByUser() {
    return clusterService.getClustersByUserId(1L);
  }

  @GetMapping("/namespaces")
  public NamespaceListResponse getNamespaces(@RequestParam(name = "clusterId") UUID clusterId) {
    return clusterService.getNamespaces(clusterId);
  }

  @GetMapping("/services")
  public ServiceNameListResponse getServiceNames(
      @RequestParam(name = "namespace") String namespace,
      @RequestParam(name = "clusterId") UUID clusterId
  ) {
    return clusterService.getServiceNames(clusterId, namespace);
  }

  @GetMapping("/deployments")
  public DeploymentListResponse getDeployments(
      @RequestParam(name = "namespace") String namespace,
      @RequestParam(name = "serviceName") String serviceName,
      @RequestParam(name = "clusterId") UUID clusterId
  ) {
    return clusterService.getDeployments(clusterId, namespace, serviceName);
  }
}
