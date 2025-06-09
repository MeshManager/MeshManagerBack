package org.istizo.clusterservice.service;

import lombok.RequiredArgsConstructor;
import org.istizo.clusterservice.common.DataResponse;
import org.istizo.clusterservice.dto.response.ClusterListResponse;
import org.istizo.clusterservice.dto.request.RegisterClusterRequest;
import org.istizo.clusterservice.dto.response.NamespaceListResponse;
import org.istizo.clusterservice.dto.response.RegisterClusterResponse;
import org.istizo.clusterservice.dto.response.ServiceNameListResponse;
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

  public NamespaceListResponse fetchNamespaces(UUID clusterId) {
    String url = String.format(
        "http://localhost:8080/api/v1/management/cluster/namespaces?clusterId=%s", clusterId
    );
    return restTemplate.getForObject(url, NamespaceListResponse.class);
  }

  public ServiceNameListResponse fetchServiceNames(UUID clusterId, String namespace) {
    String url = String.format(
        "http://localhost:8080/api/v1/management/cluster/namespaces/%s/services?clusterId=%s",
        namespace, clusterId
    );

    return restTemplate.getForObject(url, ServiceNameListResponse.class);
  }
}
