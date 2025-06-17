package com.istizo.crd_service.web.controller;


import com.istizo.crd_service.global.response.ResForm;
import com.istizo.crd_service.global.response.status.InSuccess;
import com.istizo.crd_service.service.CrdService.CrdService;
import com.istizo.crd_service.web.api.CrdApiSpecification;
import com.istizo.crd_service.web.dto.CrdRequestDTO;
import com.istizo.crd_service.web.dto.CrdResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class CrdController implements CrdApiSpecification {

    private final CrdService crdService;

    @Override
    public ResForm<CrdResponseDTO.toGetServiceEntityListDTO> getServiceEntityList(UUID uuid) {
        CrdResponseDTO.toGetServiceEntityListDTO serviceEntityListDTO = crdService.GetServiceEntityList(uuid);
        return ResForm.onSuccess(InSuccess.GET_SERVICE_ENTITY_LIST_SUCCESS, serviceEntityListDTO);
    }

    @Override
    public ResForm<CrdResponseDTO.toGetServiceEntityDTO> getServiceEntity(Long serviceEntityID) {
        CrdResponseDTO.toGetServiceEntityDTO serviceEntityDTO = crdService.GetServiceEntityDTO(serviceEntityID);
        return ResForm.onSuccess(InSuccess.GET_SERVICE_ENTITY_SUCCESS, serviceEntityDTO);
    }

    @Override
    public ResForm<CrdResponseDTO.toGetDependencyDTO> getDependency(Long dependencyID) {
        CrdResponseDTO.toGetDependencyDTO dependencyDTO = crdService.GetDependencyDTO(dependencyID);
        return ResForm.onSuccess(InSuccess.GET_DEPENDENCY_SUCCESS, dependencyDTO);
    }

    @Override
    public ResForm<CrdResponseDTO.toGetdarknessReleaseDTO> getdarknessRelease(Long darknessReleaseID) {
        CrdResponseDTO.toGetdarknessReleaseDTO darknessReleaseDTO = crdService.GetDarknessReleaseDTO(darknessReleaseID);
        return ResForm.onSuccess(InSuccess.GET_DARKNESSRELEASE_SUCCESS, darknessReleaseDTO);
    }

    @Override
    public ResForm<CrdResponseDTO.toResponseID> createServiceEntity(UUID uuid, CrdRequestDTO.toCreateServiceEntityDTO createServiceEntityDTO) {
        CrdResponseDTO.toResponseID createdServiceEntity = crdService.CreateServiceEntity(uuid, createServiceEntityDTO);
        return ResForm.onSuccess(InSuccess.POST_SERVICE_ENTITY_SUCCESS, createdServiceEntity);
    }

    @Override
    public ResForm<CrdResponseDTO.toResponseID> createDependency(UUID uuid, CrdRequestDTO.toCreateDependencyDTO createDependencyDTO) {
        CrdResponseDTO.toResponseID responseID = crdService.CreateDependency(uuid, createDependencyDTO);
        return ResForm.onSuccess(InSuccess.POST_DEPENDENCY_SUCCESS, responseID);
    }

    @Override
    public ResForm<CrdResponseDTO.toResponseID> createDarknessRelease(UUID uuid, CrdRequestDTO.toCreateDarknessReleaseDTO createDarknessReleaseDTO) {
        CrdResponseDTO.toResponseID responseID = crdService.CreateDarknessRelease(uuid, createDarknessReleaseDTO);
        return ResForm.onSuccess(InSuccess.POST_DARKNESSRELEASE_SUCCESS, responseID);
    }

    @Override
    public ResForm<CrdResponseDTO.toResponseID> deleteServiceEntity(Long serviceEntityID) {
        CrdResponseDTO.toResponseID responseID = crdService.DeleteServiceEntity(serviceEntityID);
        return ResForm.onSuccess(InSuccess.DELETE_SERVICE_ENTITY_SUCCESS, responseID);
    }

    @Override
    public ResForm<CrdResponseDTO.toResponseID> deleteDependency(Long dependencyID) {
        CrdResponseDTO.toResponseID responseID = crdService.DeleteDependency(dependencyID);
        return ResForm.onSuccess(InSuccess.DELETE_DEPENDENCY_SUCCESS, responseID);
    }

    @Override
    public ResForm<CrdResponseDTO.toResponseID> deleteDarknessRelease(Long darknessReleaseID) {
        CrdResponseDTO.toResponseID responseID = crdService.DeleteDependency(darknessReleaseID);
        return ResForm.onSuccess(InSuccess.DELETE_DARKNESSRELEASE_SUCCESS, responseID);
    }
}
