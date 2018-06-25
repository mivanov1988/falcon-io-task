package io.falcon.task.service;

import io.falcon.schema.MessageAvroDTO;
import io.falcon.task.kafka.KafkaConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private KafkaTemplate<Long, MessageAvroDTO> kafkaTemplate;
    private KafkaConfig kafkaConfig;

    public MessageProducer(KafkaTemplate<Long, MessageAvroDTO> kafkaTemplate, KafkaConfig kafkaConfig) {
        this.kafkaTemplate = kafkaTemplate;
        this.kafkaConfig = kafkaConfig;
    }

    /**
     * Sends the message to the kafka topic
     *
     * @param messageDTO the message to be sent
     */
    public void sendMessage(MessageAvroDTO messageDTO) {
        String topic = kafkaConfig.getTopicMessages();
        logger.debug("REQUEST SEND MESSAGE | TOPIC [{}] | PAYLOAD {}", topic, messageDTO);
        kafkaTemplate.send(topic, messageDTO);
        logger.debug("SUCCESSFULLY SENT MESSAGE | TOPIC [{}] | PAYLOAD {}", topic, messageDTO);

    }
}
