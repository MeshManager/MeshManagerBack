package org.management.service.repository;

import org.management.service.entity.ClusterResource;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClusterResourceRepository extends JpaRepository<ClusterResource, Long> {

  void deleteAllByClusterId(Long clusterId);

  List<ClusterResource> findAllByClusterIdAndChangedIsTrue(Long clusterId);
}
