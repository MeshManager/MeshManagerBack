package org.istizo.canaryservice.repository;

import org.istizo.canaryservice.entity.ClusterCRD;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ClusterCRDRepository extends JpaRepository<ClusterCRD, Long> {

  Optional<ClusterCRD> findByClusterId(UUID clusterId);
}
