package io.falcon.task;

import io.falcon.schema.MessageAvroDTO;
import io.falcon.task.service.MessageConsumer;
import io.falcon.task.service.MessageService;
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
 * Test class for {@link MessageConsumer}
 * <p>
 *
 * @author Miroslav Ivanov
 * @since 0.0.1
 * 28/07/18
 */
@RunWith(SpringRunner.class)
public class MessageConsumerTest {
    private static final String TITLE = "TestTitle";
    private static final String CONTENT = "TestContent";

    @InjectMocks
    private MessageConsumer messageConsumer;

    @MockBean
    private MessageService messageService;


    @Before
    public void setupMock() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testMessageListener() {
        final boolean[] isInvoked = {false};

        doAnswer(invocation -> {
            MessageAvroDTO message = invocation.getArgument(0);
            Assert.assertEquals(TITLE, message.getTitle());
            Assert.assertEquals(CONTENT, message.getContent());
            isInvoked[0] = true;
            return null;
        }).when(messageService).saveMessage(any(MessageAvroDTO.class));

        MessageAvroDTO messageAvroDTO = MessageAvroDTO.newBuilder().setTitle(TITLE).setContent(CONTENT).build();
        messageConsumer.messageListener(messageAvroDTO);

        Assert.assertTrue(isInvoked[0]);
    }
}
