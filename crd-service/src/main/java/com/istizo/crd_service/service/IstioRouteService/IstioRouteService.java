package com.istizo.crd_service.service.IstioRouteService;

import com.istizo.crd_service.domain.DarknessRelease;
import com.istizo.crd_service.domain.Dependency;
import com.istizo.crd_service.domain.ServiceEntity;
import com.istizo.crd_service.web.dto.IstioRouteDTO;

import java.util.List;
import java.util.UUID;

public interface IstioRouteService {
    String serveYAML(UUID uuid);
}
