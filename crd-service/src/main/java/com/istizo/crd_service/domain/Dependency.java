package com.istizo.crd_service.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "dependency")
public class Dependency {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "service_id", nullable = false)
    private ServiceEntity service;

    private String name;
    private String namespace;
}
