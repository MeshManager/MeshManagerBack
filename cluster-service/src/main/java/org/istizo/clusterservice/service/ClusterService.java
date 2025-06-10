package org.istizo.clusterservice.service;

import lombok.RequiredArgsConstructor;
import org.istizo.clusterservice.common.DataResponse;
import org.istizo.clusterservice.dto.response.*;
import org.istizo.clusterservice.dto.request.RegisterClusterRequest;
import org.istizo.clusterservice.entity.Cluster;
import org.istizo.clusterservice.repository.ClusterRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ClusterService {

  private final ClusterRepository clusterRepository;
  private final RestTemplate restTemplate;

  @Transactional
  public RegisterClusterResponse register(RegisterClusterRequest request) {
    Cluster cluster = clusterRepository.save(
        Cluster.create(request.name(), request.token(), request.prometheusUrl())
    );
    return RegisterClusterResponse.of(cluster.getUuid());
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
