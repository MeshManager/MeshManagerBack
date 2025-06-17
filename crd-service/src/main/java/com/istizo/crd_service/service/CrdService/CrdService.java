package com.istizo.crd_service.service.CrdService;

import com.istizo.crd_service.web.dto.CrdRequestDTO;
import com.istizo.crd_service.web.dto.CrdResponseDTO;

import java.util.UUID;

public interface CrdService {
    CrdResponseDTO.toGetServiceEntityListDTO GetServiceEntityList(UUID uuid);
    CrdResponseDTO.toGetServiceEntityDTO GetServiceEntityDTO(Long serviceEntityID);
    CrdResponseDTO.toGetDependencyDTO GetDependencyDTO(Long dependencyID);
    CrdResponseDTO.toGetdarknessReleaseDTO GetDarknessReleaseDTO(Long darknessReleaseID);
    CrdResponseDTO.toResponseDependantID GetDependantID(Long serviceEntityID);

    CrdResponseDTO.toResponseID CreateServiceEntity(CrdRequestDTO.toCreateServiceEntityDTO serviceEntityDTO);
    CrdResponseDTO.toResponseID CreateDependency(CrdRequestDTO.toCreateDependencyDTO dependencyDTO);
    CrdResponseDTO.toResponseID CreateDarknessRelease(CrdRequestDTO.toCreateDarknessReleaseDTO DarknessReleaseDTO);

    CrdResponseDTO.toResponseID DeleteServiceEntity();
    CrdResponseDTO.toResponseID DeleteDependency();
    CrdResponseDTO.toResponseID DeleteDarknessRelease();



}
