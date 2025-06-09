package org.management.service.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

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

  private UUID clusterId;

  private String namespace;

  private String kind;

  private String yaml;

  private boolean changed;

  public static ClusterResource create(
      String clusterId, String namespace, String kind, String yaml
  ) {
    return ClusterResource.builder()
        .clusterId(UUID.fromString(clusterId))
        .namespace(namespace)
        .kind(kind)
        .yaml(yaml)
        .changed(false)
        .build();
  }

  public void updateChanged() {
    this.changed = false;
  }
}
