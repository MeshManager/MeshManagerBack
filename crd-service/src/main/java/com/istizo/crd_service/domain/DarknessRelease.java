package com.istizo.crd_service.domain;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "darkness_release")
public class DarknessRelease {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "istio_route_id", nullable = false)
    private IstioRoute istioRoute;

    private String commitHash;

    @Column(columnDefinition = "text[]")
    private List<String> ips;
}
