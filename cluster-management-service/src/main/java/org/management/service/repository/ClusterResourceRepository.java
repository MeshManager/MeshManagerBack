package org.management.service.repository;

import org.management.service.entity.ClusterResource;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ClusterResourceRepository extends JpaRepository<ClusterResource, Long> {

  void deleteAllByClusterId(UUID clusterId);

  List<ClusterResource> findAllByClusterIdAndChangedIsTrue(UUID clusterId);
}
