package io.falcon.task.service;

import io.falcon.schema.MessageAvroDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * Consumes messages from the Kafka topic
 * <p>
 *
 * @author Miroslav Ivanov
 * @since 0.0.1
 * 25/06/18
 */
@Component
public class MessageConsumer {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private MessageService messageService;

    public MessageConsumer(MessageService messageService) {
        this.messageService = messageService;
    }

    @KafkaListener(topics = "${kafka.topic.messages}", groupId = "save-messages", containerFactory = "kafkaListenerContainerFactory")
    public void messageListener(MessageAvroDTO message) {
        logger.debug("RECEIVED MESSAGE | PAYLOAD {}", message);
        messageService.saveMessage(message);
        logger.debug("SUCCESSFULLY SAVED MESSAGE | PAYLOAD {}", message);
    }
}
