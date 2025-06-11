package org.istizo.canaryservice.service;

import lombok.RequiredArgsConstructor;
import org.istizo.canaryservice.entity.ClusterCRD;
import org.istizo.canaryservice.repository.ClusterCRDRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ClusterCRDService {

  private final ClusterCRDRepository clusterCRDRepository;

  public String getClusterCRD(UUID clusterId) {
    ClusterCRD clusterCRD = clusterCRDRepository.findByClusterId(clusterId)
        .orElseThrow(null);

    return clusterCRD.getYaml();
  }
}
