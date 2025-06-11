package org.istizo.canaryservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity(name = "cluster_crd")
public class ClusterCRD {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "cluster_crd_id", nullable = false, updatable = false)
  private Long id;

  @Column(nullable = false)
  private UUID clusterId;

  @Column(columnDefinition = "TEXT", nullable = false)
  private String yaml;

  public static ClusterCRD create(UUID clusterId, String yaml) {
    return ClusterCRD.builder()
        .clusterId(clusterId)
        .yaml(yaml)
        .build();
  }
}
