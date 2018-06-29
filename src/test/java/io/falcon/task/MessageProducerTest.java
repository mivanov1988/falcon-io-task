package io.falcon.task;

import io.falcon.schema.MessageAvroDTO;
import io.falcon.task.kafka.KafkaConfig;
import io.falcon.task.service.MessageProducer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

/**
 * Test class for {@link MessageProducer}
 * <p>
 *
 * @author Miroslav Ivanov
 * @since 0.0.1
 * 28/07/18
 */
@RunWith(SpringRunner.class)
public class MessageProducerTest {
    private static final String TITLE = "TestTitle";
    private static final String CONTENT = "TestContent";
    private static final String TOPIC = "messages";

    @InjectMocks
    private MessageProducer messageProducer;

    @MockBean
    private KafkaTemplate<Long, MessageAvroDTO> kafkaTemplate;

    @MockBean
    private KafkaConfig kafkaConfig;

    @Before
    public void setupMock() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSendMessage() {
        final boolean[] isInvoked = {false};
        when(kafkaConfig.getTopicMessages()).thenReturn(TOPIC);

        doAnswer(invocation -> {
            String topic = invocation.getArgument(0);
            Assert.assertEquals(TOPIC, topic);

            MessageAvroDTO message = invocation.getArgument(1);
            Assert.assertEquals(TITLE, message.getTitle());
            Assert.assertEquals(CONTENT, message.getContent());
            isInvoked[0] = true;
            return null;
        }).when(kafkaTemplate).send(any(String.class), any(MessageAvroDTO.class));

        MessageAvroDTO messageAvroDTO = MessageAvroDTO.newBuilder().setTitle(TITLE).setContent(CONTENT).build();
        messageProducer.sendMessage(messageAvroDTO);

        Assert.assertTrue(isInvoked[0]);
    }
}
