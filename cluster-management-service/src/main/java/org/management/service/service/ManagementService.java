package org.management.service.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.management.service.common.DataResponse;
import org.management.service.common.StatusResponse;
import org.management.service.dto.ContainerInfo;
import org.management.service.dto.request.ClusterResourceRequest;
import org.management.service.dto.request.Resource;
import org.management.service.dto.response.*;
import org.management.service.entity.ClusterCRD;
import org.management.service.entity.ClusterResource;
import org.management.service.repository.ClusterCRDRepository;
import org.management.service.repository.ClusterResourceRepository;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ManagementService {

  private static final String DEPLOYMENT_KIND = "Deployment";
  private static final String SERVICE_KIND = "Service";

  private final ClusterResourceRepository clusterResourceRepository;
  private final ClusterCRDRepository clusterCRDRepository;

  private final RedisTemplate<String, String> redisTemplate;
  private final ObjectMapper objectMapper = new ObjectMapper();

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

  public ClusterNamespacesResponse getClusterNamespaces(UUID clusterId) {
    List<String> namespaces = clusterResourceRepository.findDistinctNamespacesByClusterId(clusterId);
    return ClusterNamespacesResponse.of(namespaces);
  }

  public ClusterServicesResponse getServicesByNamespace(String namespace, UUID clusterId) {
    List<String> serviceNames = clusterResourceRepository.findServiceNamesByClusterIdAndNamespace(clusterId, namespace);
    return ClusterServicesResponse.of(serviceNames);
  }

  public DataResponse<DeploymentResponse> fetchContainers(UUID clusterId, String namespace, String serviceName) {
    List<ClusterResource> resources = clusterResourceRepository.findByNamespaceAndClusterId(namespace, clusterId);

    String selectorApp = null;

    // Step 1: Find Service and extract selector.app
    for (ClusterResource resource : resources) {
      if ("Service".equalsIgnoreCase(resource.getKind())) {
        try {
          JsonNode node = objectMapper.readTree(resource.getYaml());
          if (serviceName.equals(node.get("name").asText())) {
            JsonNode selector = node.get("selector");
            if (selector != null && selector.has("app")) {
              selectorApp = selector.get("app").asText();
              break;
            }
          }
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    }

    if (selectorApp == null) {
      return DataResponse.of(Collections.emptyList());
    }

    // Step 2: Find all matching Deployments
    List<DeploymentResponse> deployments = new ArrayList<>();

    for (ClusterResource resource : resources) {
      if ("Deployment".equalsIgnoreCase(resource.getKind())) {
        try {
          JsonNode node = objectMapper.readTree(resource.getYaml());
          JsonNode podLabels = node.get("podLabels");

          if (podLabels != null && selectorApp.equals(podLabels.get("app").asText())) {
            List<ContainerInfo> containers = new ArrayList<>();
            JsonNode containersNode = node.get("containers");
            if (containersNode != null && containersNode.isArray()) {
              for (JsonNode container : containersNode) {
                containers.add(new ContainerInfo(
                    container.get("name").asText(),
                    container.get("image").asText()
                ));
              }
            }

            Map<String, String> labelsMap = new HashMap<>();
            podLabels.fields().forEachRemaining(e -> labelsMap.put(e.getKey(), e.getValue().asText()));

            int replicas = node.has("replicas") ? node.get("replicas").asInt() : 1;

            deployments.add(new DeploymentResponse(
                node.get("name").asText(),
                containers,
                labelsMap,
                replicas
            ));
          }

        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    }

    return DataResponse.of(deployments);
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

  public String getClusterCRD(UUID clusterId) {
    ClusterCRD clusterCRD = clusterCRDRepository.findByClusterId(clusterId)
        .orElseThrow(null);

    return clusterCRD.getYaml();
  }
}
