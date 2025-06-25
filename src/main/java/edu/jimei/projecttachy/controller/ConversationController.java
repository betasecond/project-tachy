package edu.jimei.projecttachy.controller;

import edu.jimei.projecttachy.controller.dto.MessageDto;
import edu.jimei.projecttachy.entity.Message;
import edu.jimei.projecttachy.service.ChatService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//-TODO: Replace with actual DTOs
record ConversationDto(long id, String title, String date) {}
record PostMessageRequest(String content, String dataSource) {}
record AgentSelectionRequest(long agentId) {}

@RestController
@RequestMapping("/api/conversations")
public class ConversationController {

    private final ChatService chatService;

    public ConversationController(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping("/{conversationId}/messages")
    public MessageDto postMessage(@PathVariable Long conversationId, @RequestBody PostMessageRequest request) {
        Message agentMessage = chatService.generateResponse(conversationId, request.content());
        return MessageDto.fromEntity(agentMessage);
    }


    // --- The following endpoints are still using mock data ---

    @GetMapping
    public List<ConversationDto> getConversations() {
        // TODO: Implement logic to fetch conversations for the current user
        return List.of(
                new ConversationDto(1, "Analyze sales data", "2024-01-20"),
                new ConversationDto(2, "Customer feedback analysis", "2024-01-19")
        );
    }

    @GetMapping("/{conversationId}/messages")
    public List<MessageDto> getMessages(@PathVariable Long conversationId) {
        // TODO: Implement logic to fetch messages for the given conversationId
        return List.of(
                new MessageDto(1, "agent", "Hello, I'm ready to assist with your data analysis. Please provide your instructions and select a data source.", "https://lh3.googleusercontent.com/aida-public/AB6AXuA4n3DwFF_4CNdc8KTvA2uXnmTH-I7iEupK77SfzgVWYK6v-FjOSOhvjwEkGvqMN9B8r86A1Q-LF4YtROUFNBbWTtvCpBGN2DK3zy14M-iXKd9ou5NrsnOhjL8Qb6vSU3xNnmEfM5aRUyanRT44g-zl-AZjh8JUr9SsXWIYmXzPWm4KKzmR6Y8bagxnBuOGpO5-P2ub-KovZJqiPTtEUwfZm2mrz1tbfHs3Tg_gOYKxa2G35mcmStmYpeQjsIUKyDo9LryrblyB1-07", false)
        );
    }


    @PostMapping("/{conversationId}/agent")
    public void selectAgent(@PathVariable Long conversationId, @RequestBody AgentSelectionRequest request) {
        // TODO: Implement logic to associate agent with conversation
        System.out.println("Conversation " + conversationId + " selected agent " + request.agentId());
    }

} 