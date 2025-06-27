package com.istizo.crd_service.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tb_ratio_schedule")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RatioSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_entity_id", nullable = false)
    private ServiceEntity serviceEntity;

    @Column(nullable = false)
    private Long triggerTime;

    @Column(nullable = false)
    private Integer newRatio;
}
