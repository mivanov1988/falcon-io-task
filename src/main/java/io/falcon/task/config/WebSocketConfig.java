package io.falcon.task.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * Holds the configuration of Web Socket
 * <p>
 * @author Miroslav Ivanov
 * @since 0.0.1
 * 26/06/18
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    @Value(value = "${websocket.broker.host}")
    private String brokerHost;

    @Value(value = "${websocket.broker.port}")
    private int brokerPort;

    @Value(value = "${websocket.broker.username}")
    private String brokerUsername;

    @Value(value = "${websocket.broker.password}")
    private String brokerPassword;

    @Value(value = "${websocket.broker.destination.prefix}")
    private String brokerDestinationPrefix;

    @Value(value = "${websocket.endpoint.name}")
    private String endpointName;

    @Value(value = "${websocket.topic.messages}")
    private String topicMessages;

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableStompBrokerRelay(getBrokerDestinationPrefix())
                .setRelayHost(getBrokerHost())
                .setRelayPort(getBrokerPort())
                .setClientLogin(getBrokerUsername())
                .setClientPasscode(getBrokerPassword());
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint(getEndpointName()).withSockJS();
    }

    public String getBrokerHost() {
        return brokerHost;
    }

    public int getBrokerPort() {
        return brokerPort;
    }

    public String getBrokerUsername() {
        return brokerUsername;
    }

    public String getBrokerPassword() {
        return brokerPassword;
    }

    public String getBrokerDestinationPrefix() {
        return brokerDestinationPrefix;
    }

    public String getEndpointName() {
        return endpointName;
    }

    public String getTopicMessages() {
        return topicMessages;
    }
}