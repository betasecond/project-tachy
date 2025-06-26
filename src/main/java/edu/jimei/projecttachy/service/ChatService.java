package edu.jimei.projecttachy.service;

import edu.jimei.projecttachy.entity.Agent;
import edu.jimei.projecttachy.entity.Conversation;
import edu.jimei.projecttachy.entity.Message;
import edu.jimei.projecttachy.repository.AgentRepository;
import edu.jimei.projecttachy.repository.ConversationRepository;
import edu.jimei.projecttachy.repository.MessageRepository;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jakarta.persistence.EntityNotFoundException;


@Service
public class ChatService {

    private final ChatClient chatClient;
    private final ConversationRepository conversationRepository;
    private final MessageRepository messageRepository;
    private final AgentRepository agentRepository;

    public ChatService(ChatClient.Builder chatClientBuilder, ConversationRepository conversationRepository, MessageRepository messageRepository, AgentRepository agentRepository) {
        this.chatClient = chatClientBuilder.build();
        this.conversationRepository = conversationRepository;
        this.messageRepository = messageRepository;
        this.agentRepository = agentRepository;
    }

    @Transactional
    public Message generateResponse(Long conversationId, String userContent) {
        // 1. Find the conversation.
        Conversation conversation = conversationRepository.findById(conversationId)
                .orElseGet(() -> createNewConversation(userContent));

        // 2. Create and save the user's message
        Message userMessage = new Message();
        userMessage.setConversation(conversation);
        userMessage.setSenderType(Message.SenderType.user);
        userMessage.setContent(userContent);
        messageRepository.save(userMessage);

        // 3. Build the prompt, including system prompt if an agent is selected
        ChatClient.ChatClientRequestSpec promptRequest = chatClient.prompt()
                .user(userContent);

        if (conversation.getAgent() != null && conversation.getAgent().getSystemPrompt() != null) {
            promptRequest.system(conversation.getAgent().getSystemPrompt());
        }

        // 4. Call the AI model to get a response
        String agentContent = promptRequest.call().content();

        // 5. Create and save the agent's message
        Message agentMessage = new Message();
        agentMessage.setConversation(conversation);
        agentMessage.setSenderType(Message.SenderType.agent);
        agentMessage.setContent(agentContent);
        return messageRepository.save(agentMessage);
    }

    @Transactional
    public void selectAgentForConversation(Long conversationId, Long agentId) {
        Conversation conversation = conversationRepository.findById(conversationId)
                .orElseThrow(() -> new EntityNotFoundException("Conversation not found with id: " + conversationId));

        Agent agent = agentRepository.findById(agentId)
                .orElseThrow(() -> new EntityNotFoundException("Agent not found with id: " + agentId));

        conversation.setAgent(agent);
        conversationRepository.save(conversation);
    }

    private Conversation createNewConversation(String userContent) {
        Conversation newConversation = new Conversation();
        // In a real app, user ID would come from the security context
        newConversation.setUserId(1L); 
        
        String title = userContent.length() > 50 ? userContent.substring(0, 50) + "..." : userContent;
        newConversation.setTitle(title);
        
        return conversationRepository.save(newConversation);
    }
} 