package org.list.service.client;

import org.list.service.dto.ClusterDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import java.util.List;
import java.util.Arrays;

@Component
public class ClusterClient {

    private final RestTemplate restTemplate;
    private final String clusterServiceUrl;

    public ClusterClient(RestTemplate restTemplate, @Value("${cluster.service.url}") String clusterServiceUrl) {
        this.restTemplate = restTemplate;
        this.clusterServiceUrl = clusterServiceUrl;
    }

    public List<ClusterDto> fetchClusters() {
        ClusterDto[] response = restTemplate.getForObject(clusterServiceUrl + "/api/v1/cluster", ClusterDto[].class);
        return Arrays.asList(response);
    }
}
