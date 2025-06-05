package org.agent.service.api;

import lombok.RequiredArgsConstructor;
import org.agent.service.common.StatusResponse;
import org.agent.service.dto.RegisterAgentRequest;
import org.agent.service.dto.SaveClusterStateRequest;
import org.agent.service.service.AgentService;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api/v1/agent")
@RestController
public class AgentController {

  private final AgentService agentService;

  @PostMapping
  public StatusResponse registerAgent(@RequestBody RegisterAgentRequest request) {
    return agentService.register(request);
  }

  @PostMapping("/{agentName}/cluster-state")
  public StatusResponse saveClusterState(
      @PathVariable(name = "agentName") String agentName,
      @RequestBody SaveClusterStateRequest request
  ) {
    return agentService.saveClusterState(agentName, request);
  }

  @GetMapping("/{agentName}/status")
  public StatusResponse checkAgentStatus(@PathVariable(name = "agentName") String agentName) {
    return agentService.checkAgentStatus(agentName);
  }
}
