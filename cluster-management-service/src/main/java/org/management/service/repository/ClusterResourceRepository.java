package org.management.service.repository;

import io.lettuce.core.dynamic.annotation.Param;
import org.management.service.entity.ClusterResource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface ClusterResourceRepository extends JpaRepository<ClusterResource, Long> {

  void deleteAllByClusterId(UUID clusterId);

  List<ClusterResource> findAllByClusterIdAndChangedIsTrue(UUID clusterId);

  @Query(value = """
    SELECT DISTINCT namespace
    FROM cluster_resource
    WHERE cluster_id = :clusterId
  """, nativeQuery = true)
  List<String> findDistinctNamespacesByClusterId(@Param("clusterId") UUID clusterId);

  @Query(value = """
    SELECT JSON_UNQUOTE(JSON_EXTRACT(yaml, '$.name'))
    FROM cluster_resource
    WHERE kind = 'Service'
      AND cluster_id = :clusterId
      AND namespace = :namespace
  """, nativeQuery = true)
  List<String> findServiceNamesByClusterIdAndNamespace(
      @Param("clusterId") UUID clusterId,
      @Param("namespace") String namespace
  );

  List<ClusterResource> findByNamespaceAndClusterId(String namespace, UUID clusterId);
}
