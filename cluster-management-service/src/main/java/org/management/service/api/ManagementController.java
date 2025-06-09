package org.management.service.api;

import lombok.RequiredArgsConstructor;
import org.management.service.common.StatusResponse;
import org.management.service.dto.ClusterResourceRequest;
import org.management.service.service.ManagementService;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api/v1/agent/cluster")
@RestController
public class ManagementController {

  private final ManagementService managementService;

  @PostMapping("/{clusterId}")
  public StatusResponse processClusterResource(
      @PathVariable(name = "clusterId") Long clusterId,
      @RequestBody ClusterResourceRequest request
  ) {
    return managementService.processClusterResource(String.valueOf(clusterId), request);
  }

  @PatchMapping("/{clusterId}/changes")
  public StatusResponse notifyAgentOnResourceChange(@PathVariable(name = "clusterId") Long clusterId) {
    return managementService.notifyAgentOnResourceChange(clusterId);
  }
}
