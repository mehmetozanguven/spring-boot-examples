package com.mehmetozanguven.genericbaseentity.entity;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.OffsetDateTime;

@MappedSuperclass
@EntityListeners(AuditableListener.class)
public class BaseEntity implements Auditable {

    private String versionId;

    @CreatedDate
    private OffsetDateTime createTime;
    @LastModifiedDate
    private OffsetDateTime lastModifiedTime;

    @Override
    public void setEntityVersion(String versionId) {
        this.versionId = versionId;
    }

    @Override
    public String getEntityVersion() {
        return versionId;
    }

    @Override
    public OffsetDateTime getCreateUTCTime() {
        return createTime;
    }

    @Override
    public void setCreateUTCTime(OffsetDateTime createTime) {
        this.createTime = createTime;
    }

    @Override
    public OffsetDateTime getLastModifiedUTCTime() {
        return lastModifiedTime;
    }

    @Override
    public void setLastModifiedUTCTime(OffsetDateTime lastModifiedTime) {
        this.lastModifiedTime = lastModifiedTime;
    }
}
