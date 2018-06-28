package io.falcon.task.service;

import io.falcon.schema.MessageAvroDTO;
import io.falcon.task.controller.dto.ListMessageResult;
import io.falcon.task.controller.dto.SendMessageRequest;
import io.falcon.task.persistence.dao.MessageRepository;
import io.falcon.task.persistence.entity.Message;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class for managing of messages
 * (sends to the queue, persists to the database and etc.)
 * <p>
 *
 * @author Miroslav Ivanov
 * @since 0.0.1
 * 25/07/18
 */
@Service
public class MessageService {
    private MessageProducer messageProducer;
    private MessageRepository messageRepository;
    private WebSocketService webSocketService;

    public MessageService(MessageProducer messageProducer, MessageRepository messageRepository, WebSocketService webSocketService) {
        this.messageProducer = messageProducer;
        this.messageRepository = messageRepository;
        this.webSocketService = webSocketService;
    }

    /**
     * Sends message to the Kafka topic and Web Socket
     *
     * @param request the message to be sent
     */
    public void sendMessage(SendMessageRequest request) {
        MessageAvroDTO message = converToMessageAvroDTO(request);
        webSocketService.sendMessage(request);
        messageProducer.sendMessage(message);
    }

    /**
     * Saves message to the database
     *
     * @param messageDTO the message to be saved
     */
    public void saveMessage(MessageAvroDTO messageDTO) {
        Message messageEntity = convertToMessageEntity(messageDTO);
        messageRepository.save(messageEntity);
    }

    /**
     * Returns all messages from the database
     * @return all messages from the database
     */
    public List<ListMessageResult> loadMessages() {
        List<Message> messages = messageRepository.findAll();
        return convertToListMessageResult(messages);
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

    private List<ListMessageResult> convertToListMessageResult(List<Message> messages) {
        if (CollectionUtils.isEmpty(messages)) {
            return Collections.emptyList();
        }

        return messages.stream()
                .map(message -> {
                    ListMessageResult result = new ListMessageResult();

                    Long id = message.getId();
                    result.setId(id);

                    String title = message.getTitle();
                    result.setTitle(title);

                    String content = message.getContent();
                    result.setContent(content);

                    return result;
                })
                .collect(Collectors.toList());
    }
}
