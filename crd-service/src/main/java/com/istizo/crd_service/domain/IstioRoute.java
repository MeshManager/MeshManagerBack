package com.istizo.crd_service.domain;

import com.istizo.crd_service.domain.enums.ServiceType;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.*;

@Entity
@Table(name = "tb_istio_route")
public class IstioRoute {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String clusterName;
}
