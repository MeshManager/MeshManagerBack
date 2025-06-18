package com.istizo.crd_service.service.IstioRouteService;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.istizo.crd_service.domain.DarknessRelease;
import com.istizo.crd_service.domain.Dependency;
import com.istizo.crd_service.domain.ServiceEntity;
import com.istizo.crd_service.repository.DarknessReleaseRepository;
import com.istizo.crd_service.repository.DependencyRepository;
import com.istizo.crd_service.repository.ServiceEntityRepository;
import com.istizo.crd_service.web.dto.IstioRouteDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class IstioRouteServiceImpl implements IstioRouteService {
    private final DarknessReleaseRepository darknessReleaseRepository;
    private final DependencyRepository dependencyRepository;
    private final ServiceEntityRepository serviceEntityRepository;

    @Override
    public String serveYAML(UUID uuid) {
        List<ServiceEntity> serviceEntityList = serviceEntityRepository.findAllByUuid(uuid);
        List<Long> ids = serviceEntityList.stream().map(ServiceEntity::getId).collect(Collectors.toList());
        List<Dependency> dependencies = dependencyRepository.findAllByServiceEntityIdIn(ids);
        List<DarknessRelease> darknessReleases = darknessReleaseRepository.findAllByServiceEntityIdIn(ids);

        Map<Long, List<Dependency>> dependencyMap = dependencies.stream()
                .collect(Collectors.groupingBy(Dependency::getServiceEntityId));

        Map<Long, List<DarknessRelease>> darknessReleaseMap = darknessReleases.stream()
                .collect(Collectors.groupingBy(DarknessRelease::getServiceEntityId));

        // 기존 getMapping 사용
        IstioRouteDTO dto = getMapping(serviceEntityList, dependencyMap, darknessReleaseMap);

        // YAML 변환
        ObjectMapper yamlMapper = new ObjectMapper(new YAMLFactory());
        try {
            return yamlMapper.writeValueAsString(dto);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public IstioRouteDTO getMapping(List<ServiceEntity> serviceEntities,
                                    Map<Long, List<Dependency>> dependencyMap,
                                    Map<Long, List<DarknessRelease>> darknessReleaseMap) {
        List<IstioRouteDTO.ServiceConfig> serviceConfigs = serviceEntities.stream().map(serviceEntity -> {
            // 해당 서비스의 dependencies와 darknessReleases 추출
            List<Dependency> dependencies = dependencyMap.getOrDefault(serviceEntity.getId(), Collections.emptyList());
            List<DarknessRelease> darknessReleases = darknessReleaseMap.getOrDefault(serviceEntity.getId(), Collections.emptyList());

            // Dependency 변환
            Map<String, List<String>> depGrouped = dependencies.stream()
                    .collect(Collectors.groupingBy(
                            d -> d.getName() + "|" + d.getNamespace(),
                            Collectors.mapping(Dependency::getCommitHash, Collectors.toList())
                    ));

            List<IstioRouteDTO.DependencyDTO> dependencyDTOs = depGrouped.entrySet().stream()
                    .map(e -> {
                        String[] key = e.getKey().split("\\|");
                        return IstioRouteDTO.DependencyDTO.builder()
                                .name(key[0])
                                .namespace(key[1])
                                .commitHashes(e.getValue())
                                .build();
                    }).collect(Collectors.toList());

            // DarknessRelease 변환
            List<IstioRouteDTO.DarknessReleaseDTO> darknessReleaseDTOs = darknessReleases.stream()
                    .map(dr -> IstioRouteDTO.DarknessReleaseDTO.builder()
                            .commitHash(dr.getCommitHash())
                            .ips(dr.getIps())
                            .build())
                    .collect(Collectors.toList());

            // ServiceConfig 생성
            return IstioRouteDTO.ServiceConfig.builder()
                    .name(serviceEntity.getName())
                    .namespace(serviceEntity.getNamespace())
                    .type(serviceEntity.getServiceType().name())
                    .commitHashes(serviceEntity.getCommitHashes())
                    .ratio(serviceEntity.getRatio())
                    .dependencies(dependencyDTOs)
                    .darknessReleases(darknessReleaseDTOs)
                    .build();
        }).collect(Collectors.toList());

        // IstioRouteDTO 생성 (첫 번째 서비스 기준으로 metadata 설정)
        ServiceEntity first = serviceEntities.get(0);
        return IstioRouteDTO.builder()
                .apiVersion("mesh-manager.meshmanager.com/v1")
                .kind("IstioRoute")
                .metadata(IstioRouteDTO.Metadata.builder()
                        .name(first.getName())
                        .namespace(first.getNamespace())
                        .build())
                .spec(IstioRouteDTO.IstioRouteSpec.builder()
                        .services(serviceConfigs)
                        .build())
                .build();
    }
}
