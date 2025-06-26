package com.istizo.crd_service.service.CrdService;

import com.istizo.crd_service.domain.DarknessRelease;
import com.istizo.crd_service.domain.Dependency;
import com.istizo.crd_service.domain.RatioSchedule;
import com.istizo.crd_service.domain.ServiceEntity;
import com.istizo.crd_service.global.exception.CustomException;
import com.istizo.crd_service.global.response.status.ErrorStatus;
import com.istizo.crd_service.repository.DarknessReleaseRepository;
import com.istizo.crd_service.repository.DependencyRepository;
import com.istizo.crd_service.repository.RatioScheduleRepository;
import com.istizo.crd_service.repository.ServiceEntityRepository;
import com.istizo.crd_service.web.dto.CrdRequestDTO;
import com.istizo.crd_service.web.dto.CrdResponseDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CrdServiceImpl implements CrdService {
    private final DarknessReleaseRepository darknessReleaseRepository;
    private final DependencyRepository dependencyRepository;
    private final ServiceEntityRepository serviceEntityRepository;
    private final RatioScheduleRepository ratioScheduleRepository;
    private final RestClient.Builder builder;

    @Override
    public CrdResponseDTO.toGetServiceEntityListDTO GetServiceEntityList(UUID uuid) {
        List<Long> ids = serviceEntityRepository.findIdByUuid(uuid);
        if (ids == null || ids.isEmpty()) {
            throw new CustomException(ErrorStatus.SERVICE_ENTITY_NOT_FOUND);
        }
        return CrdResponseDTO.toGetServiceEntityListDTO.builder()
                .serviceEntityID(ids)
                .build();
    }

    @Override
    @Transactional
    public CrdResponseDTO.toGetServiceEntityDTO GetServiceEntityDTO(Long serviceEntityID) {
        ServiceEntity entity = serviceEntityRepository.findById(serviceEntityID)
                .orElseThrow(() -> new CustomException(ErrorStatus.SERVICE_ENTITY_NOT_FOUND));

        List<RatioSchedule> schedules = ratioScheduleRepository.findByServiceEntityId(entity.getId());
        List<CrdResponseDTO.toGetServiceEntityDTO.RatioScheduleInfo> scheduleInfos = schedules.stream()
                .map(s -> CrdResponseDTO.toGetServiceEntityDTO.RatioScheduleInfo.builder()
                        .triggerTime(s.getTriggerTime())
                        .newRatio(s.getNewRatio())
                        .build())
                .toList();

        Long darknessReleaseId = darknessReleaseRepository.findDarknessReleaseIdByServiceEntityId(entity.getId());

        List<Long> dependencyIds = dependencyRepository.findDependenciesIdByServiceEntityId(entity.getId());

        return CrdResponseDTO.toGetServiceEntityDTO.builder()
                .name(entity.getName())
                .namespace(entity.getNamespace())
                .serviceType(entity.getServiceType())
                .ratio(entity.getRatio())
                .commitHash(entity.getCommitHashes())
                .darknessReleaseID(darknessReleaseId)
                .dependencyID(dependencyIds)
                .ratioSchedules(scheduleInfos)
                .build();
    }

    @Override
    public CrdResponseDTO.toGetDependencyDTO GetDependencyDTO(Long dependencyID) {
        Dependency dependency = dependencyRepository.findById(dependencyID)
                .orElseThrow(() -> new CustomException(ErrorStatus.DEPENDENCY_NOT_FOUND));
        return CrdResponseDTO.toGetDependencyDTO.builder()
                .serviceEntityId(dependency.getServiceEntityId())
                .name(dependency.getName())
                .namespace(dependency.getNamespace())
                .commitHash(dependency.getCommitHash())
                .build();
    }

    @Override
    public CrdResponseDTO.toGetdarknessReleaseDTO GetDarknessReleaseDTO(Long darknessReleaseID) {
        DarknessRelease dr = darknessReleaseRepository.findById(darknessReleaseID)
                .orElseThrow(() -> new CustomException(ErrorStatus.DARKNESS_RELEASE_NOT_FOUND));
        return CrdResponseDTO.toGetdarknessReleaseDTO.builder()
                .serviceEntityId(dr.getServiceEntityId())
                .commitHash(dr.getCommitHash())
                .ips(dr.getIps())
                .build();
    }


    @Override
    public CrdResponseDTO.toResponseID CreateServiceEntity(UUID uuid, CrdRequestDTO.toCreateServiceEntityDTO serviceEntityDTO) {
        ServiceEntity entity = ServiceEntity.builder()
                .uuid(uuid)
                .name(serviceEntityDTO.getName())
                .namespace(serviceEntityDTO.getNamespace())
                .serviceType(serviceEntityDTO.getServiceType())
                .ratio(serviceEntityDTO.getRatio())
                .commitHashes(serviceEntityDTO.getCommitHash())
                .build();
        ServiceEntity serviceEntity = serviceEntityRepository.save(entity);

        // 2. RatioSchedule 처리 (Optional)
        List<CrdRequestDTO.toCreateServiceEntityDTO.RatioScheduleDTO> schedules = Optional.ofNullable(serviceEntityDTO.getRatioSchedules())
                .orElse(Collections.emptyList());

        if (!schedules.isEmpty()) {
            long baseTime = System.currentTimeMillis();

            for (CrdRequestDTO.toCreateServiceEntityDTO.RatioScheduleDTO scheduleDTO : schedules) {
                RatioSchedule schedule = RatioSchedule.builder()
                        .serviceEntity(serviceEntity)
                        .triggerTime(baseTime + scheduleDTO.getDelayMs())
                        .newRatio(scheduleDTO.getNewRatio())
                        .build();
                ratioScheduleRepository.save(schedule);
            }
        }

        System.out.println("Saved Entity ID: " + serviceEntity.getId());

        return CrdResponseDTO.toResponseID.builder()
                .ID(serviceEntity.getId())
                .build();
    }

    @Override
    public CrdResponseDTO.toResponseID CreateDependency(UUID uuid, CrdRequestDTO.toCreateDependencyDTO dependencyDTO) {
        Dependency dependency = Dependency.builder()
                .serviceEntityId(dependencyDTO.getServiceEntityId())
                .name(dependencyDTO.getName())
                .namespace(dependencyDTO.getNamespace())
                .commitHash(dependencyDTO.getCommitHash())
                .build();
        Dependency dependencyResult = dependencyRepository.save(dependency);
        return CrdResponseDTO.toResponseID.builder()
                .ID(dependencyResult.getId())
                .build();
    }

    @Override
    public CrdResponseDTO.toResponseID CreateDarknessRelease(UUID uuid, CrdRequestDTO.toCreateDarknessReleaseDTO darknessReleaseDTO) {
        DarknessRelease dr = DarknessRelease.builder()
                .serviceEntityId(darknessReleaseDTO.getServiceEntityId())
                .commitHash(darknessReleaseDTO.getCommitHash())
                .ips(darknessReleaseDTO.getIps())
                .build();
        DarknessRelease drResult = darknessReleaseRepository.save(dr);
        return CrdResponseDTO.toResponseID.builder()
                .ID(drResult.getId())
                .build();
    }

    @Override
    public CrdResponseDTO.toResponseID DeleteServiceEntity(Long serviceEntityId) {
        ServiceEntity entity = serviceEntityRepository.findById(serviceEntityId)
                .orElseThrow(() -> new RuntimeException("ServiceEntity not found"));

        ratioScheduleRepository.deleteByServiceEntityId(serviceEntityId);

        serviceEntityRepository.delete(entity);
        return CrdResponseDTO.toResponseID.builder()
                .ID(entity.getId())
                .build();
    }

    @Override
    public CrdResponseDTO.toResponseID DeleteDependency(Long dependencyId) {
        Dependency dependency = dependencyRepository.findById(dependencyId)
                .orElseThrow(() -> new RuntimeException("Dependency not found"));
        dependencyRepository.delete(dependency);
        return CrdResponseDTO.toResponseID.builder()
                .ID(dependency.getId())
                .build();
    }

    @Override
    public CrdResponseDTO.toResponseID DeleteDarknessRelease(Long darknessReleaseId) {
        DarknessRelease dr = darknessReleaseRepository.findById(darknessReleaseId)
                .orElseThrow(() -> new RuntimeException("DarknessRelease not found"));
        darknessReleaseRepository.delete(dr);
        return CrdResponseDTO.toResponseID.builder()
                .ID(dr.getId())
                .build();
    }
}
