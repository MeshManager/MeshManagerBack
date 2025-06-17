package com.istizo.crd_service.web.dto;

import com.istizo.crd_service.domain.enums.ServiceType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

public class CrdResponseDTO {

    @Getter
    @Builder
    @Schema(description = "제어하고 있는 서비스 ID 목록 조회 결과 반환 DTO")
    public static class toGetServiceEntityListDTO {
        @Schema(description = "제어하고 있는 서비스 ID 목록", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false)
        private List<Long> serviceEntityID;
    }

    @Getter
    @Builder
    @Schema(description = "제어하고 있는 서비스 ID 상세 조회 결과 반환 DTO")
    public static class toGetServiceEntityDTO {
        @Schema(description = "해당 서비스의 이름", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false)
        private String name;

        @Schema(description = "해당 서비스가 배포된 NS", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false)
        private String namespace;

        @Schema(description = "해당 서비스의 TYPE", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false)
        private ServiceType serviceType;

        @Schema(description = "카나리 배포 시 비율 (없으면 null 반환)", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false)
        private Integer ratio;

        @Schema(description = "svc의 버전 정보", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false)
        private List<String> commitHash;

        @Schema(description = "darknessRelease ID (없으면 null 반환)", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true)
        private Long darknessReleaseID;

        @Schema(description = "dependency ID 목록(없으면 null 반환)", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true)
        private List<Long> dependencyID;
    }

    @Getter
    @Builder
    @Schema(description = "dependency ID 상세 조회 결과 반환 DTO")
    public static class toGetDependencyDTO {
        @Schema(description = "dependency가 종속된 서비스의 ID", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false)
        private Long serviceEntityId;

        @Schema(description = "dependency가 있는 서비스의 이름", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false)
        private String name;

        @Schema(description = "dependency가 있는 서비스의 네임스페이스", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false)
        private String namespace;

        @Schema(description = "dependency가 있는 서비스의 버전", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false)
        private String commitHash;
    }

    @Getter
    @Builder
    @Schema(description = "darknessRelease ID 상세 조회 결과 반환 DTO")
    public static class toGetdarknessReleaseDTO {
        @Schema(description = "darknessRelease가 종속된 서비스의 ID", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false)
        private Long serviceEntityId;

        @Schema(description = "darknessRelease 설정에서 보낼 버전", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false)
        private String commitHash;

        @Schema(description = "조건 리스트 [ip]", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false)
        private List<String> ips;
    }

    @Getter
    @Builder
    @Schema(description = "생성된 Resource의 ID 반환")
    public static class toResponseID {
        @Schema(description = "생성된 리소스의 ID", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false)
        private Long ID;
    }

    @Getter
    @Builder
    @Schema(description = "생성된 ServiceEntity가 가지고 있는 dependency 및 darknessRelease ID 반환")
    public static class toResponseDependantID {
        @Schema(description = "생성된 리소스의 dependency ID", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true)
        private List<Long> dependencyID;

        @Schema(description = "생성된 리소스의 darknessRelease ID", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true)
        private List<Long> darknessReleaseID;
    }

}
