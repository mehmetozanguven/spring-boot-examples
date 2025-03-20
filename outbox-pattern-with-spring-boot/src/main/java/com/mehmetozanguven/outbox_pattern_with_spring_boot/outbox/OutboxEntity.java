package com.mehmetozanguven.outbox_pattern_with_spring_boot.outbox;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;

import java.time.OffsetDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
@Entity
@Table(name = "outbox_messages")
public class OutboxEntity {
    @Id
    @UuidGenerator(style = UuidGenerator.Style.TIME)
    @Column(name = "id")
    private String id;
    @Column(name = "type")
    private String type;
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "content")
    private String content;
    @Column(name = "generated_at_utc")
    private OffsetDateTime generatedUtc;
    @Column(name = "consumed_at_utc")
    private OffsetDateTime consumedUtc;
    @Column(name = "error")
    private String error;
}
