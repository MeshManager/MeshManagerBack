package com.istizo.crd_service.service.CrdService;

import com.istizo.crd_service.web.dto.CrdRequestDTO;
import com.istizo.crd_service.web.dto.CrdResponseDTO;

import java.util.UUID;

public interface CrdService {
    CrdResponseDTO.toGetServiceEntityListDTO GetServiceEntityList(UUID uuid);
    CrdResponseDTO.toGetServiceEntityDTO GetServiceEntityDTO(Long serviceEntityID);
    CrdResponseDTO.toGetDependencyDTO GetDependencyDTO(Long dependencyID);
    CrdResponseDTO.toGetdarknessReleaseDTO GetDarknessReleaseDTO(Long darknessReleaseID);

    CrdResponseDTO.toResponseID CreateServiceEntity(UUID uuid, CrdRequestDTO.toCreateServiceEntityDTO serviceEntityDTO);
    CrdResponseDTO.toResponseID CreateDependency(UUID uuid, CrdRequestDTO.toCreateDependencyDTO dependencyDTO);
    CrdResponseDTO.toResponseID CreateDarknessRelease(UUID uuid, CrdRequestDTO.toCreateDarknessReleaseDTO DarknessReleaseDTO);

    CrdResponseDTO.toResponseID DeleteServiceEntity(Long serviceEntityID);
    CrdResponseDTO.toResponseID DeleteDependency(Long dependencyID);
    CrdResponseDTO.toResponseID DeleteDarknessRelease(Long darknessReleaseID);



}
