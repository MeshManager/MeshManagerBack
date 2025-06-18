package org.agent.service.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Builder(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Agent {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "agent_id", nullable = false, updatable = false)
  private Long id;

  @Column(nullable = false, unique = true)
  private String name;

  @Column(name = "cluster_id", nullable = false, unique = true)
  private UUID clusterId;

  @Column(nullable = false)
  private boolean connected;

  public static Agent create(String name, UUID clusterId) {
    return Agent.builder()
        .name(name)
        .clusterId(clusterId)
        .connected(true)
        .build();
  }
}
