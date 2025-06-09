package org.management.service.service;

import lombok.RequiredArgsConstructor;
import org.management.service.common.StatusResponse;
import org.management.service.dto.ClusterResourceRequest;
import org.management.service.entity.ClusterResource;
import org.management.service.repository.ClusterResourceRepository;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ManagementService {

  private static final String DEPLOYMENT_KIND = "Deployment";
  private static final String SERVICE_KIND = "Service";

  private final ClusterResourceRepository clusterResourceRepository;
  private final RedisTemplate<String, String> redisTemplate;

  @Transactional
  public StatusResponse processClusterResource(String clusterId, ClusterResourceRequest request) {
    String existingHash = redisTemplate.opsForValue().get(clusterId);

    if (!request.hash().equals(existingHash)) {
      redisTemplate.delete(clusterId);
      redisTemplate.opsForValue().set(clusterId, request.hash());

      clusterResourceRepository.deleteAllByClusterId(Long.valueOf(clusterId));

      List<ClusterResource> resources = parseJsonToResources(clusterId, request.json());
      clusterResourceRepository.saveAll(resources);
    }

    return StatusResponse.of(true);
  }

  @Transactional
  public StatusResponse notifyAgentOnResourceChange(Long clusterId) {
    List<ClusterResource> clusterResources = clusterResourceRepository.findAllByClusterIdAndChangedIsTrue(clusterId);
    List<String> links = new ArrayList<>();

    // TODO: 매니페스트 파일 형식으로 반환

    return StatusResponse.of(links);
  }

  private List<ClusterResource> parseJsonToResources(String clusterId, Map<String, Object> jsonData) {
    List<ClusterResource> result = new ArrayList<>();
    Object nsObj = jsonData.get("namespaces");

    if (!(nsObj instanceof List)) {
      return result;
    }

    List<Map<String, Object>> namespaces = (List<Map<String, Object>>) nsObj;

    for (Map<String, Object> ns : namespaces) {
      String namespace = (String) ns.get("namespace");

      List<Map<String, Object>> deployments = (List<Map<String, Object>>) ns.get("deployments");
      for (Map<String, Object> dep : deployments) {
        ClusterResource cr = ClusterResource.create(clusterId, namespace, DEPLOYMENT_KIND, dep.toString());
        result.add(cr);
      }

      List<Map<String, Object>> services = (List<Map<String, Object>>) ns.get("services");
      for (Map<String, Object> svc : services) {
        ClusterResource cr = ClusterResource.create(clusterId, namespace, SERVICE_KIND, svc.toString());
        result.add(cr);
      }
    }

    return result;
  }
}
