package org.istizo.clusterservice.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Getter
@Builder
public class ClusterDetailResponse {
    private UUID uuid;
    private String name;
    private String prometheusUrl;
    private String token; // 보안상 실제 서비스에서는 토큰을 직접 노출하지 않을 수 있음 (예: 마스킹)

    // Kubernetes 관련 상세 정보 필드 (예시)
    private String namespace;
    private List<ContainerInfo> containers;
    private Map<String, String> podLabels;
    private Integer replicaCount; // 배포 또는 스테이트풀셋의 replicas
    private List<ServiceInfo> services;

    @Getter
    @Builder
    public static class ContainerInfo {
        private String name;
        private String image;
    }

    @Getter
    @Builder
    public static class ServiceInfo {
        private String name;
        private String clusterIp;
        private String type;
        private Map<String, String> selector;
    }
} 