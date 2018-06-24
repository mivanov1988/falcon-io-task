package io.falcon.task.kafka;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * Holds the common Kafka configuration
 * <p>
 *
 * @author Miroslav Ivanov
 * @since 0.0.1
 * 24/06/18
 */
@Configuration
public class KafkaConfig {
    @Value(value = "${kafka.bootstrap.servers}")
    private String bootstrapServers;

    @Value(value = "${kafka.schema.registry.url}")
    private String schemaRegistryUrl;

    @Value(value = "${kafka.topic.messages}")
    private String topicMessages;

    public String getBootstrapServers() {
        return bootstrapServers;
    }

    public String getSchemaRegistryUrl() {
        return schemaRegistryUrl;
    }

    public String getTopicMessages() {
        return topicMessages;
    }
}
