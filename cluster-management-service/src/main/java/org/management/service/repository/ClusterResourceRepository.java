package org.management.service.repository;

import org.management.service.entity.ClusterResource;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClusterResourceRepository extends JpaRepository<ClusterResource, Long> {

  void deleteAllByClusterId(Long clusterId);
}
