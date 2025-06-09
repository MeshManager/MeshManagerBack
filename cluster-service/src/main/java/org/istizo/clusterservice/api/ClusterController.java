package org.istizo.clusterservice.api;

import lombok.RequiredArgsConstructor;
import org.istizo.clusterservice.dto.request.RegisterClusterRequest;
import org.istizo.clusterservice.dto.response.NamespaceListResponse;
import org.istizo.clusterservice.dto.response.RegisterClusterResponse;
import org.istizo.clusterservice.dto.response.ServiceNameListResponse;
import org.istizo.clusterservice.service.ClusterService;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RequestMapping("/api/v1/cluster")
@RestController
public class ClusterController {

  private final ClusterService clusterService;

  @PostMapping
  public RegisterClusterResponse registerAgent(@RequestBody RegisterClusterRequest request) {
    return clusterService.register(request);
  }

  @GetMapping("/namespaces")
  public NamespaceListResponse getServiceNames(@RequestParam(name = "clusterId")UUID clusterId) {
    return clusterService.fetchNamespaces(clusterId);
  }

  @GetMapping("/namespaces/{namespace}/services")
  public ServiceNameListResponse getNamespaces(
      @PathVariable(name = "namespace") String namespace,
      @RequestParam(name = "clusterId")UUID clusterId
  ) {
    return clusterService.fetchServiceNames(clusterId, namespace);
  }
}
