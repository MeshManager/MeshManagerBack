package org.management.service.api;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.management.service.common.DataResponse;
import org.management.service.common.StatusResponse;
import org.management.service.dto.request.ClusterResourceRequest;
import org.management.service.dto.response.*;
import org.management.service.service.ManagementService;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RequestMapping("/api/v1/management/clusters")
@RestController
public class ManagementController {

  private final ManagementService managementService;

  @Operation(
      summary = "고객 k8s 클러스터의 리소스 저장 API",
      description = "해시 값이 변경될 때만 기존 리소스 (service, deployment)를 삭제하고 다시 저장함"
  )
  @PostMapping("/state")
  public StatusResponse saveClusterResource(@RequestBody ClusterResourceRequest request) {
    return managementService.saveClusterResource(request);
  }

  @Operation(
      summary = "고객 k8s 클러스터의 네임스페이스 목록 조회 API (내부 호출용)"
  )
  @GetMapping("/namespaces")
  public ClusterNamespacesResponse getClusterNamespaces(@RequestParam(name = "clusterId") UUID clusterId) {
    return managementService.getClusterNamespaces(clusterId);
  }

  @Operation(
      summary = "고객 k8s 클러스터의 네임스페이스 별 서비스 이름 목록 조회 API (내부 호출용)"
  )
  @GetMapping("/services")
  public ClusterServicesResponse getServicesByNamespace(
      @RequestParam(name = "namespace") String namespace,
      @RequestParam(name = "clusterId") UUID clusterId
  ) {
    return managementService.getServicesByNamespace(namespace, clusterId);
  }

  @Operation(
      summary = "고객 k8s 클러스터의 deployment 데이터 조회 API (내부 호출용)"
  )
  @GetMapping("/deployments")
  public DataResponse<DeploymentResponse> getDeployments(
      @RequestParam(name = "namespace") String namespace,
      @RequestParam(name = "serviceName") String serviceName,
      @RequestParam(name = "clusterId") UUID clusterId
  ) {
    return managementService.getDeployments(clusterId, namespace, serviceName);
  }
}
