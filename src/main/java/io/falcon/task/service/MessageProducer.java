package io.falcon.task.service;

import io.falcon.schema.MessageDTO;
import io.falcon.task.kafka.KafkaConfig;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * <p>
 *
 * @author Miroslav Ivanov
 * @since 0.0.1
 * 24/06/18
 */
@Component
public class MessageProducer {
    private KafkaTemplate<Long, MessageDTO> kafkaTemplate;
    private KafkaConfig kafkaConfig;

    public MessageProducer(KafkaTemplate<Long, MessageDTO> kafkaTemplate, KafkaConfig kafkaConfig) {
        this.kafkaTemplate = kafkaTemplate;
        this.kafkaConfig = kafkaConfig;
    }

    /**
     * Sends the message to the kafka topic
     *
     * @param messageDTO the message to be sent
     */
    public void sendMessage(MessageDTO messageDTO) {
        kafkaTemplate.send(kafkaConfig.getTopicMessages(), messageDTO);
    }
}
