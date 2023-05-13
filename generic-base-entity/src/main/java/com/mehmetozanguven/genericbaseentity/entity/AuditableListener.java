package com.mehmetozanguven.genericbaseentity.entity;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.UUID;

public class AuditableListener {

    @PrePersist
    void preCreate(Auditable auditable) {
        auditable.setCreateUTCTime(OffsetDateTime.now(ZoneId.of("UTC")));
        auditable.setLastModifiedUTCTime(OffsetDateTime.now(ZoneId.of("UTC")));
        auditable.setEntityVersion(UUID.randomUUID().toString());
    }

    @PreUpdate
    void preUpdate(Auditable auditable) {
        auditable.setLastModifiedUTCTime(OffsetDateTime.now(ZoneId.of("UTC")));
        auditable.setEntityVersion(UUID.randomUUID().toString());
    }
}
