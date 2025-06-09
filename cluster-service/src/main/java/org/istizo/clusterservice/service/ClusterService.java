package org.istizo.clusterservice.service;

import lombok.RequiredArgsConstructor;
import org.istizo.clusterservice.dto.request.RegisterClusterRequest;
import org.istizo.clusterservice.dto.response.RegisterClusterResponse;
import org.istizo.clusterservice.entity.Cluster;
import org.istizo.clusterservice.repository.ClusterRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ClusterService {

  private final ClusterRepository clusterRepository;

  @Transactional
  public RegisterClusterResponse register(RegisterClusterRequest request) {
    Cluster cluster = clusterRepository.save(
        Cluster.create(request.name(), request.token(), request.prometheusUrl())
    );
    return RegisterClusterResponse.of(cluster.getUuid());
  }
}
