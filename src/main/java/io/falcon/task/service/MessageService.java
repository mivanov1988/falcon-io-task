package io.falcon.task.service;

import io.falcon.schema.MessageAvroDTO;
import io.falcon.task.controller.dto.SendMessageRequest;
import org.springframework.stereotype.Service;

/**
 * Service class for managing of messages
 * (sends to the queue, persists to the database and etc.)
 * <p>
 * @author Miroslav Ivanov
 * @since 0.0.1
 * 25/07/18
 */
@Service
public class MessageService {
    private MessageProducer messageProducer;

    public MessageService(MessageProducer messageProducer) {
        this.messageProducer = messageProducer;
    }

    public void sendMessage(SendMessageRequest request) {
        MessageAvroDTO meesage = converToMessageAvroDTO(request);
        messageProducer.sendMessage(meesage);
    }

    private MessageAvroDTO converToMessageAvroDTO(SendMessageRequest request) {
        return MessageAvroDTO.newBuilder()
                .setTitle(request.getTitle())
                .setContent(request.getContent())
                .build();
    }
}
