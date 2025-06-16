package org.istizo.clusterservice.api;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.istizo.clusterservice.common.DataResponse;
import org.istizo.clusterservice.dto.request.RegisterClusterRequest;
import org.istizo.clusterservice.dto.response.*;
import org.istizo.clusterservice.service.ClusterService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RequestMapping("/api/v1/cluster")
@RestController

public class ClusterController {

  private final ClusterService clusterService;

  @Operation(
      summary = "고객의 k8s 클러스터 등록 API"
  )
  @PostMapping
  public RegisterClusterResponse registerAgent(@RequestBody RegisterClusterRequest request) {
    return clusterService.register(request);
  }

  @Operation(
      summary = "클러스터 이름 중복 확인 API",
      description = "지정된 클러스터 이름이 이미 존재하는지 확인합니다."
  )
  @GetMapping("/check-duplicate/{clusterName}")
  public ResponseEntity<Boolean> checkDuplicate(@PathVariable String clusterName) {
    boolean isDuplicate = clusterService.checkDuplicateClusterName(clusterName);
    return ResponseEntity.ok(isDuplicate);
  }

  @Operation(
      summary = "고객 k8s 클러스터 목록 조회 API"
  )
  @GetMapping
  public DataResponse<ClusterListResponse> getClustersByUser() {
    return clusterService.getClustersByUserId(1L);
  }

  @Operation(
      summary = "고객 k8s 클러스터 삭제 API",
      description = "지정된 UUID를 가진 클러스터를 삭제합니다."
  )
  @DeleteMapping("/{uuid}")
  public ResponseEntity<Void> deleteCluster(@PathVariable UUID uuid) {
    clusterService.deleteCluster(uuid);
    return ResponseEntity.noContent().build();
  }

  @Operation(
      summary = "고객 k8s 클러스터 상세 정보 조회 API",
      description = "지정된 UUID를 가진 클러스터의 상세 정보를 조회합니다."
  )
  @GetMapping("/{uuid}/details")
  public ResponseEntity<ClusterDetailResponse> getClusterDetails(@PathVariable UUID uuid) {
    ClusterDetailResponse details = clusterService.getClusterDetails(uuid);
    return ResponseEntity.ok(details);
  }

  @Operation(
      summary = "고객 k8s 클러스터의 네임스페이스 목록 조회 API"
  )
  @GetMapping("/namespaces")
  public NamespaceListResponse getNamespaces(@RequestParam(name = "clusterId") UUID clusterId) {
    return clusterService.getNamespaces(clusterId);
  }

  @Operation(
      summary = "고객 k8s 클러스터의 네임스페이스 별 서비스 이름 목록 조회 API"
  )
  @GetMapping("/services")
  public ServiceNameListResponse getServiceNames(
      @RequestParam(name = "namespace") String namespace,
      @RequestParam(name = "clusterId") UUID clusterId
  ) {
    return clusterService.getServiceNames(clusterId, namespace);
  }

  @Operation(
      summary = "고객 k8s 클러스터의 deployment 데이터 조회 API"
  )
  @GetMapping("/deployments")
  public DeploymentListResponse getDeployments(
      @RequestParam(name = "namespace") String namespace,
      @RequestParam(name = "serviceName") String serviceName,
      @RequestParam(name = "clusterId") UUID clusterId
  ) {
    return clusterService.getDeployments(clusterId, namespace, serviceName);
  }
}


