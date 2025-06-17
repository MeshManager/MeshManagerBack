package com.istizo.crd_service.domain;

import com.istizo.crd_service.domain.enums.ServiceType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@Entity
@Table(name = "tb_service_entity")
public class ServiceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long istioRouteId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String namespace;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private ServiceType serviceType;

    @Column(nullable = true)
    private Integer ratio;

    @ElementCollection(fetch = FetchType.LAZY)
    private List<String> commitHashes;
}
