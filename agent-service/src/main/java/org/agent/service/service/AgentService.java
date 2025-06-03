package org.agent.service.service;

import lombok.RequiredArgsConstructor;
import org.agent.service.common.StatusResponse;
import org.agent.service.dto.RegisterAgentRequest;
import org.agent.service.dto.SaveClusterStateRequest;
import org.agent.service.entity.Agent;
import org.agent.service.repository.AgentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class AgentService {

  private final AgentRepository agentRepository;
  private final RedisService redisService;

  @Transactional
  public StatusResponse register(RegisterAgentRequest request) {
    agentRepository.save(Agent.create(request.name()));
    return StatusResponse.of(true);
  }

  public StatusResponse saveClusterState(String agentName, SaveClusterStateRequest request) {
    redisService.saveJsonWithTTL(agentName, request);
    return StatusResponse.of(true);
  }
}
