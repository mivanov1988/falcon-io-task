package io.falcon.task.service;

import io.falcon.schema.MessageAvroDTO;
import io.falcon.task.controller.dto.SendMessageRequest;
import io.falcon.task.persistence.dao.MessageRepository;
import io.falcon.task.persistence.entity.Message;
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
    private MessageRepository messageRepository;

    public MessageService(MessageProducer messageProducer, MessageRepository messageRepository) {
        this.messageProducer = messageProducer;
        this.messageRepository = messageRepository;
    }

    /**
     * Sends message to the Kafka topic
     * @param request the message to be sent to the kafka topic
     */
    public void sendMessage(SendMessageRequest request) {
        MessageAvroDTO meesage = converToMessageAvroDTO(request);
        messageProducer.sendMessage(meesage);
    }

    /**
     * Saves message to the database
     * @param messageDTO the message to be saved
     */
    public void saveMessage(MessageAvroDTO messageDTO) {
        Message messageEntity = convertToMessageEntity(messageDTO);
        messageRepository.save(messageEntity);
    }

    private MessageAvroDTO converToMessageAvroDTO(SendMessageRequest request) {
        return MessageAvroDTO.newBuilder()
                .setTitle(request.getTitle())
                .setContent(request.getContent())
                .build();
    }

    private Message convertToMessageEntity(MessageAvroDTO messageDTO) {
        Message messageEntity = new Message();

        String title = messageDTO.getTitle();
        messageEntity.setTitle(title);

        String content = messageDTO.getContent();
        messageEntity.setContent(content);

        return messageEntity;
    }
}
