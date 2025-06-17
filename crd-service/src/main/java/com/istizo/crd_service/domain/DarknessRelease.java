package com.istizo.crd_service.domain;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "tb_darkness_release")
public class DarknessRelease {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long serviceEntityId;

    @Column(nullable = false)
    private String commitHash;

    @ElementCollection(fetch = FetchType.LAZY)
    private List<String> ips;
}
