package org.istizo.clusterservice.service;

import lombok.RequiredArgsConstructor;
import org.istizo.clusterservice.dto.request.RegisterClusterRequest;
import org.istizo.clusterservice.dto.response.RegisterClusterResponse;
import org.istizo.clusterservice.dto.response.ClusterResponse;
import org.istizo.clusterservice.dto.response.ClusterDetailResponse;
import org.istizo.clusterservice.entity.Cluster;
import org.istizo.clusterservice.repository.ClusterRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.Map;

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

  public boolean checkDuplicateName(String name) {
    return clusterRepository.existsByName(name);
  }

  public List<ClusterResponse> findAll() {
    return clusterRepository.findAll()
        .stream()
        .map(ClusterResponse::of)
        .collect(Collectors.toList());
  }

  @Transactional
  public void deleteByUuid(UUID uuid) {
    clusterRepository.deleteById(uuid);
  }

  public ClusterDetailResponse getClusterDetailsByUuid(UUID uuid) {
    Cluster cluster = clusterRepository.findById(uuid)
        .orElseThrow(() -> new RuntimeException("Cluster not found with UUID: " + uuid));

    // TODO: 실제 Kubernetes API 호출을 통해 상세 정보를 가져와야 합니다.
    // 현재는 임시 데이터(placeholder)를 반환합니다.
    return ClusterDetailResponse.builder()
        .uuid(cluster.getUuid())
        .name(cluster.getName())
        .prometheusUrl(cluster.getPrometheusUrl())
        .token(cluster.getToken()) // 실제 배포 시 보안상 노출하지 않을 수 있음
        .namespace("default") // 임시 데이터
        .containers(List.of( // 임시 데이터
            ClusterDetailResponse.ContainerInfo.builder().name("nginx-container").image("nginx:latest").build(),
            ClusterDetailResponse.ContainerInfo.builder().name("agent-container").image("mesh-manager-agent:v1.0").build()
        ))
        .podLabels(Map.of("app", "nginx", "env", "production")) // 임시 데이터
        .replicaCount(3) // 임시 데이터
        .services(List.of( // 임시 데이터
            ClusterDetailResponse.ServiceInfo.builder()
                .name("my-service")
                .clusterIp("10.96.0.100")
                .type("ClusterIP")
                .selector(Map.of("app", "nginx"))
                .build()
        ))
        .build();
  }
}
