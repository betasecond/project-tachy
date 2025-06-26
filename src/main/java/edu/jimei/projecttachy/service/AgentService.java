package edu.jimei.projecttachy.service;

import edu.jimei.projecttachy.controller.dto.AgentDto;
import edu.jimei.projecttachy.entity.Agent;
import edu.jimei.projecttachy.repository.AgentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AgentService {

    private final AgentRepository agentRepository;

    public AgentService(AgentRepository agentRepository) {
        this.agentRepository = agentRepository;
    }

    public List<AgentDto> findAllAgents() {
        return agentRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private AgentDto convertToDto(Agent agent) {
        return new AgentDto(
                agent.getId(),
                agent.getName(),
                agent.getDescription(),
                agent.getAvatarUrl(),
                agent.getCompatibleSources()
        );
    }
} 