package edu.jimei.projecttachy.repository;

import edu.jimei.projecttachy.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {
} 