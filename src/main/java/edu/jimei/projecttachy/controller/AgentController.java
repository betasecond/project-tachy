package edu.jimei.projecttachy.controller;

import edu.jimei.projecttachy.controller.dto.AgentDto;
import edu.jimei.projecttachy.service.AgentService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/agents")
public class AgentController {

    private final AgentService agentService;

    public AgentController(AgentService agentService) {
        this.agentService = agentService;
    }

    @GetMapping
    public List<AgentDto> getAgents() {
        return agentService.findAllAgents();
    }
} 