package io.falcon.task.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Represents T_MESSAGE table
 * <p>
 *
 * @author Miroslav Ivanov
 * @since 0.0.1
 * 24/06/18
 */
@Entity
@Table(name = "T_MESSAGE")
public class Message extends BaseEntity implements Serializable {
    @Column(length = 45, nullable = false)
    private String title;

    @Column(length = 255, nullable = false)
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
