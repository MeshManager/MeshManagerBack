package org.agent.service.entity;

import jakarta.persistence.*;
import lombok.*;

@Builder(access = AccessLevel.PRIVATE)
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

  public static Agent create(String name) {
    return Agent.builder()
        .name(name)
        .build();
  }
}
