package com.istizo.crd_service.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "tb_dependency")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Dependency {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long serviceEntityId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String namespace;

    @Column(nullable = false)
    private String commitHash;
}
