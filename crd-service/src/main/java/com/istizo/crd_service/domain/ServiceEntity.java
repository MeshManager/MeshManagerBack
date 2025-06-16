package com.istizo.crd_service.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "service")
public class ServiceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "istio_route_id", nullable = false)
    private IstioRoute istioRoute;

    private String name;
    private String namespace;
    private String type;
    private Integer ratio;
}
