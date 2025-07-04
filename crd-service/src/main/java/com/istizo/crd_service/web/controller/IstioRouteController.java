package com.istizo.crd_service.web.controller;

import com.istizo.crd_service.service.IstioRouteService.IstioRouteService;
import com.istizo.crd_service.web.dto.IstioRouteDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/yaml")
public class IstioRouteController {
    private final IstioRouteService istioRouteService;

    @GetMapping(value = "/{uuid}", produces = "application/json")
    public String getIstioRouteAsYaml(@PathVariable UUID uuid) {
        return istioRouteService.serveYAML(uuid);
    }
}
