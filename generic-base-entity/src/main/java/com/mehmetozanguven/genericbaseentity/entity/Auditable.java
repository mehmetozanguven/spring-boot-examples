package com.mehmetozanguven.genericbaseentity.entity;

import java.time.OffsetDateTime;

public interface Auditable {

    void setEntityVersion(String versionId);

    String getEntityVersion();

    OffsetDateTime getCreateUTCTime();

    void setCreateUTCTime(OffsetDateTime createTime);

    OffsetDateTime getLastModifiedUTCTime();

    void setLastModifiedUTCTime(OffsetDateTime lastModifiedTime);
}
