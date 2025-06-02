package org.agent.service.api;

import lombok.RequiredArgsConstructor;
import org.agent.service.common.StatusResponse;
import org.agent.service.dto.RegisterAgentRequest;
import org.agent.service.service.AgentService;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api/v1/agent")
@RestController
public class AgentController {

  private final AgentService agentService;

  @PostMapping("/register")
  public StatusResponse registerAgent(@RequestBody RegisterAgentRequest request) {
    return agentService.register(request);
  }
}
