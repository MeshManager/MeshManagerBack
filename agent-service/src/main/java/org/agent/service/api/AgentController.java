package org.agent.service.api;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.agent.service.common.StatusResponse;
import org.agent.service.dto.AgentStatusResponse;
import org.agent.service.dto.RegisterAgentRequest;
import org.agent.service.dto.SaveClusterStateRequest;
import org.agent.service.service.AgentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/v1/agent")
@RestController
public class AgentController {

  private final AgentService agentService;

  @Operation(
      summary = "고객 k8s에 설치된 agent 정보 등록 API"
  )
  @PostMapping("/")
  public StatusResponse registerAgent(@RequestBody RegisterAgentRequest request) {
    return agentService.register(request);
  }

  @Operation(
      summary = "고객 k8s의 클러스터 리소스 상태 확인 API"
  )
  @PostMapping("/{agentName}/cluster-state")
  public StatusResponse saveClusterState(
      @PathVariable(name = "agentName") String agentName,
      @RequestBody SaveClusterStateRequest request
  ) {
    return agentService.saveClusterState(agentName, request);
  }

  @Operation(
      summary = "고객 k8s에 설치된 agent와의 연결 확인 API"
  )
  @GetMapping("/{agentName}/status")
  public StatusResponse checkAgentStatus(@PathVariable(name = "agentName") String agentName) {
    return agentService.checkAgentStatus(agentName);
  }

  @Operation(
      summary = "모든 에이전트의 연결 상태 목록 조회 API"
  )
  @GetMapping
  public List<AgentStatusResponse> getAllAgentStatuses() {
    return agentService.getAllAgentStatuses();
  }
}
