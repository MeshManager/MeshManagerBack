package com.istizo.crd_service.web.controller;


import com.istizo.crd_service.global.response.ResForm;
import com.istizo.crd_service.web.api.CrdApiSpecification;
import com.istizo.crd_service.web.dto.CrdRequestDTO;
import com.istizo.crd_service.web.dto.CrdResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class CrdController implements CrdApiSpecification {

    @Override
    public ResForm<CrdResponseDTO.toGetServiceEntityListDTO> getServiceEntityList(Long uuid) {
        return null;
    }

    @Override
    public ResForm<CrdResponseDTO.toGetServiceEntityDTO> getServiceEntity(Long serviceEntityID) {
        return null;
    }

    @Override
    public ResForm<CrdResponseDTO.toGetDependencyDTO> getDependency(Long dependencyID) {
        return null;
    }

    @Override
    public ResForm<CrdResponseDTO.toGetdarknessReleaseDTO> getdarknessRelease(Long darknessReleaseID) {
        return null;
    }

    @Override
    public ResForm<CrdResponseDTO.toResponseID> createServiceEntity(UUID uuid, CrdRequestDTO.toCreateServiceEntityDTO createServiceEntityDTO) {
        return null;
    }

    @Override
    public ResForm<CrdResponseDTO.toResponseID> createDependency(UUID uuid, CrdRequestDTO.toCreateDependencyDTO createDependencyDTO) {
        return null;
    }

    @Override
    public ResForm<CrdResponseDTO.toResponseID> createDarknessRelease(UUID uuid, CrdRequestDTO.toCreateDarknessReleaseDTO createDarknessReleaseDTO) {
        return null;
    }

    @Override
    public ResForm<CrdResponseDTO.toGetServiceEntityDTO> deleteServiceEntity(Long serviceEntityID) {
        return null;
    }

    @Override
    public ResForm<CrdResponseDTO.toGetServiceEntityDTO> deleteDependency(Long dependencyID) {
        return null;
    }

    @Override
    public ResForm<CrdResponseDTO.toGetServiceEntityDTO> deleteDarknessRelease(Long darknessReleaseID) {
        return null;
    }
}
