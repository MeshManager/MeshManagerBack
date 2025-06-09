package org.management.service.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.management.service.common.StatusResponse;
import org.management.service.dto.request.ClusterResourceRequest;
import org.management.service.dto.request.Resource;
import org.management.service.dto.response.ClusterNamespacesResponse;
import org.management.service.dto.response.ClusterServicesResponse;
import org.management.service.entity.ClusterResource;
import org.management.service.repository.ClusterResourceRepository;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ManagementService {

  private static final String DEPLOYMENT_KIND = "Deployment";
  private static final String SERVICE_KIND = "Service";

  private final ClusterResourceRepository clusterResourceRepository;
  private final RedisTemplate<String, String> redisTemplate;

  @Transactional
  public StatusResponse saveClusterResource(ClusterResourceRequest request) {
    String clusterId = request.uuid();
    String existingHash = redisTemplate.opsForValue().get(clusterId);

    if (!request.hash().equals(existingHash)) {
      redisTemplate.delete(clusterId);
      redisTemplate.opsForValue().set(clusterId, request.hash());

      clusterResourceRepository.deleteAllByClusterId(UUID.fromString(clusterId));

      List<ClusterResource> resources = buildResources(clusterId, request.namespaces());
      clusterResourceRepository.saveAll(resources);
    }

    return StatusResponse.of(true);
  }

  @Transactional
  public StatusResponse notifyAgentOnResourceChange(String clusterId) {
    List<ClusterResource> clusterResources = clusterResourceRepository.findAllByClusterIdAndChangedIsTrue(UUID.fromString(clusterId));
    List<String> links = new ArrayList<>();

    // TODO: 매니페스트 파일 형식으로 반환

    return StatusResponse.of(links);
  }

  public ClusterNamespacesResponse getClusterNamespaces(UUID clusterId) {
    List<String> namespaces = clusterResourceRepository.findDistinctNamespacesByClusterId(clusterId);
    return ClusterNamespacesResponse.of(namespaces);
  }

  public ClusterServicesResponse getServicesByNamespace(String namespace, UUID clusterId) {
    List<String> serviceNames = clusterResourceRepository.findServiceNamesByClusterIdAndNamespace(clusterId, namespace);
    return ClusterServicesResponse.of(serviceNames);
  }

  private List<ClusterResource> buildResources(String clusterId, List<Resource> namespaces) {
    List<ClusterResource> result = new ArrayList<>();
    ObjectMapper mapper = new ObjectMapper();

    for (Resource ns : namespaces) {
      String namespace = ns.namespace();

      // Deployment 처리
      if (ns.deployments() != null) {
        for (Map<String, Object> deployment : ns.deployments()) {
          String json = serializeToJson(mapper, deployment);
          result.add(ClusterResource.create(clusterId, namespace, DEPLOYMENT_KIND, json));
        }
      }

      // Service 처리
      if (ns.services() != null) {
        for (Map<String, Object> service : ns.services()) {
          String json = serializeToJson(mapper, service);
          result.add(ClusterResource.create(clusterId, namespace, SERVICE_KIND, json));
        }
      }
    }

    return result;
  }

  private String serializeToJson(ObjectMapper mapper, Map<String, Object> resource) {
    try {
      return mapper.writeValueAsString(resource);
    } catch (JsonProcessingException e) {
      return "{}";
    }
  }
}
