package org.istizo.clusterservice.api;

import lombok.RequiredArgsConstructor;
import org.istizo.clusterservice.dto.request.RegisterClusterRequest;
import org.istizo.clusterservice.dto.response.RegisterClusterResponse;
import org.istizo.clusterservice.service.ClusterService;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api/v1/cluster")
@RestController
public class ClusterController {

  private final ClusterService clusterService;

  @PostMapping
  public RegisterClusterResponse registerAgent(@RequestBody RegisterClusterRequest request) {
    return clusterService.register(request);
  }
}
