package io.falcon.task.persistence.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Contains common fields
 * <p>
 *
 * @author Miroslav Ivanov
 * @since 0.0.1
 * 24/06/18
 */
@MappedSuperclass
public abstract class BaseEntity {
    @Transient
    protected final Logger logger = LoggerFactory.getLogger(BaseEntity.class);

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @CreationTimestamp
    @Column(name = "ts_inserted", nullable = false)
    private LocalDateTime tsInserted;

    @UpdateTimestamp
    @Column(name = "ts_updated")
    private LocalDateTime tsUpdated;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getTsInserted() {
        return tsInserted;
    }

    public void setTsInserted(LocalDateTime tsInserted) {
        this.tsInserted = tsInserted;
    }

    public LocalDateTime getTsUpdated() {
        return tsUpdated;
    }

    public void setTsUpdated(LocalDateTime tsUpdated) {
        this.tsUpdated = tsUpdated;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (this.getId() != null ? this.getId().hashCode() : 0);

        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object)
            return true;
        if (object == null)
            return false;
        // object.getClass() may be a proxy !!
        if (!getClass().isAssignableFrom(object.getClass())){
            logger.error("#### BaseEntity.equals on unassignable class ####" + object.getClass());
            return false;
        }

        BaseEntity other = (BaseEntity) object;
        if (this.getId() != other.getId() && (this.getId() == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
