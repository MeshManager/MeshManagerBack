package org.management.service.api;

import lombok.RequiredArgsConstructor;
import org.management.service.common.StatusResponse;
import org.management.service.dto.ClusterResourceRequest;
import org.management.service.service.ManagementService;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api/v1/management/cluster")
@RestController
public class ManagementController {

  private final ManagementService managementService;

  @PostMapping
  public StatusResponse saveClusterResource(@RequestBody ClusterResourceRequest request) {
    return managementService.saveClusterResource(request);
  }

  @PatchMapping("/{clusterId}/changes")
  public StatusResponse notifyAgentOnResourceChange(@PathVariable(name = "clusterId") String clusterId) {
    return managementService.notifyAgentOnResourceChange(clusterId);
  }
}
