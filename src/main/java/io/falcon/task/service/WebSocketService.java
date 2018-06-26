package io.falcon.task.service;

import io.falcon.task.config.WebSocketConfig;
import io.falcon.task.controller.dto.SendMessageRequest;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

/**
 * Contains the methods for working with Web Sockets
 * <p>
 * @author Miroslav Ivanov
 * @since 0.0.1
 * 26/06/18
 */
@Component
public class WebSocketService {
    private SimpMessagingTemplate messagingTemplate;
    private WebSocketConfig webSocketConfig;

    public WebSocketService(SimpMessagingTemplate messagingTemplate, WebSocketConfig webSocketConfig) {
        this.messagingTemplate = messagingTemplate;
        this.webSocketConfig = webSocketConfig;
    }

    /**
     * Sends the message to the Web Socket
     * @param message the message to be sent
     */
    public void sendMessage(SendMessageRequest message) {
        messagingTemplate.convertAndSend(webSocketConfig.getTopicMessages(), message);
    }
}
