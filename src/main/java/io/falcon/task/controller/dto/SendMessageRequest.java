package io.falcon.task.controller.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * This class represents send message request
 * <p>
 * @author Miroslav Ivanov
 * @since 0.0.1
 * 25/07/18
 */
public class SendMessageRequest extends BaseDTO {
    @NotNull
    @Size(min=1, max = 45)
    private String title;
    @NotNull
    @Size(min=1, max = 255)
    private String content;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
