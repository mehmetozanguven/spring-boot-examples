package com.mehmetozanguven.criteria_api_with_spring_boot.entity;

import java.time.OffsetDateTime;

public interface Auditable {
    OffsetDateTime getCreateTime();

    void setCreateTime(OffsetDateTime createTime);
}
