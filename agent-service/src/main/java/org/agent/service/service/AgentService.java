package org.agent.service.service;

import lombok.RequiredArgsConstructor;
import org.agent.service.common.StatusResponse;
import org.agent.service.dto.AgentStatusResponse;
import org.agent.service.dto.RegisterAgentRequest;
import org.agent.service.dto.SaveClusterStateRequest;
import org.agent.service.entity.Agent;
import org.agent.service.repository.AgentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class AgentService {

  private final AgentRepository agentRepository;
  private final RedisService redisService;

  private static final Logger logger = LoggerFactory.getLogger(AgentService.class);

  @Transactional
  public StatusResponse register(RegisterAgentRequest request) {
    logger.info("register agent request: {}", request);

    agentRepository.save(
        Agent.create(request.name(), request.clusterId())
    );
    return StatusResponse.of(true);
  }

  public StatusResponse saveClusterState(String agentName) {
    logger.info("saveClusterState called for agent: {}", agentName);
    redisService.saveWithTTL(agentName);
    logger.info("saveClusterState completed for agent: {}", agentName);
    return StatusResponse.of(true);
  }

  public StatusResponse checkAgentStatus(String agentName) {
    return redisService.exists(agentName)
        ? StatusResponse.of(true)
        : StatusResponse.of(false);
  }

  public List<AgentStatusResponse> getAllAgentStatuses() {
    return agentRepository.findAll().stream()
        .map(agent -> new AgentStatusResponse(
            agent.getName(),
            agent.getClusterId(),
            true // 등록만 되어 있으면 무조건 연결됨으로 표시
        ))
        .toList();
  }

  public Set<String> getConnectedAgentNames() {
    return redisService.getAllKeys();
  }
}
