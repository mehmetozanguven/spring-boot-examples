package com.mehmetozanguven.criteria_api_with_spring_boot.entity;

import jakarta.persistence.PrePersist;

import java.time.OffsetDateTime;
import java.time.ZoneId;

public class AuditableListener {
    @PrePersist
    void preCreate(Auditable auditable) {
        auditable.setCreateTime(OffsetDateTime.now(ZoneId.of("UTC")));
    }
}
