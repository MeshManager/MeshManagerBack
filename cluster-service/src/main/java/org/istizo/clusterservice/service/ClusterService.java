package org.istizo.clusterservice.service;

import lombok.RequiredArgsConstructor;
import org.istizo.clusterservice.common.DataResponse;
import org.istizo.clusterservice.dto.response.*;
import org.istizo.clusterservice.dto.request.RegisterClusterRequest;
import org.istizo.clusterservice.entity.Cluster;
import org.istizo.clusterservice.repository.ClusterRepository;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ClusterService {

  private static final Logger logger = LoggerFactory.getLogger(ClusterService.class);

  private final ClusterRepository clusterRepository;
  private final RestTemplate restTemplate;

  @Transactional
  public RegisterClusterResponse register(RegisterClusterRequest request) {
    Cluster cluster = clusterRepository.save(
        Cluster.create(request.name(), request.token(), request.prometheusUrl())
    );
    return RegisterClusterResponse.of(cluster.getUuid());
  }

  public boolean checkDuplicateClusterName(String clusterName) {
    return clusterRepository.existsByName(clusterName);
  }

  @Transactional
  public void deleteCluster(UUID uuid) {
    logger.info("Attempting to delete cluster with UUID: {}", uuid);
    try {
      clusterRepository.deleteById(uuid);
      logger.info("Successfully deleted cluster with UUID: {}", uuid);
    } catch (Exception e) {
      logger.error("Failed to delete cluster with UUID: {}", uuid, e);
      throw new RuntimeException("Failed to delete cluster", e);
    }
  }

  @Transactional(readOnly = true)
  public ClusterDetailResponse getClusterDetails(UUID uuid) {
    logger.info("Attempting to retrieve cluster details for UUID: {}", uuid);
    return clusterRepository.findById(uuid)
        .map(cluster -> ClusterDetailResponse.builder()
            .uuid(cluster.getUuid())
            .name(cluster.getName())
            .prometheusUrl(cluster.getPrometheusUrl())
            .token(cluster.getToken())
            // 추가적인 필드는 필요에 따라 다른 서비스에서 가져오거나 기본값 설정
            .build())
        .orElseThrow(() -> {
          logger.warn("Cluster with UUID {} not found.", uuid);
          return new RuntimeException("Cluster not found with UUID: " + uuid);
        });
  }

  public DataResponse<ClusterListResponse> getClustersByUserId(Long userId) {
    List<ClusterListResponse> clusters = clusterRepository.findClustersByUserId(userId);
    return DataResponse.of(clusters);
  }

  // TODO: 서버 배포 후 엔드포인트 수정
  public NamespaceListResponse getNamespaces(UUID clusterId) {
    String url = String.format(
        "http://localhost:8085/api/v1/management/clusters/namespaces?clusterId=%s", clusterId
    );
    return restTemplate.getForObject(url, NamespaceListResponse.class);
  }

  public ServiceNameListResponse getServiceNames(UUID clusterId, String namespace) {
    String url = String.format(
        "http://localhost:8085/api/v1/management/clusters/services?clusterId=%s&namespace=%s",
        clusterId, namespace
    );

    return restTemplate.getForObject(url, ServiceNameListResponse.class);
  }

  public DeploymentListResponse getDeployments(UUID clusterId, String namespace, String serviceName) {
    String url = String.format(
        "http://localhost:8085/api/v1/management/clusters/deployments?clusterId=%s&namespace=%s&serviceName=%s",
        clusterId, namespace, serviceName
    );

    return restTemplate.getForObject(url, DeploymentListResponse.class);
  }
}
