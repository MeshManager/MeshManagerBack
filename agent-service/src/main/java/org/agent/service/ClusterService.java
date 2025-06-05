package org.agent.service;

import org.agent.domain.Cluster;
import org.agent.repository.ClusterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClusterService {

    private final ClusterRepository clusterRepository;

    @Autowired
    public ClusterService(ClusterRepository clusterRepository) {
        this.clusterRepository = clusterRepository;
    }

    public Cluster registerCluster(Cluster cluster) {
        return clusterRepository.save(cluster);
    }
} 