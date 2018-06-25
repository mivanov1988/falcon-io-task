package io.falcon.task.controller;

import io.falcon.task.controller.dto.SendMessageRequest;
import io.falcon.task.service.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Rest Endpoint for messages
 * <p>
 * @author Miroslav Ivanov
 * @since 0.0.1
 * 25/07/18
 */
@RestController
@RequestMapping("/api/v1/messages")
public class MessageController {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @RequestMapping(value = "/send", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void send(@RequestBody @Valid SendMessageRequest request) {
        logger.info("REQUEST SEND MESSAGE | PAYLOAD {}", request);
        messageService.sendMessage(request);
    }
}
