package com.istizo.crd_service.web.dto;

import com.istizo.crd_service.domain.enums.ServiceType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CrdRequestDTO {

    @Getter
    @Schema(description = "Cluster 등록 DTO")
    public static class toRegistorIstioRouteDTO {
        @Schema(description = "해당 cluster의 uuid", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false)
        private UUID uuid;
    }

    @Getter
    @Schema(description = "제어하고 하고 싶은 서비스 추가 DTO")
    public static class toCreateServiceEntityDTO {
        @Schema(description = "해당 서비스의 이름", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false)
        private String name;

        @Schema(description = "해당 서비스가 배포된 NS", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false)
        private String namespace;

        @Schema(description = "해당 서비스의 TYPE", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false)
        private ServiceType serviceType;

        @Schema(description = "카나리 배포 시 비율 (없으면 null 반환)", requiredMode = Schema.RequiredMode.REQUIRED, nullable = true)
        private Integer ratio;

        @Schema(description = "관리할 버전 리스트", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false)
        private List<String> commitHash;

        @Schema(description = "예약된 ratio 업데이트 목록", requiredMode = Schema.RequiredMode.NOT_REQUIRED, nullable = true)
        private final List<RatioScheduleDTO> ratioSchedules = new ArrayList<>();

        @Getter
        public static class RatioScheduleDTO {
            @Schema(description = "업데이트 지연 시간(ms)", requiredMode = Schema.RequiredMode.REQUIRED)
            private Long delayMs;

            @Schema(description = "설정할 새로운 ratio 값", requiredMode = Schema.RequiredMode.REQUIRED)
            private Integer newRatio;
        }
    }

    @Getter
    @Schema(description = "dependency 추가 DTO")
    public static class toCreateDependencyDTO {
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
    @Schema(description = "darknessRelease 추가 DTO")
    public static class toCreateDarknessReleaseDTO {
        @Schema(description = "darknessRelease가 종속된 서비스의 ID", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false)
        private Long serviceEntityId;

        @Schema(description = "darknessRelease 설정에서 보낼 버전", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false)
        private String commitHash;

        @Schema(description = "조건 리스트 [ip]", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false)
        private List<String> ips;
    }

}
