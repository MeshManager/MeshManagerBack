package org.agent.controller;

import org.agent.domain.Cluster;
import org.agent.service.ClusterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/clusters")
@CrossOrigin(origins = "http://localhost:3000")
public class ClusterController {

    private final ClusterService clusterService;

    @Autowired
    public ClusterController(ClusterService clusterService) {
        this.clusterService = clusterService;
    }

    @PostMapping
    public ResponseEntity<Cluster> registerCluster(@RequestBody Cluster cluster) {
        Cluster registeredCluster = clusterService.registerCluster(cluster);
        return new ResponseEntity<>(registeredCluster, HttpStatus.CREATED);
    }
} 