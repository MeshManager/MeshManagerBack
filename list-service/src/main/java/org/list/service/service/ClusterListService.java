package org.list.service.service;

import lombok.RequiredArgsConstructor;
import org.list.service.client.ClusterClient;
import org.list.service.dto.ClusterDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClusterListService {
    private final ClusterClient clusterClient;

    public List<ClusterDto> getClusters() {
        return clusterClient.fetchClusters();
    }
}
