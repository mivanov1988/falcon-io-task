package io.falcon.task;

import io.falcon.schema.MessageAvroDTO;
import io.falcon.task.controller.dto.SendMessageRequest;
import io.falcon.task.persistence.dao.MessageRepository;
import io.falcon.task.persistence.entity.Message;
import io.falcon.task.service.MessageProducer;
import io.falcon.task.service.MessageService;
import io.falcon.task.service.WebSocketService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;

/**
 * Test class for {@link MessageService}
 * <p>
 * @author Miroslav Ivanov
 * @since 0.0.1
 * 28/07/18
 */
@RunWith(SpringRunner.class)
public class MessageServiceTest {
    private static final String TITLE = "TestTitle";
    private static final String CONTENT = "TestContent";

    @MockBean
    private MessageRepository messageRepository;

    @MockBean
    private WebSocketService webSocketService;

    @MockBean
    private MessageProducer messageProducer;

    @InjectMocks
    private MessageService messageService;

    @Before
    public void setupMock() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSaveMessage() {
        final boolean[] isInvoked = {false};
        MessageAvroDTO messageAvroDTO = MessageAvroDTO.newBuilder().setTitle(TITLE).setContent(CONTENT).build();

        doAnswer(invocation -> {
            Message message = invocation.getArgument(0);
            Assert.assertEquals(TITLE, message.getTitle());
            Assert.assertEquals(CONTENT, message.getContent());
            isInvoked[0] = true;
            return null;
        }).when(messageRepository).save(any());

        messageService.saveMessage(messageAvroDTO);

        Assert.assertTrue(isInvoked[0]);
    }

    @Test
    public void testSendMessage() {
        final boolean[] isInvokedWebSocket = {false};

        doAnswer(invocation -> {
            SendMessageRequest message = invocation.getArgument(0);
            Assert.assertEquals(TITLE, message.getTitle());
            Assert.assertEquals(CONTENT, message.getContent());
            isInvokedWebSocket[0] = true;
            return null;
        }).when(webSocketService).sendMessage(any());

        final boolean[] isInvokedMessageProducer = {false};

        doAnswer(invocation -> {
            MessageAvroDTO message = invocation.getArgument(0);
            Assert.assertEquals(TITLE, message.getTitle());
            Assert.assertEquals(CONTENT, message.getContent());
            isInvokedMessageProducer[0] = true;
            return null;
        }).when(messageProducer).sendMessage(any());

        SendMessageRequest request = new SendMessageRequest();
        request.setTitle(TITLE);
        request.setContent(CONTENT);

        messageService.sendMessage(request);

        Assert.assertTrue(isInvokedWebSocket[0]);
        Assert.assertTrue(isInvokedMessageProducer[0]);
    }
}
