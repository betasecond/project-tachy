package edu.jimei.projecttachy.controller;

import org.springframework.web.bind.annotation.*;

import java.util.List;

//-TODO: Replace with actual DTOs
record Conversation(long id, String title, String date) {}
record Message(long id, String type, String content, String avatar, boolean isTable) {}
record PostMessageRequest(String content, String dataSource) {}

@RestController
@RequestMapping("/api/conversations")
public class ConversationController {

    @GetMapping
    public List<Conversation> getConversations() {
        // TODO: Implement logic to fetch conversations for the current user
        return List.of(
                new Conversation(1, "Analyze sales data", "2024-01-20"),
                new Conversation(2, "Customer feedback analysis", "2024-01-19")
        );
    }

    @GetMapping("/{conversationId}/messages")
    public List<Message> getMessages(@PathVariable Long conversationId) {
        // TODO: Implement logic to fetch messages for the given conversationId
        return List.of(
                new Message(1, "agent", "Hello, I'm ready to assist with your data analysis. Please provide your instructions and select a data source.", "https://lh3.googleusercontent.com/aida-public/AB6AXuA4n3DwFF_4CNdc8KTvA2uXnmTH-I7iEupK77SfzgVWYK6v-FjOSOhvjwEkGvqMN9B8r86A1Q-LF4YtROUFNBbWTtvCpBGN2DK3zy14M-iXKd9ou5NrsnOhjL8Qb6vSU3xNnmEfM5aRUyanRT44g-zl-AZjh8JUr9SsXWIYmXzPWm4KKzmR6Y8bagxnBuOGpO5-P2ub-KovZJqiPTtEUwfZm2mrz1tbfHs3Tg_gOYKxa2G35mcmStmYpeQjsIUKyDo9LryrblyB1-07", false),
                new Message(2, "user", "Analyze sales data for the last quarter.", "https://lh3.googleusercontent.com/aida-public/AB6AXuDVUBlNg0ACI3inGKAOmj4WWYKHk5t7mFjgzcf0dHCWQqXjV8WGh4n8OUWp7k-qmDg_9hQYkAnPoBQNPlwY62pZ0-x0A9A_YxLKSOnVTVk4H03HrMDQ6S-Zo-i5tq7K3iMc8amMCGAn6XqOrZPHagqRVgmEQFswCtccKdsIVK6A0rQ137HDvnUo20YIbADYiXphdg0BBQoCV58Fnw5ZC_v9lJW5loaqlsWa61j2MNoulSsFivZPxKMQbU9c3sIKl5XpWRGiTqasBz8D", false),
                new Message(3, "agent", "Here's the sales data analysis for the last quarter:\n\n**销售数据分析表格**\n\n| Product | Sales | Revenue |\n|---|---|---|\n| Product A | 1000 | $50,000 |\n| Product B | 1500 | $75,000 |\n| Product C | 800 | $40,000 |\n\n**总计统计:**\n- Total Sales: 3300\n- Total Revenue: $165,000", "https://lh3.googleusercontent.com/aida-public/AB6AXuCtSYF8Yxc5_NrvJcw0ezjYV5rluO05Uo-y0cSf8LFtyeYvy8gjtzsVQZnse1lxKFtgwC_XAXUX93QBR_8J3ew6kwkaRubup5ZA2CM_9R879EM6ZzKCRZ77QfnHrl44ytByl-cjnJf8M4Yid4Gfue0PcnWLuVUT-FCi-uZHI0JizgRG7ySeosWL9RCUjZtPUJ0N412zAoCEdycYCX5FCJommYKM9dIz7PcXiu8NgTLOUk4zIcRAgZXq87QW59CVGjyHMXBmd4sTmKWK", true)
        );
    }

    @PostMapping("/{conversationId}/messages")
    public Message postMessage(@PathVariable Long conversationId, @RequestBody PostMessageRequest request) {
        // TODO: Implement logic to process the new message and respond
        System.out.println("Received message for conversation " + conversationId + ": " + request.content());
        return new Message(4, "agent", "我已收到您的请求，正在分析数据...", "https://lh3.googleusercontent.com/aida-public/AB6AXuCtSYF8Yxc5_NrvJcw0ezjYV5rluO05Uo-y0cSf8LFtyeYvy8gjtzsVQZnse1lxKFtgwC_XAXUX93QBR_8J3ew6kwkaRubup5ZA2CM_9R879EM6ZzKCRZ77QfnHrl44ytByl-cjnJf8M4Yid4Gfue0PcnWLuVUT-FCi-uZHI0JizgRG7ySeosWL9RCUjZtPUJ0N412zAoCEdycYCX5FCJommYKM9dIz7PcXiu8NgTLOUk4zIcRAgZXq87QW59CVGjyHMXBmd4sTmKWK", false);
    }

    @PostMapping("/{conversationId}/agent")
    public void selectAgent(@PathVariable Long conversationId, @RequestBody AgentSelectionRequest request) {
        // TODO: Implement logic to associate agent with conversation
        System.out.println("Conversation " + conversationId + " selected agent " + request.agentId());
    }

    //-TODO: Replace with actual DTO
    record AgentSelectionRequest(long agentId) {}
} 