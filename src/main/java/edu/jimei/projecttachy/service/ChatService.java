package edu.jimei.projecttachy.service;

import edu.jimei.projecttachy.entity.Conversation;
import edu.jimei.projecttachy.entity.Message;
import edu.jimei.projecttachy.repository.ConversationRepository;
import edu.jimei.projecttachy.repository.MessageRepository;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class ChatService {

    private final ChatClient chatClient;
    private final ConversationRepository conversationRepository;
    private final MessageRepository messageRepository;

    public ChatService(ChatClient.Builder chatClientBuilder, ConversationRepository conversationRepository, MessageRepository messageRepository) {
        this.chatClient = chatClientBuilder.build();
        this.conversationRepository = conversationRepository;
        this.messageRepository = messageRepository;
    }

    @Transactional
    public Message generateResponse(Long conversationId, String userContent) {
        // 1. Find the conversation, or throw an exception if not found
        Conversation conversation = conversationRepository.findById(conversationId)
                .orElseThrow(() -> new IllegalArgumentException("Conversation not found with ID: " + conversationId));

        // 2. Create and save the user's message
        Message userMessage = new Message();
        userMessage.setConversation(conversation);
        userMessage.setSenderType(Message.SenderType.user);
        userMessage.setContent(userContent);
        messageRepository.save(userMessage);

        // 3. Call the AI model to get a response
        String agentContent = chatClient.prompt()
                .user(userContent)
                .call()
                .content();

        // 4. Create and save the agent's message
        Message agentMessage = new Message();
        agentMessage.setConversation(conversation);
        agentMessage.setSenderType(Message.SenderType.agent);
        agentMessage.setContent(agentContent);
        return messageRepository.save(agentMessage);
    }
} 