package org.istizo.clusterservice.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Cluster {

  @Id
  @GeneratedValue(generator = "UUID")
  @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
  @Column(name = "cluster_id", updatable = false, nullable = false, columnDefinition = "BINARY(16)")
  private UUID uuid;

  @Column(name = "user_id", nullable = false, unique = true)
  private Long userId;

  @Column(nullable = false, unique = true)
  private String name;

  @Column(nullable = false)
  private String token;

  @Column(name = "prometheus_url")
  private String prometheusUrl;

  public static Cluster create(String name, String token, String prometheusUrl) {
    return Cluster.builder()
        .userId(1L)
        .name(name)
        .token(token)
        .prometheusUrl(prometheusUrl)
        .build();
  }
}