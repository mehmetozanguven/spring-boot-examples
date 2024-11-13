package com.mehmetozanguven.criteria_api_with_spring_boot.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import org.springframework.data.annotation.CreatedDate;

import java.time.OffsetDateTime;

@MappedSuperclass
@EntityListeners(AuditableListener.class)
public class BaseEntity implements Auditable {

    @CreatedDate
    @Column(name = "create_time")
    private OffsetDateTime createTime;

    @Override
    public OffsetDateTime getCreateTime() {
        return createTime;
    }

    @Override
    public void setCreateTime(OffsetDateTime createTime) {
        this.createTime = createTime;
    }


}
