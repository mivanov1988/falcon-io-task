package io.falcon.task;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import io.falcon.task.controller.MessageController;
import io.falcon.task.controller.dto.ListMessageResult;
import io.falcon.task.controller.dto.SendMessageRequest;
import io.falcon.task.service.MessageService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doAnswer;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Test class for {@link MessageController}
 * <p>
 *
 * @author Miroslav Ivanov
 * @since 0.0.1
 * 28/07/18
 */
@RunWith(SpringRunner.class)
@WebMvcTest(MessageController.class)
public class MessageControllerTest {
    private static final long ID = 1L;
    private static final String TITLE = "TestTitle";
    private static final String CONTENT = "TestContent";

    // Endpoints
    private static final String SEND_MESSAGE_API = "/api/v1/messages/send";
    private static final String LIST_MESSAGES_API = "/api/v1/messages";

    @Autowired
    private MockMvc mvc;

    @MockBean
    private MessageService messageService;

    @Test
    public void testSendMessage_withCorrectRequest() throws Exception {
        final boolean[] isInvoked = {false};
        mockSendMessageMethod(isInvoked);

        String requestJson = generateTestRequest(TITLE, CONTENT);
        MockHttpServletRequestBuilder postRequest = buildPostRequest(requestJson);
        mvc.perform(postRequest).andExpect(status().isCreated());

        Assert.assertTrue(isInvoked[0]);
    }

    @Test
    public void testSendMessage_withEmptyTitle() throws Exception {
        String requestJson = generateTestRequest("", CONTENT);

        testSendMessageExpectUnprocessableEntity(requestJson);
    }

    @Test
    public void testSendMessage_withNullTitle() throws Exception {
        String requestJson = generateTestRequest(null, CONTENT);

        testSendMessageExpectUnprocessableEntity(requestJson);
    }

    @Test
    public void testSendMessage_withEmptyContent() throws Exception {
        String requestJson = generateTestRequest(TITLE, "");

        testSendMessageExpectUnprocessableEntity(requestJson);
    }

    @Test
    public void testSendMessage_withNullContent() throws Exception {
        String requestJson = generateTestRequest(TITLE, null);

        testSendMessageExpectUnprocessableEntity(requestJson);
    }

    @Test
    public void testSendMessage_withEmptyTitleAndContent() throws Exception {
        String requestJson = generateTestRequest("", "");

        testSendMessageExpectUnprocessableEntity(requestJson);
    }

    @Test
    public void testSendMessage_withNullTitleAndContent() throws Exception {
        String requestJson = generateTestRequest(null, null);

        testSendMessageExpectUnprocessableEntity(requestJson);
    }

    @Test
    public void testListMessages_withNonEmptyResult() throws Exception {
        ListMessageResult message = new ListMessageResult();
        message.setId(ID);
        message.setTitle(TITLE);
        message.setContent(CONTENT);

        List<ListMessageResult> messages = Arrays.asList(message);
        given(messageService.loadMessages()).willReturn(messages);

        mvc.perform(MockMvcRequestBuilders.get(LIST_MESSAGES_API).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].title", is(TITLE)))
                .andExpect(jsonPath("$[0].content", is(CONTENT)));
    }

    @Test
    public void testListMessages_withEmptyResult() throws Exception {
        List<ListMessageResult> messages = Collections.emptyList();
        given(messageService.loadMessages()).willReturn(messages);

        mvc.perform(MockMvcRequestBuilders.get(LIST_MESSAGES_API).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    private void testSendMessageExpectUnprocessableEntity(String requestJson) throws Exception {
        final boolean[] isInvoked = {false};
        mockSendMessageMethod(isInvoked);

        MockHttpServletRequestBuilder postRequest = buildPostRequest(requestJson);
        mvc.perform(postRequest).andExpect(status().isUnprocessableEntity());

        Assert.assertEquals(Boolean.FALSE, isInvoked[0]);
    }

    private void mockSendMessageMethod(boolean[] isInvoked) {
        doAnswer(invocation -> {
            Object argument = invocation.getArgument(0);
            SendMessageRequest request = (SendMessageRequest) argument;

            Assert.assertEquals(TITLE, request.getTitle());
            Assert.assertEquals(CONTENT, request.getContent());
            isInvoked[0] = true;
            return null;
        }).when(messageService).sendMessage(any());
    }

    private MockHttpServletRequestBuilder buildPostRequest(String requestJson) {
        return MockMvcRequestBuilders.post(SEND_MESSAGE_API)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson)
                .accept(MediaType.APPLICATION_JSON);
    }

    private static String generateTestRequest(String title, String content) throws JsonProcessingException {
        SendMessageRequest request = new SendMessageRequest();
        request.setTitle(title);
        request.setContent(content);
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        return ow.writeValueAsString(request);
    }
}
