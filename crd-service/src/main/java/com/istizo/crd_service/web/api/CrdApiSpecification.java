package com.istizo.crd_service.web.api;
import com.istizo.crd_service.global.response.ResForm;
import com.istizo.crd_service.web.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@Tag(name = "CRD API", description = "Agent CRD 관련 API")
@RestController
@RequestMapping("/crd/api/v1")
@Validated
public interface CrdApiSpecification {

    @Operation(summary = "현재 배포되고 있는 Service Entity 목록 조회 API", description = "uuid를 이용해 현재 배포되고 있는 Service Entity ID 목록을 조회합니다.")
    @GetMapping("/{uuid}/list")
    ResForm<CrdResponseDTO.toGetServiceEntityListDTO> getServiceEntityList(@PathVariable("uuid") Long uuid);

    @Operation(summary = "현재 배포되고 있는 Service Entity 조회 API", description = "serviceEntityID를 이용해 현재 배포되고 있는 Service Entity 정보를 조회합니다.")
    @GetMapping("/{serviceEntityID}")
    ResForm<CrdResponseDTO.toGetServiceEntityDTO> getServiceEntity(@PathVariable("serviceEntityID") Long serviceEntityID);

    @Operation(summary = "현재 배포되고 있는 dependency 조회 API", description = "dependency ID를 이용해 현재 배포되고 있는 dependency 정보를 조회합니다.")
    @GetMapping("/{dependencyID}")
    ResForm<CrdResponseDTO.toGetDependencyDTO> getDependency(@PathVariable("dependencyID") Long dependencyID);

    @Operation(summary = "현재 배포되고 있는 darknessRelease 조회 API", description = "darknessRelease ID를 이용해 현재 배포되고 있는 darknessRelease 정보를 조회합니다.")
    @GetMapping("/{darknessReleaseID}")
    ResForm<CrdResponseDTO.toGetdarknessReleaseDTO> getdarknessRelease(@PathVariable("darknessReleaseID") Long darknessReleaseID);

    @Operation(summary = "serviceEntity 생성 API", description = "UUID를 이용해 serviceEntity를 생성합니다.")
    @PostMapping("/{uuid}/serviceEntity")
    ResForm<CrdResponseDTO.toResponseID> createServiceEntity(
            @PathVariable("uuid") UUID uuid,
            @Valid @RequestBody CrdRequestDTO.toCreateServiceEntityDTO createServiceEntityDTO);

    @Operation(summary = "dependency 생성 API", description = "UUID를 이용해 dependency를 생성합니다.")
    @PostMapping("/{uuid}/dependency")
    ResForm<CrdResponseDTO.toResponseID> createDependency(
            @PathVariable("uuid") UUID uuid,
            @Valid @RequestBody CrdRequestDTO.toCreateDependencyDTO createDependencyDTO);

    @Operation(summary = "darknessRelease 생성 API", description = "UUID를 이용해 darknessRelease를 생성합니다.")
    @PostMapping("/{uuid}/darknessRelease")
    ResForm<CrdResponseDTO.toResponseID> createDarknessRelease(
            @PathVariable("uuid") UUID uuid,
            @Valid @RequestBody CrdRequestDTO.toCreateDarknessReleaseDTO createDarknessReleaseDTO);

    @Operation(summary = "현재 배포되고 있는 Service Entity 삭제 API", description = "serviceEntityID를 이용해 현재 배포되고 있는 Service Entity 정보를 삭제합니다.")
    @DeleteMapping("/{serviceEntityID}")
    ResForm<CrdResponseDTO.toGetServiceEntityDTO> deleteServiceEntity(
            @PathVariable("serviceEntityID") Long serviceEntityID);

    @Operation(summary = "현재 배포되고 있는 dependency 삭제 API", description = "dependency ID를 이용해 현재 배포되고 있는 Service Entity 정보를 삭제합니다.")
    @DeleteMapping("/{dependencyID}")
    ResForm<CrdResponseDTO.toGetServiceEntityDTO> deleteDependency(
            @PathVariable("dependencyID") Long dependencyID);

    @Operation(summary = "현재 배포되고 있는 darknessRelease 삭제 API", description = "darknessRelease ID를 이용해 현재 배포되고 있는 Service Entity 정보를 삭제합니다.")
    @DeleteMapping("/{darknessReleaseID}")
    ResForm<CrdResponseDTO.toGetServiceEntityDTO> deleteDarknessRelease(
            @PathVariable("darknessReleaseID") Long darknessReleaseID);

}
