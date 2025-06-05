package org.management.service.entity;

import jakarta.persistence.*;
import lombok.*;

@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class ClusterResource {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "cluster_resource_id", nullable = false, updatable = false)
  private Long id;

  private Long clusterId;

  private String namespace;

  private String kind;

  private String yaml;

  public static ClusterResource create(
      String clusterId, String namespace, String kind, String yaml
  ) {
    return ClusterResource.builder()
        .clusterId(Long.valueOf(clusterId))
        .namespace(namespace)
        .kind(kind)
        .yaml(yaml)
        .build();
  }
}
