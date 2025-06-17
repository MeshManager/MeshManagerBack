package com.istizo.crd_service.service.CrdService;

import com.istizo.crd_service.repository.DarknessReleaseRepository;
import com.istizo.crd_service.repository.DependencyRepository;
import com.istizo.crd_service.repository.ServiceEntityRepository;
import com.istizo.crd_service.web.dto.CrdRequestDTO;
import com.istizo.crd_service.web.dto.CrdResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class CrdServiceImpl implements CrdService {
    private final DarknessReleaseRepository darknessReleaseRepository;
    private final DependencyRepository dependencyRepository;
    private final ServiceEntityRepository serviceEntityRepository;

    @Override
    public CrdResponseDTO.toGetServiceEntityListDTO GetServiceEntityList(UUID uuid) {
        return null;
    }

    @Override
    public CrdResponseDTO.toGetServiceEntityDTO GetServiceEntityDTO(Long serviceEntityID) {
        return null;
    }

    @Override
    public CrdResponseDTO.toGetDependencyDTO GetDependencyDTO(Long dependencyID) {
        return null;
    }

    @Override
    public CrdResponseDTO.toGetdarknessReleaseDTO GetDarknessReleaseDTO(Long darknessReleaseID) {
        return null;
    }

    @Override
    public CrdResponseDTO.toResponseDependantID GetDependantID(Long serviceEntityID) {
        return null;
    }

    @Override
    public CrdResponseDTO.toResponseID CreateServiceEntity(CrdRequestDTO.toCreateServiceEntityDTO serviceEntityDTO) {
        return null;
    }

    @Override
    public CrdResponseDTO.toResponseID CreateDependency(CrdRequestDTO.toCreateDependencyDTO dependencyDTO) {
        return null;
    }

    @Override
    public CrdResponseDTO.toResponseID CreateDarknessRelease(CrdRequestDTO.toCreateDarknessReleaseDTO DarknessReleaseDTO) {
        return null;
    }

    @Override
    public CrdResponseDTO.toResponseID DeleteServiceEntity() {
        return null;
    }

    @Override
    public CrdResponseDTO.toResponseID DeleteDependency() {
        return null;
    }

    @Override
    public CrdResponseDTO.toResponseID DeleteDarknessRelease() {
        return null;
    }
}
