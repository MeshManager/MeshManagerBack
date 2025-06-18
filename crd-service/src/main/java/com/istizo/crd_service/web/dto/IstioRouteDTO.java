package com.istizo.crd_service.web.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IstioRouteDTO {
    private String apiVersion;
    private String kind;
    private Metadata metadata;
    private IstioRouteSpec spec;

    @Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
    public static class Metadata {
        private String name;
        private String namespace;
    }

    @Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
    public static class IstioRouteSpec {
        private List<ServiceConfig> services;
    }

    @Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
    public static class ServiceConfig {
        private String name;
        private String namespace;
        private String type;
        private List<String> commitHashes;
        private Integer ratio;
        @JsonInclude(JsonInclude.Include.NON_EMPTY)
        private List<DependencyDTO> dependencies;
        @JsonInclude(JsonInclude.Include.NON_EMPTY)
        private List<DarknessReleaseDTO> darknessReleases;
    }

    @Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
    public static class DependencyDTO {
        private String name;
        private String namespace;
        private List<String> commitHashes;
    }

    @Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
    public static class DarknessReleaseDTO {
        private String commitHash;
        private List<String> ips;
    }
}
