package io.falcon.task.controller.dto;

/**
 * This class represents list messages result
 * <p>
 *
 * @author Miroslav Ivanov
 * @since 0.0.1
 * 26/07/18
 */
public class ListMessageResult extends BaseDTO {
    private long id;
    private String title;
    private String content;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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
