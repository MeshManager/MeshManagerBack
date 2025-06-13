package org.management.service.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.management.service.common.StatusResponse;
import org.management.service.dto.request.ClusterResourceRequest;
import org.management.service.dto.request.Resource;
import org.management.service.dto.response.ClusterNamespacesResponse;
import org.management.service.dto.response.ClusterServicesResponse;
import org.management.service.dto.response.ClusterDetailResponse;
import org.management.service.dto.response.NamespaceDetail;
import org.management.service.dto.response.ResourceDetail;
import org.management.service.entity.ClusterResource;
import org.management.service.repository.ClusterResourceRepository;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

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

  public Optional<String> getClusterResourceYaml(UUID clusterId, String namespace, String kind, String resourceName) {
    List<ClusterResource> resources = clusterResourceRepository.findAllByClusterIdAndNamespaceAndKind(clusterId, namespace, kind);
    ObjectMapper mapper = new ObjectMapper();

    for (ClusterResource resource : resources) {
      try {
        Map<String, Object> yamlMap = mapper.readValue(resource.getYaml(), new TypeReference<Map<String, Object>>() {});
        if (yamlMap.containsKey("metadata") && ((Map<String, Object>) yamlMap.get("metadata")).get("name").equals(resourceName)) {
          return Optional.of(resource.getYaml());
        }
      } catch (JsonProcessingException e) {
        // Log the error or handle it as appropriate
        System.err.println("Error parsing YAML for resource: " + e.getMessage());
      }
    }
    return Optional.empty();
  }

  public ClusterDetailResponse getClusterDetails(UUID clusterId) {
    List<ClusterResource> allResources = clusterResourceRepository.findAllByClusterId(clusterId);
    ObjectMapper mapper = new ObjectMapper();

    Map<String, List<ClusterResource>> resourcesByNamespace = allResources.stream()
        .collect(Collectors.groupingBy(ClusterResource::getNamespace));

    List<NamespaceDetail> namespaceDetails = resourcesByNamespace.entrySet().stream()
        .map(entry -> {
          String namespace = entry.getKey();
          List<ClusterResource> resourcesInNamespace = entry.getValue();

          List<ResourceDetail> deployments = resourcesInNamespace.stream()
              .filter(r -> DEPLOYMENT_KIND.equals(r.getKind()))
              .map(r -> buildResourceDetail(mapper, r))
              .filter(Optional::isPresent)
              .map(Optional::get)
              .collect(Collectors.toList());

          List<ResourceDetail> services = resourcesInNamespace.stream()
              .filter(r -> SERVICE_KIND.equals(r.getKind()))
              .map(r -> buildResourceDetail(mapper, r))
              .filter(Optional::isPresent)
              .map(Optional::get)
              .collect(Collectors.toList());

          return NamespaceDetail.builder()
              .namespace(namespace)
              .deployments(deployments)
              .services(services)
              .build();
        })
        .collect(Collectors.toList());

    return ClusterDetailResponse.builder()
        .clusterId(clusterId)
        .namespaces(namespaceDetails)
        .build();
  }

  private Optional<ResourceDetail> buildResourceDetail(ObjectMapper mapper, ClusterResource resource) {
    try {
      Map<String, Object> yamlMap = mapper.readValue(resource.getYaml(), new TypeReference<Map<String, Object>>() {});
      String name = (String) ((Map<String, Object>) yamlMap.get("metadata")).get("name");
      return Optional.of(ResourceDetail.builder()
          .name(name)
          .kind(resource.getKind())
          .yaml(resource.getYaml())
          .build());
    } catch (JsonProcessingException | NullPointerException e) {
      System.err.println("Error processing resource for details: " + e.getMessage());
      return Optional.empty();
    }
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
