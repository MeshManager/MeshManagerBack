package org.istizo.clusterservice.repository;

import org.istizo.clusterservice.dto.response.ClusterListResponse;
import org.istizo.clusterservice.entity.Cluster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface ClusterRepository extends JpaRepository<Cluster, UUID> {

  @Query("SELECT new org.istizo.clusterservice.dto.response.ClusterListResponse(c.uuid, c.name) " +
      "FROM Cluster c WHERE c.userId = :userId")
  List<ClusterListResponse> findClustersByUserId(@Param("userId") Long userId);
}
