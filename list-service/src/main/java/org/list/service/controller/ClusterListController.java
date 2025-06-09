package org.list.service.controller;

import lombok.RequiredArgsConstructor;
import org.list.service.dto.ClusterDto;
import org.list.service.service.ClusterListService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/cluster-list")
public class ClusterListController {

    private final ClusterListService clusterListService;

    @GetMapping
    public List<ClusterDto> getClusterList() {
        return clusterListService.getClusters();
    }
}
