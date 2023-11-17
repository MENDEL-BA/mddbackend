package com.openclassrooms.mddapi.services.impl;

import com.openclassrooms.mddapi.models.Message;
import com.openclassrooms.mddapi.repositories.MessageRepository;
import com.openclassrooms.mddapi.services.MessageService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class MessageServiceImpl implements MessageService {
    private final MessageRepository messageRepository;

    public MessageServiceImpl(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @Override
    public Message save(Message message) {
        message.setCreatedAt(LocalDateTime.now());
        return this.messageRepository.save(message);
    }
}
