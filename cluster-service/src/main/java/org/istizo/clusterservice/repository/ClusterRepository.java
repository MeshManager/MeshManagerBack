package org.istizo.clusterservice.repository;

import org.istizo.clusterservice.entity.Cluster;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ClusterRepository extends JpaRepository<Cluster, UUID> {
}
