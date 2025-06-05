package org.agent.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "clusters")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cluster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cluster_name", nullable = false)
    private String clusterName;

    @Column(name = "prometheus_url", nullable = false)
    private String prometheusUrl;

    @Column(name = "token", nullable = false)
    private String token;
} 