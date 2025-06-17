package com.istizo.crd_service.web.controller;


import com.istizo.crd_service.global.response.ResForm;
import com.istizo.crd_service.web.api.CrdApiSpecification;
import com.istizo.crd_service.web.dto.crdRequestDTO;
import com.istizo.crd_service.web.dto.crdResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CrdController implements CrdApiSpecification {

    @Override
    public ResForm<crdResponseDTO.toGetServiceEntityListDTO> getServiceEntityList(Long uuid) {
        return null;
    }

    @Override
    public ResForm<crdResponseDTO.toGetServiceEntityDTO> getServiceEntity(Long serviceEntityID) {
        return null;
    }

    @Override
    public ResForm<crdResponseDTO.toGetServiceEntityDTO> getDependency(Long dependencyID) {
        return null;
    }

    @Override
    public ResForm<crdResponseDTO.toGetServiceEntityDTO> getdarknessRelease(Long darknessReleaseID) {
        return null;
    }

    @Override
    public ResForm<crdResponseDTO.toResponseID> createIstioRoute(crdRequestDTO.toRegistorIstioRouteDTO toRegistorIstioRouteDTO) {
        return null;
    }

    @Override
    public ResForm<crdResponseDTO.toResponseID> createServiceEntity(crdRequestDTO.toCreateServiceEntityDTO createServiceEntityDTO) {
        return null;
    }

    @Override
    public ResForm<crdResponseDTO.toResponseID> createDependency(crdRequestDTO.toCreateDependencyDTO createDependencyDTO) {
        return null;
    }

    @Override
    public ResForm<crdResponseDTO.toResponseID> createDarknessRelease(crdRequestDTO.toCreatedarknessReleaseDTO createDarknessReleaseDTO) {
        return null;
    }

    @Override
    public ResForm<crdResponseDTO.toGetServiceEntityDTO> deleteServiceEntity(Long serviceEntityID) {
        return null;
    }

    @Override
    public ResForm<crdResponseDTO.toGetServiceEntityDTO> deleteDependency(Long dependencyID) {
        return null;
    }

    @Override
    public ResForm<crdResponseDTO.toGetServiceEntityDTO> deleteDarknessRelease(Long darknessReleaseID) {
        return null;
    }
}
