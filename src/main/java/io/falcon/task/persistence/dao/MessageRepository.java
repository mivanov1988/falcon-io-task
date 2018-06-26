package io.falcon.task.persistence.dao;

import io.falcon.task.persistence.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Contains methods for working with database
 * <p>
 *
 * @author Miroslav Ivanov
 * @since 0.0.1
 * 25/06/18
 */
@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
}
