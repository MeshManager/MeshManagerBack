package org.istizo.clusterservice.api;

import lombok.RequiredArgsConstructor;
import org.istizo.clusterservice.dto.request.RegisterClusterRequest;
import org.istizo.clusterservice.dto.response.RegisterClusterResponse;
import org.istizo.clusterservice.dto.response.ClusterResponse;
import org.istizo.clusterservice.dto.response.ClusterDetailResponse;
import org.istizo.clusterservice.service.ClusterService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RequestMapping("/api/v1/cluster")
@RestController
public class ClusterController {

  private final ClusterService clusterService;

  @PostMapping
  public RegisterClusterResponse registerAgent(@RequestBody RegisterClusterRequest request) {
    return clusterService.register(request);
  }

  @GetMapping("/check-duplicate/{name}")
  public boolean checkDuplicateName(@PathVariable String name) {
    return clusterService.checkDuplicateName(name);
  }

  @GetMapping
  public List<ClusterResponse> getAllClusters() {
    return clusterService.findAll();
  }

  @DeleteMapping("/{uuid}")
  public ResponseEntity<Void> deleteCluster(@PathVariable String uuid) {
    clusterService.deleteByUuid(UUID.fromString(uuid));
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @GetMapping("/{uuid}/details")
  public ClusterDetailResponse getClusterDetails(@PathVariable String uuid) {
    return clusterService.getClusterDetailsByUuid(UUID.fromString(uuid));
  }
}
