package edu.jimei.projecttachy.repository;

import edu.jimei.projecttachy.entity.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConversationRepository extends JpaRepository<Conversation, Long> {
} 